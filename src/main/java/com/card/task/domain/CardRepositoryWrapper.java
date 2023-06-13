package com.card.task.domain;

import com.card.task.dto.CardRequestDto;
import com.card.task.dto.CardSearchCriteria;
import com.card.task.dto.CardUpdateRequestDto;
import com.card.task.exception.CardNotFoundException;
import com.card.task.query.CardQueryBuilder;
import com.card.util.ApplicationUtility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CardRepositoryWrapper {

    private final CardRepository cardRepository;
    private final CardQueryBuilder cardQueryBuilder;
    private final ApplicationUtility applicationUtility;

    public CardRepositoryWrapper(CardRepository cardRepository, CardQueryBuilder cardQueryBuilder, ApplicationUtility applicationUtility) {
        this.cardRepository = cardRepository;
        this.cardQueryBuilder = cardQueryBuilder;
        this.applicationUtility = applicationUtility;
    }

    public Card createCard(CardRequestDto cardRequestDto) {
        var card = Card.createcard(cardRequestDto);
        card.setCreatedBy(applicationUtility.getCurrentLoggedInUser());
        return this.cardRepository.save(card);
    }


    public Card updateCard(Long cardId, CardUpdateRequestDto cardRequestDto) {
        var card = this.findCardById(cardId);
        card.updateCard(cardRequestDto);
        return this.cardRepository.save(card);
    }

    public void updateCardStatus(Long cardId, Card.CardStatus status) {
        var card = this.findCardById(cardId);
        card.setCardStatus(status);
        this.cardRepository.save(card);
    }

    @Transactional(readOnly = true)
    public Card findCardById(Long cardId) {
        return this.cardRepository.findById(cardId)
                .filter(c -> !Card.CardStatus.DELETED.equals(c.getCardStatus()))
                .orElseThrow(() -> new CardNotFoundException(cardId));
    }

    @Transactional(readOnly = true)
    public Page<Card> findAvailableCards(CardSearchCriteria searchCriteria, Pageable pageable) {
        return this.cardRepository.findAll(this.cardQueryBuilder.buildPredicate(searchCriteria), pageable);
    }

    public void deleteCard(Long cardId) {
        this.updateCardStatus(cardId, Card.CardStatus.DELETED);
    }
}
