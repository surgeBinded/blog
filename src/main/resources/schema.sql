CREATE TABLE IF NOT EXISTS article (
                                       id INT AUTO_INCREMENT  PRIMARY KEY,
                                       title VARCHAR(250) NOT NULL,
    content TEXT NOT NULL,
    bannerImageUrl TEXT,
    dateCreated TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS comment (
                                       id INT AUTO_INCREMENT  PRIMARY KEY,
                                       articleId INT NOT NULL,
    parentCommentId INT,
    content TEXT NOT NULL,
    likes INT NOT NULL,
    dislikes INT NOT NULL,
    deleted BOOLEAN NOT NULL
    );