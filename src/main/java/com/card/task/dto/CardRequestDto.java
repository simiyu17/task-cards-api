package com.card.task.dto;

import java.io.Serializable;

public record CardRequestDto(
        String taskName,
        String description,
        String color
) implements Serializable {

}
