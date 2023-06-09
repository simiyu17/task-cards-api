package com.card.task.domain;

import com.card.task.dto.CardRequestDto;
import com.card.task.dto.CardSearchCriteria;
import com.card.task.exception.CardNotFoundException;
import com.card.task.query.CardQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CardRepositoryWrapper {

    private final CardRepository cardRepository;
    private final CardQueryBuilder cardQueryBuilder;

    public CardRepositoryWrapper(CardRepository cardRepository, CardQueryBuilder cardQueryBuilder) {
        this.cardRepository = cardRepository;
        this.cardQueryBuilder = cardQueryBuilder;
    }

    @Transactional
    public Card createCard(CardRequestDto cardRequestDto) {
        return this.cardRepository.save(Card.createcard(cardRequestDto));
    }

    @Transactional
    public Card updateCard(Long cardId, CardRequestDto cardRequestDto) {
        final var card = this.findCardById(cardId);
        card.updateCard(cardRequestDto);
        return this.cardRepository.save(card);
    }

    public Card findCardById(Long cardId) {
        return this.cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(String.format("No Card Found With ID %d", cardId)));
    }

    public Page<Card> findAvailableCards(CardSearchCriteria searchCriteria, Pageable pageable) {
        return this.cardRepository.findAll(this.cardQueryBuilder.buildPredicate(searchCriteria), pageable);
    }

    @Transactional
    public void deleteCard(Long cardId) {
        final var card = this.findCardById(cardId);
        card.setCardStatus(Card.CardStatus.DELETED);
    }
}
