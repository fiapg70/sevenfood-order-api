create table tb_order (
    id bigserial not null,
    code varchar(255) not null,
    client_id varchar(255) not null,
    status_pedido varchar(255) not null,
    total_price numeric(10,2) not null,
    create_by varchar(255) not null,
    created_date timestamp(6) not null,
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    status varchar(255) not null,
    primary key (id)
);