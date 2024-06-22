ALTER TABLE topics
ADD COLUMN topic_message TEXT NOT NULL
AFTER topic_title;