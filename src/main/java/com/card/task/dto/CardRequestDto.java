package com.card.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public record CardRequestDto(
        @Schema(description = "Task identifier",
                example = "Fix Logging")
        @NotBlank(message = "{card.name.missing}")
        String taskName,

        @Schema(description = "Task Description. This is optional",
                example = "Fix Logging display to be more readable")
        String description,

        @Schema(description = "Color to be used. This is optional",
                example = "#C0C0C0")
        @Pattern(regexp = "^(#[a-fA-F0-9]{6}|\\s*)$", message = "{color.code.invalid}")
        String color
) implements Serializable {

}
