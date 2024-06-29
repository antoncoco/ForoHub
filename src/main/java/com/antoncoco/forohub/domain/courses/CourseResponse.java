package com.antoncoco.forohub.domain.courses;

public record CourseResponse(
        Integer id,
        String title,
        CourseCategory category
) {
    public CourseResponse (Course course){
        this(course.getId(), course.getName(), course.getCategory());
    }
}
