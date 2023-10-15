SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS category; 
DROP TABLE IF EXISTS book; 
DROP TABLE IF EXISTS app_user;
SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE category
(categoryid BIGINT NOT NULL AUTO_INCREMENT,
 name VARCHAR(100) NOT NULL,
 PRIMARY KEY (categoryid)
);

INSERT INTO category (name) 
VALUES ("Scifi"), 
       ("Fantasy"), 
       ("Romance");

CREATE TABLE book (
id BIGINT NOT NULL AUTO_INCREMENT,
 title VARCHAR(250) NOT NULL,
 author VARCHAR(250) NOT NULL,
 isbn VARCHAR(250),
 publication_year INT,
 price DECIMAL(10,2),
 categoryid BIGINT,
 PRIMARY KEY (id),
 FOREIGN KEY (categoryid) REFERENCES category(categoryid)
);

INSERT INTO book (title, author, isbn, publication_year, price, categoryid) 
VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 1925, 10.00, 1), 
       ('To Kill a Mockingbird', 'Harper Lee', '9780061120084', 1960, 15.00, 2);

CREATE TABLE app_user
(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 user_role  VARCHAR(100) NOT NULL,
 username VARCHAR(250) NOT NULL,
 pashword_hash VARCHAR(250) NOT NULL
);

INSERT INTO app_user (username, pashword_hash, user_role) 
VALUES ('user', '$2a$10$1DTvwpXVBArGFixHBuzVJObjTuXhIOkx5pse6KsYs8/C2ckxnGEou', 'USER'), 
       ('admin', '$2a$10$cDZgyF4xaPMmmoRW3OVcmuf.8o2YSx8.M7CeRKqi.1PVw.t3E8uEC', 'ADMIN');

SELECT * FROM book;
SELECT * FROM category;
SELECT * FROM app_user;