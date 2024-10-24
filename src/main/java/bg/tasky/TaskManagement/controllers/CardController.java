package bg.tasky.TaskManagement.controllers;

import bg.tasky.TaskManagement.dtos.CardDto;
import bg.tasky.TaskManagement.dtos.ListDto;
import bg.tasky.TaskManagement.services.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/create/{boardKey}/{listTitle}")
    public ResponseEntity<CardDto> createCard(
            @PathVariable String listTitle,
            @PathVariable String boardKey,
            @RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.createCard(listTitle, boardKey, cardDto));
    }

    @GetMapping("/{boardKey}/{listTitle}/{cardTitle}")
    public ResponseEntity<CardDto> getCard(
            @PathVariable String boardKey,
            @PathVariable String listTitle,
            @PathVariable String cardTitle){
        return ResponseEntity.ok(cardService.getCardByTitle(boardKey, listTitle, cardTitle));
    }

    @GetMapping("/{boardKey}/{listTitle}/all")
    public ResponseEntity<List<CardDto>> getAllCards(@PathVariable String boardKey, @PathVariable String listTitle){
        return ResponseEntity.ok(cardService.getCardsByList(boardKey, listTitle));
    }

    @PatchMapping("/{boardKey}/{listTitle}/{oldTitle}/update")
    public ResponseEntity<CardDto> changeCardTitle(
            @PathVariable String boardKey,
            @PathVariable String listTitle,
            @PathVariable String oldTitle,
            @RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.updateCardTitle(boardKey, listTitle, oldTitle, cardDto));
    }

    @DeleteMapping("{boardKey}/{listTitle}/{cardTitle}/delete")
    public ResponseEntity<CardDto> deleteCard(@PathVariable String boardKey,
                                              @PathVariable String listTitle,
                                              @PathVariable String cardTitle) {
        return ResponseEntity.ok(cardService.deleteCard(boardKey, listTitle, cardTitle));
    }
}
