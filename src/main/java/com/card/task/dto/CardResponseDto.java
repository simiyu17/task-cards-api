package com.card.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public record CardResponseDto(
        Long id,
        String taskName,
        String description,
        String color,
        String status,
        String dateCreated,
        @JsonIgnore
        String createdBy
) implements Serializable {

}
