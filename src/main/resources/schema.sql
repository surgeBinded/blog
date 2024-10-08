-- Create author table
CREATE TABLE IF NOT EXISTS authors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create article table
CREATE TABLE IF NOT EXISTS article (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    banner_image_url VARCHAR(250),
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    author_id INT NOT NULL,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors(id)
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
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    author_id INT NOT NULL,
    CONSTRAINT fk_article FOREIGN KEY (article_id) REFERENCES article(id),
    CONSTRAINT fk_parent_comment FOREIGN KEY (parent_comment_id) REFERENCES comment(id),
    CONSTRAINT fk_comment_author FOREIGN KEY (author_id) REFERENCES authors(id)
);

-- Create roles table
CREATE TABLE IF NOT EXISTS roles (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(50) NOT NULL UNIQUE
);

-- Create author_roles table to represent the many-to-many relationship between authors and roles
CREATE TABLE IF NOT EXISTS author_roles (
                                            author_id INT NOT NULL,
                                            role_id INT NOT NULL,
                                            CONSTRAINT fk_author_role_author FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE,
                                            CONSTRAINT fk_author_role_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
                                            PRIMARY KEY (author_id, role_id)
);

CREATE TABLE IF NOT EXISTS jwt_secret (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          secret_key VARCHAR(255) NOT NULL
);