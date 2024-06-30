package com.antoncoco.forohub.domain.topics;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdateTopicForm(
        @NotBlank
        @Length(min = 10, max = 100)
        String title,
        @NotBlank
        String message
) {
}
