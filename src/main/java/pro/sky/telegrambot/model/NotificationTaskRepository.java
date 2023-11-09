package pro.sky.telegrambot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {
    @Query("SELECT t FROM notification_task t WHERE t.scheduledTime = :scheduledTime")
    List<NotificationTask> findByScheduledTime(@Param("scheduledTime") LocalDateTime scheduledTime);
}
