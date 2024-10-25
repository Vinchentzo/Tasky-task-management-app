package bg.tasky.TaskManagement.services;

import bg.tasky.TaskManagement.dtos.TaskDto;
import bg.tasky.TaskManagement.entities.CardEntity;
import bg.tasky.TaskManagement.entities.ListEntity;
import bg.tasky.TaskManagement.entities.TaskEntity;
import bg.tasky.TaskManagement.mappers.TaskMapper;
import bg.tasky.TaskManagement.repositories.TaskRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepo taskRepo;
    private final TaskMapper taskMapper;
    private final CardService cardService;

    public TaskService(TaskRepo taskRepo, TaskMapper taskMapper, CardService cardService) {
        this.taskRepo = taskRepo;
        this.taskMapper = taskMapper;
        this.cardService = cardService;
    }

    public TaskDto createTask(String listTitle, String boardKey, String cardTitle, TaskDto taskDto) {
        CardEntity cardEntity = cardService.getCardEntityByTitle(boardKey, listTitle, cardTitle);

        TaskEntity taskEntity = taskMapper.convertDtoToEntity(taskDto);
        String taskTitle = taskEntity.getTitle();

        boolean exists = taskRepo.existsByTitleAndCard(taskTitle, cardEntity);
        if (exists) {
            throw new IllegalArgumentException("A task with this title already exists on the card: " + taskTitle);
        }

        taskEntity.setCard(cardEntity);
        taskEntity = taskRepo.save(taskEntity);

        return taskMapper.convertEntityToDto(taskEntity);
    }


    public TaskDto getTaskByTitle(String boardKey, String listTitle, String cardTitle, String taskTitle) {
        CardEntity cardEntity = cardService.getCardEntityByTitle(boardKey, listTitle, cardTitle);

        TaskEntity taskEntity = taskRepo.findByTitleAndCard(taskTitle, cardEntity);

        return taskMapper.convertEntityToDto(taskEntity);
    }


    public List<TaskDto> getTasksByCard(String boardKey, String listTitle, String cardTitle) {
        CardEntity cardEntity = cardService.getCardEntityByTitle(boardKey, listTitle, cardTitle);
        List<TaskEntity> taskEntities = taskRepo.findAllByCard(cardEntity);
        return taskEntities.stream()
                .map(taskMapper::convertEntityToDto)
                .toList();
    }

    public TaskDto updateTask(String boardKey, String listTitle, String cardTitle, String taskTitle, TaskDto taskDto) {
        CardEntity cardEntity = cardService.getCardEntityByTitle(boardKey, listTitle, cardTitle);
        TaskEntity taskEntity = taskRepo.findByTitleAndCard(taskTitle, cardEntity);
        TaskEntity newEntity = taskMapper.convertDtoToEntity(taskDto);

        if (newEntity.getTitle() != null && !newEntity.getTitle().isEmpty()) {
            taskEntity.setTitle(newEntity.getTitle());
        }
        //if (newEntity.isCompleted() != null && !newEntity.isCompleted().isEmpty()) {
        //if (newEntity.isCompleted() != taskEntity.isCompleted()){
        if (newEntity.getCompleted() != null) {
            taskEntity.setCompleted(newEntity.getCompleted());
        }

        taskEntity = taskRepo.save(taskEntity);

        return taskMapper.convertEntityToDto(taskEntity);
    }

    public TaskDto deleteTask(String boardKey, String listTitle, String cardTitle, String taskTitle) {
        CardEntity cardEntity = cardService.getCardEntityByTitle(boardKey, listTitle, cardTitle);
        TaskEntity taskEntity = taskRepo.findByTitleAndCard(taskTitle, cardEntity);
        taskRepo.delete(taskEntity);
        return taskMapper.convertEntityToDto(taskEntity);
    }
}
