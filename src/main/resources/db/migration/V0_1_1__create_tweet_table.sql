CREATE TABLE `tweet` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(36),
    title VARCHAR(60),
    message VARCHAR(280),
    createdOn Date,
    FOREIGN KEY (user_id) REFERENCES user(id)
);