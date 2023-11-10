package pro.sky.telegrambot.parser;

import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {
    String message = "01.01.2022 20:00 Сделать домашнюю работу";
    NotificationTask notificationTask = parseMessage(message);
    public static NotificationTask parseMessage(String message) {
        Pattern pattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2})\\s(.+)");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String dateTimeString = matcher.group(1);
            String text = matcher.group(2);

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

            NotificationTask notificationTask = new NotificationTask();
            notificationTask.setScheduledTime(dateTime);
            notificationTask.setNotificationText(text);

            return notificationTask;
        } else {
            throw new IllegalArgumentException("Invalid message format");
        }
    }
}
