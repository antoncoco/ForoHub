CREATE TABLE IF NOT EXISTS replies(
    reply_id INT AUTO_INCREMENT NOT NULL,
    reply_message TEXT NOT NULL,
    reply_topic INT NOT NULL,
    reply_createdAt DATETIME NOT NULL DEFAULT NOW(),
    reply_author INT NOT NULL,
    PRIMARY KEY (reply_id),
    CONSTRAINT fk_replyTopic FOREIGN KEY (reply_topic) REFERENCES topics(topic_id),
    CONSTRAINT fk_replyAuthor FOREIGN KEY (reply_author) REFERENCES users(user_id)
);