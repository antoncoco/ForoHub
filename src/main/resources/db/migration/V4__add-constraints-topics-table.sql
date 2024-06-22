ALTER TABLE topics
    ADD CONSTRAINT fk_topicAuthor
        FOREIGN KEY (topic_author)
            REFERENCES users(user_id);

ALTER TABLE topics
    ADD CONSTRAINT fk_topicCourse
        FOREIGN KEY (topic_course)
            REFERENCES courses(course_id);