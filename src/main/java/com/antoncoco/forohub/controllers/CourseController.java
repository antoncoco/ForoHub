package com.antoncoco.forohub.controllers;

import com.antoncoco.forohub.domain.courses.Course;
import com.antoncoco.forohub.domain.courses.CourseForm;
import com.antoncoco.forohub.domain.courses.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @Operation(summary = "Insert a new course into the database")
    public ResponseEntity<Course> addCourse(
            @RequestBody @Valid CourseForm newCourse,
            UriComponentsBuilder uriBuilder) {
        Course course = this.courseService.addCourse(newCourse);
        URI uri = uriBuilder.path("/courses/{id}").buildAndExpand(course.getId()).toUri();
        return ResponseEntity.created(uri).body(course);
    }
}
