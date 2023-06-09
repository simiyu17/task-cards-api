package com.card.task.mapper;

import com.card.task.domain.Card;
import com.card.task.dto.CardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardMapper {

    @Mapping(target = "id", expression = "java(card.getId())")
    @Mapping(target = "status", expression = "java(card.getCardStatusOrNull())")
    @Mapping(target = "dateCreated", expression = "java(card.getDateCreated())")
    @Mapping(source = "name", target = "taskName")
    CardResponseDto fromEntity(Card card);

    List<CardResponseDto> fromEntity(List<Card> card);

}
