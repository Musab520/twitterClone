CREATE TABLE `follower` (
    id INT PRIMARY KEY,
    followedId VARCHAR(36),
    followerId VARCHAR(36)
);