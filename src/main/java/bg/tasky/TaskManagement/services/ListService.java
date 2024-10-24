package bg.tasky.TaskManagement.services;

import bg.tasky.TaskManagement.dtos.ListDto;
import bg.tasky.TaskManagement.entities.BoardEntity;
import bg.tasky.TaskManagement.entities.ListEntity;
import bg.tasky.TaskManagement.mappers.ListMapper;
import bg.tasky.TaskManagement.repositories.ListRepo;
import org.springframework.stereotype.Service;

@Service
public class ListService {
    private final ListRepo listRepo;
    private final ListMapper listMapper;
    private final BoardService boardService;

    public ListService(ListRepo listRepo, ListMapper listMapper, BoardService boardService) {
        this.listRepo = listRepo;
        this.listMapper = listMapper;
        this.boardService = boardService;
    }

    public ListDto createList(String boardKey, ListDto listDto) {
        BoardEntity board = boardService.getBoardEntityByKey(boardKey);

        ListEntity listEntity = listMapper.convertDtoToEntity(listDto);
        String listTitle = listEntity.getTitle();

        boolean exists = listRepo.existsByTitleAndBoardKey(listTitle, board.getKey());
        if (exists) {
            throw new IllegalArgumentException("A list with this title already exists on the board: " + listTitle);
        }

        listEntity.setBoard(board);
        listEntity = listRepo.save(listEntity);

//        board.getLists().add(listEntity);
//        boardService.save(board);

        return listMapper.convertEntityToDto(listEntity);
    }
}
