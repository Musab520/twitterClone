Create TABLE `user` (
    user_id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(512) NOT NULL,
    is_active BIT
    );