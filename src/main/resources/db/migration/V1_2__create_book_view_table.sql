CREATE TABLE IF NOT EXISTS T_BOOK_VIEW(
    id bigserial primary key,
    user_email varchar(255) NOT NULL,
    book_id bigint NOT NULL,
    CONSTRAINT fk_book
      FOREIGN KEY (book_id)
      REFERENCES T_BOOK(id)
);