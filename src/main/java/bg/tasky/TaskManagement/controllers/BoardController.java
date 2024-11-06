package bg.tasky.TaskManagement.controllers;

import bg.tasky.TaskManagement.dtos.BoardDto;
import bg.tasky.TaskManagement.services.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;


    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @PostMapping("/create")
//    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto, @AuthenticationPrincipal UserEntity currentUser){
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto){
        return ResponseEntity.ok(boardService.createBoard(boardDto));
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<BoardDto>> getUserBoards(){
        return ResponseEntity.ok(boardService.getUserBoards());
    }

    @GetMapping("/{key}")
    public ResponseEntity<BoardDto> getBoardByKey(@PathVariable String key) {
        BoardDto boardDto = boardService.getBoardByKey(key);
        return ResponseEntity.ok(boardDto);
    }

    @PutMapping("/update")
    public ResponseEntity<BoardDto> updateBoardTitle(@RequestBody BoardDto boardDto) {
        return ResponseEntity.ok(boardService.updateBoardTitle(boardDto));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<BoardDto> removeBoard(@RequestBody BoardDto boardDto){
        return ResponseEntity.ok(boardService.deleteBoard(boardDto));
    }

    @PutMapping("/user/remove")
    public ResponseEntity<BoardDto> removeBoardForUser(@RequestBody BoardDto boardDto){
        return ResponseEntity.ok(boardService.deleteBoardForUser(boardDto));
    }
}
