package bg.tasky.TaskManagement.services;

import bg.tasky.TaskManagement.dtos.BoardDto;
import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.entities.BoardEntity;
import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.mappers.BoardMapper;
import bg.tasky.TaskManagement.mappers.UserMapper;
import bg.tasky.TaskManagement.repositories.BoardRepo;
import bg.tasky.TaskManagement.repositories.UserRepo;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class BoardService {

    private final BoardRepo boardRepo;
    private final BoardMapper boardMapper;
    private final UserService userService;

    public BoardService(BoardRepo boardRepo, BoardMapper boardMapper, UserService userService, UserRepo userRepo, UserMapper userMapper) {
        this.boardRepo = boardRepo;
        this.boardMapper = boardMapper;
        this.userService = userService;
    }

    public BoardDto createBoard(BoardDto boardDto) {

        BoardEntity boardEntity = boardMapper.convertDtoToEntity(boardDto);
        boardEntity.setKey(UUID.randomUUID().toString().replace("-", ""));

        UserEntity currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("User not authenticated");
        }

        BoardEntity savedBoard = boardRepo.save(boardEntity);

        currentUser.getBoards().add(savedBoard);
        userService.save(currentUser);

        return boardMapper.convertEntityToDto(savedBoard);
    }

    public List<BoardDto> getUserBoards() {
        UserEntity currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("User not authenticated");
        }

        Set<BoardEntity> userBoards = currentUser.getBoards();

        return userBoards.stream()
                .map(boardMapper::convertEntityToDto)
                .toList();
    }

    public BoardDto getBoardByKey(String key) {
        UserEntity currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("User not authenticated");
        }

        BoardEntity boardEntity = boardRepo.findByKey(key)
                .orElseThrow(() -> new RuntimeException("Board not found with key: " + key));

        // Check if the current user already has this board
        if (!currentUser.getBoards().contains(boardEntity)) {
            currentUser.getBoards().add(boardEntity);
            userService.save(currentUser); // Save the updated user entity
        }

        return boardMapper.convertEntityToDto(boardEntity);
    }


    public BoardDto updateBoardTitle(BoardDto boardDto) {
        BoardEntity boardEntity = boardMapper.convertDtoToEntity(boardDto);
        String newTitle = boardEntity.getTitle();
        boardEntity = boardRepo.findByKey(boardEntity.getKey())
                .orElseThrow(() -> new RuntimeException("Board not found with this key"));

        boardEntity.setTitle(newTitle);

        BoardEntity updatedBoard = boardRepo.save(boardEntity);

        return boardMapper.convertEntityToDto(updatedBoard);
    }


    public BoardDto deleteBoard(BoardDto boardDto) {
        BoardEntity boardEntity = boardMapper.convertDtoToEntity(boardDto);
        boardEntity = boardRepo.findByKey(boardEntity.getKey())
                .orElseThrow(() -> new RuntimeException("Board not found with this key!"));

        List<UserEntity> users = userService.getAllUsersAsEntities();

        for (UserEntity user : users) {
            if (user.getBoards().contains(boardEntity)) {
                user.getBoards().remove(boardEntity); // Remove the board from the user's set of boards
                userService.save(user);      // Save the updated user back to the database
            }
        }

        boardRepo.delete(boardEntity);

        return boardMapper.convertEntityToDto(boardEntity);
    }

    public BoardDto deleteBoardForUser(BoardDto boardDto) {
        UserEntity currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("User not authenticated");
        }
        BoardEntity boardEntity = boardMapper.convertDtoToEntity(boardDto);
        boardEntity = boardRepo.findByKey(boardEntity.getKey())
                .orElseThrow(() -> new RuntimeException("Board not found with this key!"));

        if (currentUser.getBoards().contains(boardEntity)) {
            currentUser.getBoards().remove(boardEntity);
            userService.save(currentUser); // Save the updated user entity
        }

        return boardMapper.convertEntityToDto(boardEntity);
    }
}
