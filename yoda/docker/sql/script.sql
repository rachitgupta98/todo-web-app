CREATE TABLE IF NOT EXISTS `TODO_TASKS`
(
    `task_id`           INT           AUTO_INCREMENT,
    `task_title`        varchar(255)  NOT NULL,
    `description`       varchar(255),
    `due_date`          date,
    `priority`          ENUM ('HIGH', 'MEDIUM', 'LOW'),
    `is_active`         TINYINT(1)    NOT NULL,
    `is_recurring`      TINYINT(1)    DEFAULT 0,
    `recurrence_type`   VARCHAR(10)   DEFAULT NULL,
    `creation_date`     datetime      DEFAULT CURRENT_TIMESTAMP,
    `updation_date`     datetime      DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`task_id`)
);