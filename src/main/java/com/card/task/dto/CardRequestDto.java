package com.card.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public record CardRequestDto(
        @NotBlank(message = "{card.name.missing}")
        String taskName,
        String description,

        @Pattern(regexp = "^#([a-fA-F0-9]{6})$", message = "{color.code.invalid}")
        String color
) implements Serializable {

}
