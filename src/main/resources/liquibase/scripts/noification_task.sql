CREATE TABLE IF NOT EXISTS notification_task (
     id BIGINT NOT NULL PRIMARY KEY,
     chat_id BIGINT NOT NULL,
     notification_text TEXT NOT NULL,
     scheduled_time TIMESTAMP NOT NULL,
     additional_data VARCHAR(255)
);

create sequence notification_task_id_seq as integer;