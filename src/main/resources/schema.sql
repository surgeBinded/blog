CREATE TABLE IF NOT EXISTS article (
                                       id INT AUTO_INCREMENT  PRIMARY KEY,
                                       title VARCHAR(250) NOT NULL,
    content TEXT NOT NULL,
    banner_image_url VARCHAR(250) NOT NULL,
    date_created TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS comment (
                                       id INT AUTO_INCREMENT  PRIMARY KEY,
                                       article_id INT NOT NULL,
    parent_comment_id INT,
    content TEXT NOT NULL,
    likes INT NOT NULL,
    dislikes INT NOT NULL,
    deleted BOOLEAN NOT NULL
    );