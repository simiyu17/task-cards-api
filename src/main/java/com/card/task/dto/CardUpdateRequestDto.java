package com.card.task.dto;

import com.card.task.domain.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public record CardUpdateRequestDto(
        @Schema(description = "Task identifier",
                example = "Fix Logging")
        String taskName,

        @Schema(description = "Task Description. This is optional",
                example = "Fix Logging display to be more readable")
        String description,

        @Schema(description = "Color to be used. This is optional",
                example = "#C0C0C0")
        @Pattern(regexp = "^(#[a-fA-F0-9]{6}|\\s*)$", message = "{color.code.invalid}")
        String color,

        @Schema(description = "Task Status. Can be one of TODO/IN_PROGRESS/DONE/DELETED",
                example = "IN_PROGRESS")
        Card.CardStatus status
) implements Serializable {

}
