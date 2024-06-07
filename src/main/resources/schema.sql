-- Create article table
CREATE TABLE IF NOT EXISTS article (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       title VARCHAR(100) NOT NULL, -- Adjusted to match @Size(max = 100) in Java code
    content TEXT NOT NULL,
    banner_image_url VARCHAR(250), -- Allow NULL as per the Java entity
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Set default to CURRENT_TIMESTAMP
    );

-- Create comment table
CREATE TABLE IF NOT EXISTS comment (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       article_id INT NOT NULL,
                                       parent_comment_id INT,
                                       content TEXT NOT NULL,
                                       likes INT NOT NULL DEFAULT 0, -- Default value for likes
                                       dislikes INT NOT NULL DEFAULT 0, -- Default value for dislikes
                                       deleted BOOLEAN NOT NULL DEFAULT FALSE, -- Default value for deleted
                                       CONSTRAINT fk_article FOREIGN KEY (article_id) REFERENCES article(id), -- Foreign key constraint
    CONSTRAINT fk_parent_comment FOREIGN KEY (parent_comment_id) REFERENCES comment(id) -- Self-referential foreign key
    );