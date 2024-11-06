package bg.tasky.TaskManagement.controllers;

import bg.tasky.TaskManagement.dtos.ListDto;
import bg.tasky.TaskManagement.services.ListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/list")
public class ListController {

    private final ListService listService;

    public ListController(ListService listService) {
        this.listService = listService;
    }

    @PostMapping("/create/{boardKey}")
    public ResponseEntity<ListDto> createList(@PathVariable String boardKey, @RequestBody ListDto list){
        return ResponseEntity.ok(listService.createList(boardKey, list));
    }

    @GetMapping("/{boardKey}/{title}")
    public ResponseEntity<ListDto> getList(@PathVariable String boardKey, @PathVariable String title){
        return ResponseEntity.ok(listService.getListByTitle(boardKey, title));
    }

    @GetMapping("/{boardKey}/all")
    public ResponseEntity<List<ListDto>> getAllLists(@PathVariable String boardKey){
        return ResponseEntity.ok(listService.getListsByBoard(boardKey));
    }

    @PutMapping("/{boardKey}/{oldTitle}/update")
    public ResponseEntity<ListDto> changeListTitle(
            @PathVariable String boardKey,
            @PathVariable String oldTitle,
            @RequestBody String newTitle) {
        return ResponseEntity.ok(listService.updateListTitle(boardKey, oldTitle, newTitle));
    }

    @DeleteMapping("{boardKey}/{title}/delete")
    public ResponseEntity<ListDto> deleteList(@PathVariable String boardKey, @PathVariable String title) {
        return ResponseEntity.ok(listService.deleteList(boardKey, title));
    }
}
