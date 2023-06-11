package com.card.task.service;

import com.card.task.domain.Card;
import com.card.task.dto.CardRequestDto;
import com.card.task.dto.CardResponseDto;
import com.card.task.dto.CardSearchCriteria;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostFilter;

import java.util.List;

public interface CardService {

    CardResponseDto createCard(@Valid CardRequestDto cardRequestDto);

    CardResponseDto updateCard(Long cardId, @Valid CardRequestDto cardRequestDto);

    CardResponseDto findCardById(Long cardId);

    @PostFilter("filterObject.createdBy==authentication.principal.username or hasRole('ROLE_ADMIN')")
    List<CardResponseDto> findAvailableCards(CardSearchCriteria searchCriteria, Pageable pageable);

    void deleteCard(Long cardId);

    CardResponseDto updateCardStatus(Long cardId, Card.CardStatus status);
}
