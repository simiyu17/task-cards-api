package com.card.task.api;

import com.card.shared.annotations.RestControllerWithOpenAPIAuthentication;
import com.card.task.dto.CardRequestDto;
import com.card.task.dto.CardResponseDto;
import com.card.task.dto.CardSearchCriteria;
import com.card.task.dto.CardUpdateRequestDto;
import com.card.task.service.CardService;
import com.card.util.AppConstant;
import com.card.util.PageRequestBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestControllerWithOpenAPIAuthentication
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(
            summary = "Create a task card.",
            description = SwaggerDocumentationConstant.CREATE_CARD)

    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Bad Credentials")
    @ApiResponse(responseCode = "403", description = "Unauthorized User")
    @PostMapping
    public ResponseEntity<CardResponseDto> createCard(@RequestBody CardRequestDto cardRequestDto) {
        return new ResponseEntity<>(this.cardService.createCard(cardRequestDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a task card by Id (database primary key).",
            description = SwaggerDocumentationConstant.UPDATE_CARD_BY_ID)

    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthenticated User")
    @ApiResponse(responseCode = "403", description = "Unauthorized User")
    @PutMapping("/{card-id}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable(AppConstant.TASK_CARD_ID_PATH_PARAM) Long cardId, @RequestBody CardUpdateRequestDto cardRequestDto) {
        return new ResponseEntity<>(this.cardService.updateCard(cardId, cardRequestDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Find a task card by Id (database primary key).",
            description = SwaggerDocumentationConstant.FIND_CARD_BY_ID_DESC)

    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthenticated User")
    @ApiResponse(responseCode = "403", description = "Unauthorized User")
    @GetMapping("/{card-id}")
    public ResponseEntity<CardResponseDto> findCardById(@PathVariable(AppConstant.TASK_CARD_ID_PATH_PARAM) Long cardId) {
        return new ResponseEntity<>(this.cardService.findCardById(cardId), HttpStatus.OK);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve and search for task cards.",
            description = SwaggerDocumentationConstant.CARDS_SEARCH_AND_FILTER_DESC)

            @ApiResponse(responseCode = "200", description = "OK")
            @ApiResponse(responseCode = "401", description = "Unauthenticated User")
            @ApiResponse(responseCode = "403", description = "Unauthorized User")
    public ResponseEntity<List<CardResponseDto>> findAvailableCards(
            @Schema(description = "Use this to get all cards that contain the filter text in their name")
            @RequestParam(required = false) String cardName,
            @Schema(description = "Color filter for example #C0C0C0")
            @RequestParam(required = false) String color,
            @Schema(description = "Can only be one of TODO/IN_PROGRESS/DONE")
            @RequestParam(required = false) String cardStatus,
            @Schema(description = "All cards created on a specific date in yyyy-MM-dd format for example created on 2023-06-13")
            @RequestParam(required = false) LocalDate dateCreated,
            @Schema(description = "All cards created on and after a specified date in yyyy-MM-dd format for example created on/after 2023-06-13")
            @RequestParam(required = false) LocalDate fromDateCreated,
            @Schema(description = "All cards created on and before a specified date in yyyy-MM-dd format for example created on/before 2023-06-13")
            @RequestParam(required = false) LocalDate toDateCreated,
            @Schema(description = "It is defaulted to 0(Or first page of the cards)")
            @RequestParam(required = false) Integer pageNumber,
            @Schema(description = "It is defaulted to 10(used if pageSize < 1) records per page and a max of 30 records per page")
            @RequestParam(required = false) Integer pageSize,
            @Schema(description = "Can only be sorted by one of name/-name/color/-color/cardStatus/-cardStatus/dateCreated/-dateCreated, hyphen(-) before a filter means in descending order")
            @RequestParam(required = false, defaultValue = "-dateCreated") String sortingField
    ) {
        final var searchCriteria = new CardSearchCriteria(cardName, color, cardStatus, dateCreated, fromDateCreated, toDateCreated);
        final var pageRequest = PageRequestBuilder.getPageRequest(pageSize, pageNumber, sortingField);
        return new ResponseEntity<>(this.cardService.findAvailableCards(searchCriteria, pageRequest), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a task card by Id (database primary key)",
            description = SwaggerDocumentationConstant.CARD_DELETE_DESC)

    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthenticated User")
    @ApiResponse(responseCode = "403", description = "Unauthorized User")
    @DeleteMapping("/{card-id}")
    public ResponseEntity<String> deleteCard(@PathVariable(AppConstant.TASK_CARD_ID_PATH_PARAM) Long cardId) {
        this.cardService.deleteCard(cardId);
        return new ResponseEntity<>("Card Deleted Successfully !!", HttpStatus.OK);
    }
}
