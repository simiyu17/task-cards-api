package com.card.task.api;

import com.card.IntegrationTestBase;
import com.card.task.dto.CardRequestDto;
import com.card.task.dto.CardResponseDto;
import com.card.util.AppConstant;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.instancio.Select.field;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CardControllerIntegrationTest extends IntegrationTestBase {

    private static final String BAD_REQUEST_CARD_POST_RESPONSE = "{\"createCard.cardRequestDto.taskName\":\"Task card name is mandatory\"," +
            "\"createCard.cardRequestDto.color\":\"Invalid color provided\"}";


    @Order(1)
    @Test
    @Sql("/sql/insertSampleCardData.sql")
    void given_valid_card_request_to_make_post_request_then_create_card_and_return_card_response() throws Exception {
        setUpUser(AppConstant.DEFAULT_MEMBER_USER_EMAIL_ADDRESS);
        var postCardIdUrl = "/cards";
        var cardRequest = Instancio.of(CardRequestDto.class)
                .set(field("color"), "#808080")
                .create();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(postCardIdUrl)
                .content(objectMapper.writeValueAsString(cardRequest))
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.taskName").value(cardRequest.taskName()))
                .andExpect(jsonPath("$.color").value(cardRequest.color()));
    }

    @Test
    void given_an_invalid_card_request_to_make_post_request_then_throw_exception() throws Exception {
        setUpUser(AppConstant.DEFAULT_MEMBER_USER_EMAIL_ADDRESS);
        var postCardIdUrl = "/cards";
        var cardRequest = Instancio.of(CardRequestDto.class)
                .set(field("taskName"), null)
                .set(field("description"), "Task with no name and invalid color code")
                .set(field("color"), "#7XYZ87654")
                .create();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(postCardIdUrl)
                .content(objectMapper.writeValueAsString(cardRequest))
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.body.violations")
                        .value(BAD_REQUEST_CARD_POST_RESPONSE));
    }


    @Test
    void given_card_id_to_find_card_by_id_and_card_exists_and_user_is_owner_then_return_card_response() {
        setUpUser(AppConstant.DEFAULT_MEMBER_USER_EMAIL_ADDRESS);
        var getCardIdUrl = "/cards/"+10005L;
        ResponseEntity<CardResponseDto> response = this.testRestTemplate.exchange(getCardIdUrl, HttpMethod.GET, new HttpEntity<>(headers), CardResponseDto.class);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).hasFieldOrPropertyWithValue("id", 10005L)
                .hasFieldOrPropertyWithValue("taskName", "Test5 task")
                .hasFieldOrPropertyWithValue("dateCreated", "2023-06-12");
    }

    @Test
    void given_card_id_to_find_card_by_id_and_card_exists_and_user_is_admin_then_return_card_response() {
        setUpUser(AppConstant.DEFAULT_ADMIN_USER_EMAIL_ADDRESS);
        var getCardIdUrl = "/cards/"+10005L;
        ResponseEntity<CardResponseDto> response = this.testRestTemplate.exchange(getCardIdUrl, HttpMethod.GET, new HttpEntity<>(headers), CardResponseDto.class);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).hasFieldOrPropertyWithValue("id", 10005L)
                .hasFieldOrPropertyWithValue("taskName", "Test5 task")
                .hasFieldOrPropertyWithValue("dateCreated", "2023-06-12");
    }

    @Test
    void given_card_id_to_find_card_by_id_and_card_exists_and_user_is_not_owner_and_not_admin_then_throw_Exception() throws Exception {
        setUpUser(AppConstant.DEFAULT_MEMBER_USER_EMAIL_ADDRESS);
        var getCardIdUrl = "/cards/{card-id}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(getCardIdUrl, 10001L)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/problem+json"))
                .andExpect(jsonPath("$.title").value("Not Permitted"))
                .andExpect(jsonPath("$.detail").value(AppConstant.NOT_PERMITTED_RESPONSE_MSG));
    }

    @Test
    void given_admin_user_fetching_for_cards_then_return_all_non_deleted_cards() throws Exception {
        setUpUser(AppConstant.DEFAULT_ADMIN_USER_EMAIL_ADDRESS);
        var getCardsUrl = "/cards";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(getCardsUrl)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(7)))
                .andReturn();
    }

    @Test
    void given_member_user_fetching_for_cards_then_return_all_non_deleted_cards_owned_by_user() throws Exception {
        setUpUser(AppConstant.DEFAULT_MEMBER_USER_EMAIL_ADDRESS);
        var getCardsUrl = "/cards";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(getCardsUrl)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andReturn();
    }

}