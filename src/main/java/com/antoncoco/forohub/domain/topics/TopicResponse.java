package com.antoncoco.forohub.domain.topics;

import com.antoncoco.forohub.domain.courses.CourseResponse;
import com.antoncoco.forohub.domain.users.UserResponse;

import java.util.Date;

public record TopicResponse(
        String title,
        String message,
        Date createdAt,
        CourseResponse course,
        UserResponse author
) {
    public TopicResponse(Topic topic) {
        this(topic.getTitle(), topic.getMessage(), topic.getCreatedAt(),
                new CourseResponse(topic.getCourse()), new UserResponse(topic.getUser())
        );
    }
}
