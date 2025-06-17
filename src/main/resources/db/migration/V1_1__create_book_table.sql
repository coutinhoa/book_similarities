CREATE TABLE IF NOT EXISTS T_BOOK(
    id bigserial primary key,
    name varchar(255) NOT NULL,
    details text NOT NULL,
    price NUMERIC NOT NULL,
    image text NOT NULL,
    deleted boolean NOT NULL DEFAULT false,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);