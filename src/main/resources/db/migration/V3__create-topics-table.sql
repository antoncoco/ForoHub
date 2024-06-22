CREATE TABLE IF NOT EXISTS topics(
    topic_id INT AUTO_INCREMENT NOT NULL,
    topic_title VARCHAR(100) NOT NULL,
    topic_createdAt DATETIME NOT NULL DEFAULT NOW(),
    topic_status BOOLEAN NOT NULL DEFAULT TRUE,
    topic_author INT NOT NULL,
    topic_course INT NOT NULL,
    PRIMARY KEY (topic_id)
);