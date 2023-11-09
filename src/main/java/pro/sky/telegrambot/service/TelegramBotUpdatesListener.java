package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.model.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
@Slf4j
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
   @Autowired
    private NotificationTaskRepository notificationTaskRepository;
    private TelegramBot telegramBot;
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    public TelegramBotUpdatesListener(NotificationTaskRepository notificationTaskRepository, TelegramBot telegramBot) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {

        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            if (update.message() != null && update.message().text() != null) {
                processMessage(update.message());
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
    private void processMessage(Message message) {
        if (message == null || message.text() == null) {
            return;
        }
        String text = message.text();
        long chatId = message.chat().id();

        if ("/start".equals(text)) {
            sendWelcomeMessage(chatId);
        }
    }
    private void sendWelcomeMessage(long chatId) {
        SendMessage welcomeMessage = new SendMessage(chatId, "Добро пожаловать! Это ваше приветственное сообщение.");

        try {
            SendResponse sendResponse = telegramBot.execute(welcomeMessage);

            if (!sendResponse.isOk()) {
                log.error("Failed to send welcome message. Error: {}", sendResponse.description());
            }
        } catch (Exception e) {
            log.error("Exception while sending welcome message", e);
        }
    }
    private void sendNotification(String chatId, String notificationText) {
        SendMessage notificationMessage = new SendMessage(chatId, notificationText);

        try {
            SendResponse sendResponse = telegramBot.execute(notificationMessage);

            if (!sendResponse.isOk()) {
                log.error("Failed to send notification. Error: {}", sendResponse.description());
            }
        } catch (Exception e) {
            log.error("Exception while sending notification", e);
        }
    }
    @Scheduled(cron = "0 0/1 * * * *")
    public void processScheduledTasks() {
        LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTask> tasksToProcess = notificationTaskRepository.findByScheduledTime(currentDateTime);

        for (NotificationTask task : tasksToProcess) {
            String chatId = task.getChatId();
            String notificationText = task.getNotificationText();
            sendNotification(chatId, notificationText);

            notificationTaskRepository.delete(task);
        }
    }

}
