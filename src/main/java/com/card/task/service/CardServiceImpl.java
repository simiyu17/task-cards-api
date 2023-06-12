package com.card.task.service;

import com.card.task.domain.CardRepositoryWrapper;
import com.card.task.dto.CardRequestDto;
import com.card.task.dto.CardResponseDto;
import com.card.task.dto.CardSearchCriteria;
import com.card.task.dto.CardUpdateRequestDto;
import com.card.task.mapper.CardMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@Transactional
public class CardServiceImpl implements CardService{

    private final CardRepositoryWrapper cardRepositoryWrapper;
    private final CardMapper cardMapper;

    public CardServiceImpl(CardRepositoryWrapper cardRepositoryWrapper, CardMapper cardMapper) {
        this.cardRepositoryWrapper = cardRepositoryWrapper;
        this.cardMapper = cardMapper;
    }

    @Override
    public CardResponseDto createCard(@Valid CardRequestDto cardRequestDto) {
        return this.cardMapper.fromEntity(this.cardRepositoryWrapper.createCard(cardRequestDto));
    }

    @Override
    public CardResponseDto updateCard(Long cardId, @Valid CardUpdateRequestDto cardRequestDto) {
        return this.cardMapper.fromEntity(this.cardRepositoryWrapper.updateCard(cardId, cardRequestDto));
    }

    @Transactional(readOnly = true)
    @Override
    public CardResponseDto findCardById(Long cardId) {
        return this.cardMapper.fromEntity(this.cardRepositoryWrapper.findCardById(cardId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CardResponseDto> findAvailableCards(CardSearchCriteria searchCriteria, Pageable pageable) {
        return this.cardMapper.fromEntity(this.cardRepositoryWrapper.findAvailableCards(searchCriteria, pageable).getContent());
    }

    @Override
    public void deleteCard(Long cardId) {
        this.cardRepositoryWrapper.deleteCard(cardId);
    }
}
