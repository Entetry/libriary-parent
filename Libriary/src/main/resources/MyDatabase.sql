CREATE database DbOne;
CREATE TABLE book(
book_id SERIAL PRIMARY KEY,
author varchar(50)  NOT NULL,
bookname varchar(50),
dateadd timestamp NOT NULL,
numberofpages integer NOT NULL);
CREATE TABLE users(
user_id SERIAL PRIMARY KEY,
dob TIMESTAMP NOT NULL,
email VARCHAR(355) NOT NULL,
firstname VARCHAR(50) NOT NULL,
username VARCHAR(50) NOT NULL,
lastname VARCHAR(50) NOT NULL,
password VARCHAR(50) NOT NULL,
enabled BOOLEAN NOT NULL,
wallet_id INTEGER,
CONSTRAINT fk_wallet FOREIGN KEY(wallet_id) REFERENCES wallet(wallet_id)
);
CREATE TABLE user_book(
user_id integer NOT NULL,
book_id integer NOT NULL,
 PRIMARY KEY (user_id,book_id),
  CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book (book_id)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id) 
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
CREATE TABLE wallet(
wallet_id SERIAL PRIMARY KEY,
curency VARCHAR(20) NOT NULL,
balance NUMERIC(19,2) NOT NULL
)

CREATE TABLE genre(
genre_id SERIAL PRIMARY KEY,
genre_name VARCHAR(50) NOT NULL
);
CREATE TABLE subgenre(
subgenre_id SERIAL PRIMARY KEY,
genre_id integer,
subgenre_name VARCHAR(50) NOT NULL,
FOREIGN KEY(genre_id) REFERENCES genre (genre_id)
);
CREATE TABLE book_subgenre(
book_id integer NOT NULL,
subgenre_id integer NOT NULL,
 PRIMARY KEY (book_id,subgenre_id),
  CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book (book_id)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_subgenre FOREIGN KEY (subgenre_id) REFERENCES subgenre (subgenre_id) 
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
CREATE TABLE role(
role_id SERIAL PRIMARY KEY,
role_name VARCHAR(50) NOT NULL
);
CREATE TABLE user_role(
user_id integer NOT NULL,
role_id integer NOT NULL,
 PRIMARY KEY (user_id,role_id),
  CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role (role_id)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id) 
      ON UPDATE NO ACTION ON DELETE NO ACTION);
CREATE TABLE orders(
order_id SERIAL PRIMARY KEY,
user_id integer NOT NULL,
order_date TIMESTAMP NOT NULL,
price integer NOT NULL,
 CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);
CREATE TABLE order_book(
order_id integer NOT NULL,
book_id integer NOT NULL,
 PRIMARY KEY (order_id,book_id),
  CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book (book_id)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders (order_id) 
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
CREATE TABLE authority(
authority_id SERIAL PRIMARY KEY,
authority_name VARCHAR(50) NOT NULL
);
CREATE TABLE role_authority(
role_id integer NOT NULL,
authority_id integer NOT NULL,
 PRIMARY KEY (role_id,authority_id),
  CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role (role_id)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_authority FOREIGN KEY (authority_id) REFERENCES authority (authority_id) 
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
CREATE TYPE userstatus AS ENUM ('Basic', 'Invalid');