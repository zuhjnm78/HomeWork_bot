package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "notification_task")
@SequenceGenerator(name = "notification_task_id_seq", sequenceName = "notification_task_id_seq", allocationSize = 1)
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_task_id_seq")
    private Long id;
    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "notification_text", nullable = false)
    private String notificationText;

    @Column(name = "scheduled_time", nullable = false)
    private LocalDateTime scheduledTime;

    @Column(name = "additional_data")
    private String additionalData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getAdditionalData() {
        return additionalData;
    }
    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }
}
