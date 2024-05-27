INSERT INTO article (title, content, banner_image_url, date_created) VALUES
    ('First Article', 'This is the content of the first article', 'example.com/image1.jpg', CURRENT_TIMESTAMP);

INSERT INTO article (title, content, banner_image_url, date_created) VALUES
    ('Second Article', 'This is the content of the second article', 'example.com/image2.jpg', CURRENT_TIMESTAMP);

INSERT INTO comment (article_id, parent_comment_id, content, likes, dislikes, deleted) VALUES
    (1, NULL, 'This is a comment on the first article', 0, 0, false);

INSERT INTO comment (article_id, parent_comment_id, content, likes, dislikes, deleted) VALUES
    (1, 1, 'This is a subcomment on the first comment', 0, 0, false);