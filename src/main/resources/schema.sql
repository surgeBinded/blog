-- Create user table
CREATE TABLE IF NOT EXISTS users (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
    );

-- Create article table
CREATE TABLE IF NOT EXISTS article (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    banner_image_url VARCHAR(250),
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
    );

-- Create comment table
CREATE TABLE IF NOT EXISTS comment (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       article_id INT NOT NULL,
                                       parent_comment_id INT,
                                       content TEXT NOT NULL,
                                       likes INT NOT NULL DEFAULT 0,
                                       dislikes INT NOT NULL DEFAULT 0,
                                       deleted BOOLEAN NOT NULL DEFAULT FALSE,
                                       user_id INT NOT NULL,
                                       CONSTRAINT fk_article FOREIGN KEY (article_id) REFERENCES article(id),
    CONSTRAINT fk_parent_comment FOREIGN KEY (parent_comment_id) REFERENCES comment(id),
    CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES users(id)
    );
