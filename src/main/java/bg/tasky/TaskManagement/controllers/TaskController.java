package bg.tasky.TaskManagement.controllers;

import bg.tasky.TaskManagement.dtos.TaskDto;
import bg.tasky.TaskManagement.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create/{boardKey}/{listTitle}/{cardTitle}")
    public ResponseEntity<TaskDto> createTask(
            @PathVariable String listTitle,
            @PathVariable String boardKey,
            @PathVariable String cardTitle,
            @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.createTask(listTitle, boardKey, cardTitle, taskDto));
    }

    @GetMapping("/{boardKey}/{listTitle}/{cardTitle}/{taskTitle}")
    public ResponseEntity<TaskDto> getTask(
            @PathVariable String boardKey,
            @PathVariable String listTitle,
            @PathVariable String cardTitle,
            @PathVariable String taskTitle){
        return ResponseEntity.ok(taskService.getTaskByTitle(boardKey, listTitle, cardTitle, taskTitle));
    }

    @GetMapping("/{boardKey}/{listTitle}/{cardTitle}/all")
    public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable String boardKey,
                                                     @PathVariable String listTitle,
                                                     @PathVariable String cardTitle){
        return ResponseEntity.ok(taskService.getTasksByCard(boardKey, listTitle, cardTitle));
    }

    @PatchMapping("/{boardKey}/{listTitle}/{cardTitle}/{taskTitle}/update")
    public ResponseEntity<TaskDto> changeTaskTitle(
            @PathVariable String boardKey,
            @PathVariable String listTitle,
            @PathVariable String cardTitle,
            @PathVariable String taskTitle,
            @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.updateTask(boardKey, listTitle, cardTitle, taskTitle,taskDto));
    }

    @DeleteMapping("{boardKey}/{listTitle}/{cardTitle}/{taskTitle}/delete")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable String boardKey,
                                              @PathVariable String listTitle,
                                              @PathVariable String cardTitle,
                                              @PathVariable String taskTitle) {
        return ResponseEntity.ok(taskService.deleteTask(boardKey, listTitle, cardTitle, taskTitle));
    }
}
