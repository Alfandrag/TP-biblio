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

-- ========= 2) Test de remplissage =========

USE Bibliotheque;

-- =========================
-- 1) Catégories d’utilisateurs
-- =========================
INSERT INTO Categories (name, loan_time, loan_number) VALUES
('Étudiant', 21, 3),
('Professeur', 30, 5),
('Gestionnaire', 60, 10);

-- =========================
-- 2) Utilisateurs
-- =========================
INSERT INTO Users (first_name, last_name, email, password, cat) VALUES
('Alice', 'Durand', 'alice.durand@example.com', 'password123', 1),
('Bob', 'Martin', 'bob.martin@example.com', 'password123', 1),
('Claire', 'Dupuis', 'claire.dupuis@example.com', 'password123', 2),
('David', 'Admin', 'david.admin@example.com', 'admin123', 3);

-- =========================
-- 3) Auteurs
-- =========================
INSERT INTO Writers (first_name, last_name, birth_date) VALUES
('Jules', 'Verne', '1828-02-08'),
('George', 'Orwell', '1903-06-25'),
('Jane', 'Austen', '1775-12-16');

-- =========================
-- 4) Livres (œuvres)
-- =========================
INSERT INTO Books (title, writer_id) VALUES
('Vingt mille lieues sous les mers', 1),
('1984', 2),
('Orgueil et Préjugés', 3);

-- =========================
-- 5) Éditions
-- =========================
INSERT INTO Editions (book_id, edition_year, isbn, publisher, number_of_copies) VALUES
(1, 1870, '9782070408502', 'Gallimard', 3),
(2, 1949, '9780451524935', 'Secker & Warburg', 2),
(3, 1813, '9780141439518', 'Penguin Classics', 1);

-- =========================
-- 6) Exemplaires physiques (Book_list)
-- =========================
INSERT INTO Book_list (edition_id, etat, disponible) VALUES
(1, 'bon', TRUE),
(1, 'neuf', TRUE),
(1, 'abîmé', FALSE),
(2, 'neuf', TRUE),
(2, 'bon', FALSE),
(3, 'neuf', TRUE);

-- =========================
-- 7) Catégories de livres (mots-clés ou genres)
-- =========================
INSERT INTO Book_Categories (category) VALUES
('Science-fiction'),
('Classique'),
('Roman'),
('Politique'),
('Aventure');

-- =========================
-- 8) Liens livres ↔ catégories
-- =========================
INSERT INTO Books_cat_link (book_id, book_cat) VALUES
(1, 1),  -- Vingt mille lieues → SF
(1, 5),  -- Vingt mille lieues → Aventure
(2, 4),  -- 1984 → Politique
(2, 2),  -- 1984 → Classique
(3, 3),  -- Orgueil et Préjugés → Roman
(3, 2);  -- Orgueil et Préjugés → Classique

-- =========================
-- 9) Liens livres ↔ auteurs (utile si plusieurs auteurs)
-- =========================
INSERT INTO Book_writer_link (book_id, writer_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- =========================
-- 10) Emprunts
-- =========================
INSERT INTO Log_loans (user_id, book_id, loan_date, return_date) VALUES
(1, 3, '2025-10-01', NULL),   -- Alice emprunte exemplaire 3 (Vingt mille lieues, abîmé)
(2, 4, '2025-09-20', '2025-10-05'),  -- Bob a rendu exemplaire 4 (1984)
(3, 6, '2025-10-10', NULL);   -- Claire emprunte exemplaire 6 (Orgueil et Préjugés)

-- =========================
-- 11) Journal des actions utilisateurs
-- =========================
INSERT INTO Log_users (user_id, action) VALUES
(1, 'borrow'),
(2, 'return'),
(3, 'borrow'),
(2, 'ban');  -- Bob mis en liste rouge



