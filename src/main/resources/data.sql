-- Insert Authors
INSERT INTO authors (first_name, last_name, username, password, email, deleted) VALUES
    ('John', 'Doe', 'johndoe', '$2a$10$mZeBfa4tnJNdVP9BOAZovuIABX1ZdZsyfOEAzrfms83UYWSZj4u8O', 'johndoe@example.com', false);

INSERT INTO authors (first_name, last_name, username, password, email, deleted) VALUES
    ('Jane', 'Smith', 'janesmith', '$2a$10$Y6kLmXBPc56L0IwBJ207EujvKnwf85N6XfNpoYNrxfsA1ihpKAVM.', 'janesmith@example.com', false);

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

-- Insert roles (unchanged)
INSERT INTO roles (name) VALUES
                             ('ROLE_USER'),
                             ('ROLE_ADMIN');

-- Assign roles to authors (unchanged)
INSERT INTO author_roles (author_id, role_id) VALUES
                                                  (1, 1), -- John Doe is a USER
                                                  (1, 2), -- John Doe is also an ADMIN
                                                  (2, 1); -- Jane Smith is a USER
