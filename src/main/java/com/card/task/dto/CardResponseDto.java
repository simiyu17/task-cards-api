package com.card.task.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record CardResponseDto(
        Long id,
        String taskName,
        String description,
        String color,
        String status,
        LocalDate dateCreated
) implements Serializable {

}
