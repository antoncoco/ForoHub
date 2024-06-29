package com.antoncoco.forohub.domain.topics;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record TopicForm(
        @NotBlank
        @Length(min = 10, max = 100)
        String title,
        @NotBlank
        String message,
        @NotNull
        Integer courseId
) {
}
