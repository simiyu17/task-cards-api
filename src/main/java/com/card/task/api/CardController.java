package com.card.task.api;

import com.card.shared.annotations.RestControllerWithOpenAPIAuthentication;
import com.card.task.domain.Card;
import com.card.task.dto.CardRequestDto;
import com.card.task.dto.CardResponseDto;
import com.card.task.dto.CardSearchCriteria;
import com.card.task.service.CardService;
import com.card.util.AppConstant;
import com.card.util.PageRequestBuilder;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping
    public ResponseEntity<CardResponseDto> createCard(@RequestBody CardRequestDto cardRequestDto) {
        return new ResponseEntity<>(this.cardService.createCard(cardRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{card-id}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable(AppConstant.TASK_CARD_ID_PATH_PARAM) Long cardId, @RequestBody CardRequestDto cardRequestDto) {
        return new ResponseEntity<>(this.cardService.updateCard(cardId, cardRequestDto), HttpStatus.OK);
    }

    @PutMapping("/{card-id}/status")
    public ResponseEntity<CardResponseDto> updateCardStatus(@PathVariable(AppConstant.TASK_CARD_ID_PATH_PARAM) Long cardId, @RequestBody Card.CardStatus status) {
        return new ResponseEntity<>(this.cardService.updateCardStatus(cardId, status), HttpStatus.OK);
    }

    @GetMapping("/{card-id}")
    public ResponseEntity<CardResponseDto> findCardById(@PathVariable(AppConstant.TASK_CARD_ID_PATH_PARAM) Long cardId) {
        return new ResponseEntity<>(this.cardService.findCardById(cardId), HttpStatus.OK);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve client accounts overview",
            description = """
                    Search for task cards
                    """)

            @ApiResponse(responseCode = "200", description = "OK")
            @ApiResponse(responseCode = "400", description = "Bad Request")
            @ApiResponse(responseCode = "403", description = "Unauthorized User")
    public ResponseEntity<List<CardResponseDto>> findAvailableCards(@RequestParam(required = false) String cardName,
                                                                    @RequestParam(required = false) String color,
                                                                    @RequestParam(required = false) String cardStatus,
                                                                    @RequestParam(required = false) LocalDate dateCreated,
                                                                    @RequestParam(required = false) LocalDate fromDateCreated,
                                                                    @RequestParam(required = false) LocalDate toDateCreated,
                                                                    @RequestParam(required = false) Integer pageNumber,
                                                                    @RequestParam(required = false) Integer pageSize,
                                                                    @RequestParam(required = false, defaultValue = "-dateCreated") String sortingField
    ) {
        final var searchCriteria = new CardSearchCriteria(cardName, color, cardStatus, dateCreated, fromDateCreated, toDateCreated);
        final var pageRequest = PageRequestBuilder.getPageRequest(pageSize, pageNumber, sortingField);
        return new ResponseEntity<>(this.cardService.findAvailableCards(searchCriteria, pageRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{card-id}")
    public ResponseEntity<String> deleteCard(@PathVariable(AppConstant.TASK_CARD_ID_PATH_PARAM) Long cardId) {
        this.cardService.deleteCard(cardId);
        return new ResponseEntity<>("Card Deleted Successfully !!", HttpStatus.OK);
    }
}
