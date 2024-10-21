package bg.tasky.TaskManagement.services;

import bg.tasky.TaskManagement.dtos.BoardDto;
import bg.tasky.TaskManagement.entities.BoardEntity;
import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.mappers.BoardMapper;
import bg.tasky.TaskManagement.repositories.BoardRepo;
import bg.tasky.TaskManagement.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class BoardService {

    private final BoardRepo boardRepo;
    private final BoardMapper boardMapper;
    private final UserService userService;

    public BoardService(BoardRepo boardRepo, BoardMapper boardMapper, UserService userService, UserRepo userRepo) {
        this.boardRepo = boardRepo;
        this.boardMapper = boardMapper;
        this.userService = userService;
    }

    public BoardDto createBoard(BoardDto boardDto) {

        BoardEntity boardEntity = boardMapper.convertDtoToEntity(boardDto);

        UserEntity currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("User not authenticated");
        }

        //boardEntity.setUsers(new HashSet<>());
        //boardEntity.getUsers().add(currentUser);
        BoardEntity savedBoard = boardRepo.save(boardEntity);

        currentUser.getBoards().add(savedBoard);
        userService.save(currentUser);

        return boardMapper.convertEntityToDto(savedBoard);
    }
}
