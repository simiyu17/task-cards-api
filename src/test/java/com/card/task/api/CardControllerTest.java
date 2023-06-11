package com.card.task.api;

import com.card.UnitTestBase;
import com.card.task.dto.CardResponseDto;
import com.card.task.dto.CardSearchCriteria;
import com.card.task.service.CardService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CardController.class)
class CardControllerTest extends UnitTestBase {

    @MockBean
    private CardService cardService;

    @Autowired
    private MockMvc mockMvc;

    private static final CardResponseDto cardResponse = Instancio.create(CardResponseDto.class);

    @Test
    void findCardById() throws Exception {
        when(cardService.findCardById(anyLong())).thenReturn(cardResponse);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cards/{card-id}", 111L)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.taskName").value(cardResponse.taskName()))
                .andExpect(jsonPath("$.dateCreated").value(cardResponse.dateCreated()));
        verify(cardService, atLeast(1)).findCardById(111L);
    }

    @Test
    void findAvailableCards() throws Exception {
        final var cardResponseDtos = Instancio.ofList(CardResponseDto.class).size(10).create();
        when(cardService.findAvailableCards(any(CardSearchCriteria.class), any(Pageable.class))).thenReturn(cardResponseDtos);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cards")
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(cardResponseDtos)));
    }

}