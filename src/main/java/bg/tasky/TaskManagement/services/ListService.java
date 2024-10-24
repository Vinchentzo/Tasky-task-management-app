package bg.tasky.TaskManagement.services;

import bg.tasky.TaskManagement.dtos.ListDto;
import bg.tasky.TaskManagement.entities.BoardEntity;
import bg.tasky.TaskManagement.entities.ListEntity;
import bg.tasky.TaskManagement.mappers.ListMapper;
import bg.tasky.TaskManagement.repositories.ListRepo;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ListDto getListByTitle(String boardKey, String title) {
        return listMapper.convertEntityToDto(listRepo.findByTitleAndBoardKey(title, boardKey));
    }


    public List<ListDto> getListsByBoard(String boardKey) {
        List<ListEntity> listEntities = listRepo.findAllByBoardKey(boardKey);
        return listEntities.stream()
                .map(listMapper::convertEntityToDto)
                .toList();
    }

    public ListDto updateListTitle(String boardKey, String oldTitle, String newTitle){
        ListEntity listEntity = listRepo.findByTitleAndBoardKey(oldTitle, boardKey);
        listEntity.setTitle(newTitle);
        listEntity = listRepo.save(listEntity);
        return listMapper.convertEntityToDto(listEntity);
    }

    public ListDto deleteList(String boardKey, String title) {
        ListEntity listEntity = listRepo.findByTitleAndBoardKey(title, boardKey);
        listRepo.delete(listEntity);
        return listMapper.convertEntityToDto(listEntity);
    }
}
