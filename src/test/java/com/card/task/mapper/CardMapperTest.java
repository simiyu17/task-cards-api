package com.card.task.mapper;

import com.card.task.domain.Card;
import com.card.task.dto.CardResponseDto;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CardMapperTest {

    @Autowired
    private CardMapper cardMapper;

    @Test
    void given_a_card_object_then_return_a_card_response_object() {
        final var card = Instancio.create(Card.class);
        final var cardResponse = this.cardMapper.fromEntity(card);
        Assertions.assertThat(cardResponse).isNotNull().isInstanceOf(CardResponseDto.class);
        Assertions.assertThat(cardResponse.color()).isEqualTo(card.getColor());
        Assertions.assertThat(cardResponse.taskName()).isEqualTo(card.getName());
    }

    @Test
    void given_a_list_of_cards_then_return_a_list_of_card_responses() {
        final var cards = Instancio.ofList(Card.class).size(3).create();
        final var cardResponses = this.cardMapper.fromEntity(cards);
        Assertions.assertThat(cardResponses).isNotNull().isInstanceOf(List.class);
        Assertions.assertThat(cardResponses.get(0).color()).isEqualTo(cards.get(0).getColor());
        Assertions.assertThat(cardResponses.get(0).taskName()).isEqualTo(cards.get(0).getName());
    }
}