CREATE DATABASE IF NOT EXISTS BlogApp;

USE BlogApp;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    about VARCHAR(255),
    email VARCHAR(255),
    user_name VARCHAR(100),
    password VARCHAR(255),
    role ENUM('USER', 'ADMIN')
);

CREATE TABLE IF NOT EXISTS categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    title VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS post (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    add_date DATETIME(6),
    content VARCHAR(1000),
    image_name VARCHAR(255),
    post_title VARCHAR(100) NOT NULL,
    category_id INT,
    user_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS comments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255),
    post_id INT,
    FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO categories (description, title) VALUES 
('All about technology', 'Technology'),
('Self improvement and help', 'Self Help'),
('News and updates about sports', 'Sports'),
('Travel guides and experiences', 'Travel'),
('Delicious food recipes and tips', 'Food'),
('Fitness routines and health tips', 'Fitness');

