create table tb_product (
    id bigserial not null,
    product_id varchar(255) not null,
    product_name varchar(255) not null,
    quantity integer not null,
    price numeric(10,2) not null,
    create_by varchar(255) not null,
    created_date timestamp(6) not null,
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    status varchar(255) not null,
    primary key (id)
);