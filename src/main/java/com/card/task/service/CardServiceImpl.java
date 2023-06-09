package com.card.task.service;

import com.card.task.domain.CardRepositoryWrapper;
import com.card.task.dto.CardRequestDto;
import com.card.task.dto.CardResponseDto;
import com.card.task.dto.CardSearchCriteria;
import com.card.task.mapper.CardMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService{

    private final CardRepositoryWrapper cardRepositoryWrapper;
    private final CardMapper cardMapper;

    public CardServiceImpl(CardRepositoryWrapper cardRepositoryWrapper, CardMapper cardMapper) {
        this.cardRepositoryWrapper = cardRepositoryWrapper;
        this.cardMapper = cardMapper;
    }

    @Transactional
    @Override
    public CardResponseDto createCard(CardRequestDto cardRequestDto) {
        return this.cardMapper.fromEntity(this.cardRepositoryWrapper.createCard(cardRequestDto));
    }

    @Override
    public CardResponseDto updateCard(Long cardId, CardRequestDto cardRequestDto) {
        return this.cardMapper.fromEntity(this.cardRepositoryWrapper.updateCard(cardId, cardRequestDto));
    }

    @Override
    public CardResponseDto findCardById(Long cardId) {
        return this.cardMapper.fromEntity(this.cardRepositoryWrapper.findCardById(cardId));
    }

    @Override
    public List<CardResponseDto> findAvailableCards(CardSearchCriteria searchCriteria, Pageable pageable) {
        return this.cardMapper.fromEntity(this.cardRepositoryWrapper.findAvailableCards(searchCriteria, pageable).getContent());
    }

    @Transactional
    @Override
    public void deleteCard(Long cardId) {
        this.cardRepositoryWrapper.deleteCard(cardId);
    }
}
