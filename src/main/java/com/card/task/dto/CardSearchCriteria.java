package com.card.task.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record CardSearchCriteria(
        String name,
        String color,
        String status,
        LocalDate dateCreated,
        LocalDate dateCreatedFrom,
        LocalDate dateCreatedTo

) implements Serializable {
}
