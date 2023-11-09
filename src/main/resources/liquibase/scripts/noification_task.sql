CREATE TABLE IF NOT EXISTS notification_task (
                        id BIGINT NOT NULL,
                        chat_id VARCHAR(255) NOT NULL,
                        notification_text TEXT NOT NULL,
                        scheduled_time TIMESTAMP NOT NULL,
                        additional_data VARCHAR(255),
                        PRIMARY KEY (id)
);
