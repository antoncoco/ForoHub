CREATE TABLE IF NOT EXISTS users(
    user_id INT AUTO_INCREMENT NOT NULL,
    user_name VARCHAR(35) NOT NULL,
    user_email VARCHAR(255) UNIQUE NOT NULL,
    user_password VARCHAR(72) NOT NULL,
    user_biography TINYTEXT NULL,
    PRIMARY KEY (user_id)
);