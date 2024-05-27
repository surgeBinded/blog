INSERT INTO article (title, content, bannerImageUrl, dateCreated) VALUES
('First Article', 'This is the content of the first article', 'http://example.com/image1.jpg', CURRENT_TIMESTAMP);

INSERT INTO article (title, content, bannerImageUrl, dateCreated) VALUES
('Second Article', 'This is the content of the second article', 'http://example.com/image2.jpg', CURRENT_TIMESTAMP);


INSERT INTO comment (articleId, parentCommentId, content, likes, dislikes, deleted) VALUES
    (1, NULL, 'This is a comment on the first article', 0, 0, false);

INSERT INTO comment (articleId, parentCommentId, content, likes, dislikes, deleted) VALUES
    (1, 1, 'This is a subcomment on the first comment', 0, 0, false);