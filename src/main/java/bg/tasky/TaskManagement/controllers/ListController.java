package bg.tasky.TaskManagement.controllers;

import bg.tasky.TaskManagement.dtos.ListDto;
import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.services.ListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //getByTitle
    //getALl
    //changeTitle
    //delete
}
