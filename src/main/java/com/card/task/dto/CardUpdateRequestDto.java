package com.card.task.dto;

import com.card.task.domain.Card;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public record CardUpdateRequestDto(
        String taskName,
        String description,

        @Pattern(regexp = "^(#[a-fA-F0-9]{6}|\\s*)$", message = "{color.code.invalid}")
        String color,
        Card.CardStatus status
) implements Serializable {

}
