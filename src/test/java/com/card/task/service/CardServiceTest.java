package com.card.task.service;

import com.card.task.domain.Card;
import com.card.task.domain.CardRepositoryWrapper;
import com.card.task.dto.CardRequestDto;
import com.card.task.dto.CardResponseDto;
import com.card.task.dto.CardUpdateRequestDto;
import com.card.task.mapper.CardMapper;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepositoryWrapper cardRepositoryWrapper;
    @Mock
    private CardMapper cardMapper;
    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void createCard() {
        Card card = Instancio.create(Card.class);
        CardRequestDto cardRequestDto = Instancio.create(CardRequestDto.class);
        CardResponseDto cardResponseDto = Instancio.create(CardResponseDto.class);
        when(cardRepositoryWrapper.createCard(any(CardRequestDto.class))).thenReturn(card);
        when(cardMapper.fromEntity(any(Card.class))).thenReturn(cardResponseDto);
        final var cardResponseDtoDto = cardService.createCard(cardRequestDto);
        Assertions.assertThat(cardResponseDtoDto).isNotNull().isEqualTo(cardResponseDto);
    }

    @Test
    void updateCard() {
        Card card = Instancio.create(Card.class);
        CardUpdateRequestDto cardRequestDto = Instancio.create(CardUpdateRequestDto.class);
        CardResponseDto cardResponseDto = Instancio.create(CardResponseDto.class);
        when(cardRepositoryWrapper.updateCard(anyLong(), any(CardUpdateRequestDto.class))).thenReturn(card);
        when(cardMapper.fromEntity(any(Card.class))).thenReturn(cardResponseDto);
        final var cardResponseDtoDto = cardService.updateCard(100001L, cardRequestDto);
        Assertions.assertThat(cardResponseDtoDto).isNotNull().isEqualTo(cardResponseDto);
    }

    @Test
    void findCardById() {
        Card card = Instancio.create(Card.class);
        CardResponseDto cardResponseDto = Instancio.create(CardResponseDto.class);
        when(cardRepositoryWrapper.findCardById(anyLong())).thenReturn(card);
        when(cardMapper.fromEntity(any(Card.class))).thenReturn(cardResponseDto);
        final var cardResponseDtoDto = cardService.findCardById(1L);
        Assertions.assertThat(cardResponseDtoDto).isNotNull().isEqualTo(cardResponseDto);
    }

}