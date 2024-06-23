package com.antoncoco.forohub.domain.courses;

import com.antoncoco.forohub.infrastructure.errors.ObjectAlreadyPersisted;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(CourseForm course) {
        Optional<Course> optionalCourse = this.courseRepository.findByName(course.name());
        if(optionalCourse.isPresent()) {
            throw new ObjectAlreadyPersisted("Course with name " + course.name() + " already exists");
        }
        return this.courseRepository.save(
                Course.builder()
                        .name(course.name())
                        .category(CourseCategory.valueOf(course.category()))
                        .build()
        );
    }
}
