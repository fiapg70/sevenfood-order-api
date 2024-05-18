CREATE TABLE IF NOT EXISTS tb_queue_order (
    id bigserial  PRIMARY KEY,
    message Text,
    create_by varchar(255) not null,
    created_date timestamp(6) not null,
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    status varchar(255) not null
);
