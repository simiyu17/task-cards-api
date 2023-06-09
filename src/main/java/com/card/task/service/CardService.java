package com.card.task.service;

import com.card.task.dto.CardRequestDto;
import com.card.task.dto.CardResponseDto;
import com.card.task.dto.CardSearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CardService {

    CardResponseDto createCard(CardRequestDto cardRequestDto);

    CardResponseDto updateCard(Long cardId, CardRequestDto cardRequestDto);

    CardResponseDto findCardById(Long cardId);

    List<CardResponseDto> findAvailableCards(CardSearchCriteria searchCriteria, Pageable pageable);

    void deleteCard(Long cardId);
}
