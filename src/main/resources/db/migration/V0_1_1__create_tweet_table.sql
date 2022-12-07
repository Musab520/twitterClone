CREATE TABLE `tweet` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    author_id VARCHAR(36),
    title VARCHAR(60),
    message VARCHAR(280),
    created_on Date,
    FOREIGN KEY (author_id) REFERENCES user(id)
);