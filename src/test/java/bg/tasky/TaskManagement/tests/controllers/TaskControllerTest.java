package bg.tasky.TaskManagement.tests.controllers;

import bg.tasky.TaskManagement.controllers.TaskController;
import bg.tasky.TaskManagement.dtos.TaskDto;
import bg.tasky.TaskManagement.entities.CardEntity;
import bg.tasky.TaskManagement.entities.TaskEntity;
import bg.tasky.TaskManagement.mappers.TaskMapper;
import bg.tasky.TaskManagement.services.JwtService;
import bg.tasky.TaskManagement.services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskDto sampleTaskDto;

    @BeforeEach
    public void setup() {
        // Using constructor to initialize TaskDto
        sampleTaskDto = new TaskDto("Sample Task", false);
    }

    @Test
    @WithMockUser
    public void createTask_ShouldReturnCreatedTask() throws Exception {
        when(taskService.createTask(anyString(), anyString(), anyString(), any(TaskDto.class))).thenReturn(sampleTaskDto);

        mockMvc.perform(post("/task/create/{boardKey}/{listTitle}/{cardTitle}", "board123", "Sample List", "Sample Card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTaskDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Sample Task")))
                .andExpect(jsonPath("$.completed", is(false)));
    }

    @Test
    @WithMockUser
    public void getTask_ShouldReturnTask() throws Exception {
        when(taskService.getTaskByTitle("board123", "Sample List", "Sample Card", "Sample Task")).thenReturn(sampleTaskDto);

        mockMvc.perform(get("/task/{boardKey}/{listTitle}/{cardTitle}/{taskTitle}", "board123", "Sample List", "Sample Card", "Sample Task")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Sample Task")))
                .andExpect(jsonPath("$.completed", is(false)));
    }

    @Test
    @WithMockUser
    public void getAllTasks_ShouldReturnListOfTasks() throws Exception {
        List<TaskDto> tasks = Collections.singletonList(sampleTaskDto);
        when(taskService.getTasksByCard("board123", "Sample List", "Sample Card")).thenReturn(tasks);

        mockMvc.perform(get("/task/{boardKey}/{listTitle}/{cardTitle}/all", "board123", "Sample List", "Sample Card")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Sample Task")))
                .andExpect(jsonPath("$[0].completed", is(false)));
    }

    @Test
    @WithMockUser
    public void changeTaskTitle_ShouldReturnUpdatedTask() throws Exception {
        TaskDto updatedTaskDto = new TaskDto("Updated Task Title", true);

        when(taskService.updateTask("board123", "Sample List", "Sample Card", "Sample Task", updatedTaskDto)).thenReturn(updatedTaskDto);

        mockMvc.perform(patch("/task/{boardKey}/{listTitle}/{cardTitle}/{taskTitle}/update", "board123", "Sample List", "Sample Card", "Sample Task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTaskDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Task Title")))
                .andExpect(jsonPath("$.completed", is(true)));
    }

    @Test
    @WithMockUser
    public void deleteTask_ShouldReturnDeletedTask() throws Exception {
        when(taskService.deleteTask("board123", "Sample List", "Sample Card", "Sample Task")).thenReturn(sampleTaskDto);

        mockMvc.perform(delete("/task/{boardKey}/{listTitle}/{cardTitle}/{taskTitle}/delete", "board123", "Sample List", "Sample Card", "Sample Task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Sample Task")))
                .andExpect(jsonPath("$.completed", is(false)));
    }
}

