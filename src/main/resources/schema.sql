CREATE TABLE IF NOT EXISTS article (
                                       id INT AUTO_INCREMENT  PRIMARY KEY,
                                       title VARCHAR(250) NOT NULL,
    content TEXT NOT NULL,
    bannerImageUrl TEXT,
    dateCreated TIMESTAMP
    );