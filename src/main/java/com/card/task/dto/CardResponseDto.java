package com.card.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
