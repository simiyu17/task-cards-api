package com.card.task.api;

import com.card.shared.annotations.RestControllerWithOpenAPIAuthentication;
import com.card.task.dto.CardRequestDto;
import com.card.task.dto.CardResponseDto;
import com.card.task.dto.CardSearchCriteria;
import com.card.task.service.CardService;
import com.card.util.PageRequestBuilder;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestControllerWithOpenAPIAuthentication
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping(
            value = "/cards",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CardResponseDto> createCard(@Valid @RequestBody CardRequestDto cardRequestDto) {
        return new ResponseEntity<>(this.cardService.createCard(cardRequestDto), HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/cards/{card-id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable("card-id") Long cardId, @Valid @RequestBody CardRequestDto cardRequestDto) {
        return new ResponseEntity<>(this.cardService.updateCard(cardId, cardRequestDto), HttpStatus.OK);
    }

    @GetMapping(
            value = "/cards/{card-id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CardResponseDto> findCardById(@PathVariable("card-id") Long cardId) {
        return new ResponseEntity<>(this.cardService.findCardById(cardId), HttpStatus.OK);
    }

    @GetMapping(
            value = "/cards",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
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

    @DeleteMapping(
            value = "/cards{card-id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> deleteCard(@PathVariable("card-id") Long cardId) {
        this.cardService.deleteCard(cardId);
        return new ResponseEntity<>("Card Deleted Successfully !!", HttpStatus.OK);
    }
}
