-- Insert authors
INSERT INTO authors (first_name, last_name, username, password, email, deleted) VALUES
    ('John', 'Doe', 'johndoe', 'password1', 'johndoe@example.com', false);

INSERT INTO authors (first_name, last_name, username, password, email, deleted) VALUES
    ('Jane', 'Smith', 'janesmith', 'password2', 'janesmith@example.com', false);

-- Insert Articles
INSERT INTO article (title, content, banner_image_url, date_updated, date_created, author_id) VALUES
    ('First Article', 'This is the content of the first article', 'example.com/image1.jpg', null, CURRENT_TIMESTAMP, 1);

INSERT INTO article (title, content, banner_image_url, date_updated, date_created, author_id) VALUES
    ('Second Article', 'This is the content of the second article', 'example.com/image2.jpg', null, CURRENT_TIMESTAMP, 2);

-- Insert Comments
INSERT INTO comment (article_id, parent_comment_id, content, likes, dislikes, deleted, date_updated, date_created, author_id) VALUES
    (1, NULL, 'This is a comment on the first article', 0, 0, false, null, CURRENT_TIMESTAMP, 1);

INSERT INTO comment (article_id, parent_comment_id, content, likes, dislikes, deleted, date_updated, date_created, author_id) VALUES
    (1, 1, 'This is a subcomment on the first comment', 0, 0, false, null, CURRENT_TIMESTAMP, 2);
