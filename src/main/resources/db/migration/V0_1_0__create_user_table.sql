Create TABLE `user` (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(32),
    email VARCHAR(512) NOT NULL,
    is_active BIT,
    profile_pic_url VARCHAR(512)
    );