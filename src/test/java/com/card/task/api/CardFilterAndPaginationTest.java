package com.card.task.api;

import com.card.IntegrationTestBase;
import com.card.util.AppConstant;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CardFilterAndPaginationTest extends IntegrationTestBase {

    static Stream<Arguments> adminFilteringArgumentSource(){
        return Stream.of(Arguments.of("cardName", "Task", 6),
                Arguments.of("color", "#FFFFFF", 1),
                Arguments.of("cardStatus", "TODO", 2),
                Arguments.of("dateCreated", "2023-04-13", 1),
                Arguments.of("fromDateCreated", "2023-04-01", 6),
                Arguments.of("toDateCreated", "2023-04-15", 2));
    }

    @Order(1)
    @Sql("/sql/insertSampleCardData.sql")
    @ParameterizedTest
    @MethodSource("adminFilteringArgumentSource")
    void given_user_fetching_for_cards_with_filter_params_then_return_all_non_deleted_cards_matching_filters_params(String filterField, String filterValue, int expectedSize) throws Exception {
        setUpUser(AppConstant.DEFAULT_ADMIN_USER_EMAIL_ADDRESS);
        var getCardsUrl = "/cards";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(getCardsUrl)
                .param(filterField, filterValue)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(expectedSize)))
                .andReturn();
    }


    static Stream<Arguments> adminPaginationArgumentSource(){
        return Stream.of(Arguments.of(0, 5, null, 10005L, 5),
                Arguments.of(0, 15, "dateCreated", 10007L, 6),
                Arguments.of(0, 7, "cardStatus", 10003L, 6),
                Arguments.of(0, 10, "-cardStatus", 10001L, 6),
                Arguments.of(0, 3, "color", 10001L, 3),
                Arguments.of(1, 2, "-color", 10003L, 2),
                Arguments.of(3, 1, "name", 10005L, 1),
                Arguments.of(2, 2, "-name", 10002L, 2));
    }

    @ParameterizedTest
    @MethodSource("adminPaginationArgumentSource")
    void given_user_fetching_for_cards_with_pagination_params_then_return_all_non_deleted_cards_matching_the_params(Integer pageNumber, Integer pageSize, String sortingField, Long firstCardId, Integer expectedSize) throws Exception {
        setUpUser(AppConstant.DEFAULT_ADMIN_USER_EMAIL_ADDRESS);
        var getCardsUrl = "/cards";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(getCardsUrl)
                .param("pageNumber", String.valueOf(pageNumber))
                .param("pageSize", String.valueOf(pageSize))
                .param("sortingField", sortingField)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[0].id").value(firstCardId))
                .andExpect(jsonPath("$", hasSize(expectedSize)))
                .andReturn();
    }
}
