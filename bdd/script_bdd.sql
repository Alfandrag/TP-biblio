CREATE DATABASE IF NOT EXISTS Bibliotheque;
USE Bibliotheque;

-- ========= 1) Tables =========
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Categories;
DROP TABLE IF EXISTS Writers;
DROP TABLE IF EXISTS Editions;
DROP TABLE IF EXISTS Books;
DROP TABLE IF EXISTS Book_Categories;
DROP TABLE IF EXISTS Log_users;
DROP TABLE IF EXISTS Log_loans;
DROP TABLE IF EXISTS Books_cat_link;
DROP TABLE IF EXISTS Book_writer_link;


CREATE TABLE Categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    loan_time INT NOT NULL,
    loan_number INT NOT NULL,
)

CREATE TABLE Users(
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    cat INT,
    FOREIGN KEY (cat) REFERENCES Categories(id)
)

CREATE TABLE Writers(
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE
)

CREATE TABLE Books(
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    writer_id INT,
    FOREIGN KEY (writer_id) REFERENCES Writers(id)
)

CREATE TABLE Editions(
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    FOREIGN KEY (book_id) REFERENCES Books(id),
    edition_year YEAR,
    isbn VARCHAR(20) UNIQUE,
    publisher VARCHAR(100),
    available_copies INT DEFAULT 0
)


CREATE TABLE Book_Categories(
    id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(200)
)

CREATE TABLE Log_users(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    action VARCHAR(250),
    action_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)

CREATE TABLE Log_loans(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    book_id INT,
    FOREIGN KEY (book_id) REFERENCES Books(id),
    loan_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP DEFAULT NULL
)

CREATE TABLE Books_cat_link(
    book_id INT,
    book_cat INT,
    primary key (book_id, book_cat),
    FOREIGN KEY (book_id) REFERENCES Books(id),
    FOREIGN KEY (book_cat) REFERENCES Book_Categories(id)
)

CREATE TABLE Book_writer_link(
    book_id INT,
    writer_id INT,
    primary key (book_id, writer_id),
    FOREIGN KEY (book_id) REFERENCES Books(id),
    FOREIGN KEY (writer_id) REFERENCES Writers(id)
)




