create table task (
    id          bigserial primary key,
    title       varchar(255) not null,
    description text,
    status      varchar(50)  not null,
    priority    varchar(50),
    due_date    timestamp,
    created_at  timestamp   default current_timestamp,
    updated_at  timestamp
);

comment on column task.status is 'Status of the task, e.g., TODO, IN_PROGRESS, DONE';
comment on column task.priority is 'Priority of the task, e.g., LOW, MEDIUM, HIGH';
comment on column task.created_at is 'Timestamp when the task was created';
comment on column task.updated_at is 'Timestamp when the task was last updated';