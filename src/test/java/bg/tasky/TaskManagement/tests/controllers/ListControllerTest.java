package bg.tasky.TaskManagement.tests.controllers;

import bg.tasky.TaskManagement.controllers.ListController;
import bg.tasky.TaskManagement.dtos.ListDto;
import bg.tasky.TaskManagement.services.JwtService;
import bg.tasky.TaskManagement.services.ListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(ListController.class)
public class ListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private ListService listService;

    @Autowired
    private ObjectMapper objectMapper;

    private ListDto sampleListDto;

    @BeforeEach
    public void setup() {
        sampleListDto = new ListDto("Sample List");
    }

    @Test
    @WithMockUser
    public void createList_ShouldReturnCreatedList() throws Exception {
        when(listService.createList(anyString(), any(ListDto.class))).thenReturn(sampleListDto);

        mockMvc.perform(post("/list/create/{boardKey}", "board123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleListDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Sample List")));
    }

    @Test
    @WithMockUser
    public void getList_ShouldReturnList() throws Exception {
        when(listService.getListByTitle("board123", "Sample List")).thenReturn(sampleListDto);

        mockMvc.perform(get("/list/{boardKey}/{title}", "board123", "Sample List")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Sample List")));
    }

    @Test
    @WithMockUser
    public void getAllLists_ShouldReturnListOfLists() throws Exception {
        List<ListDto> lists = Collections.singletonList(sampleListDto);
        when(listService.getListsByBoard("board123")).thenReturn(lists);

        mockMvc.perform(get("/list/{boardKey}/all", "board123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Sample List")));
    }

    @Test
    @WithMockUser
    public void changeListTitle_ShouldReturnUpdatedList() throws Exception {
        when(listService.updateListTitle("board123", "Old Title", "New Title")).thenReturn(sampleListDto);

        mockMvc.perform(put("/list/{boardKey}/{oldTitle}/update", "board123", "Old Title")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("New Title")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Sample List")));
    }

    @Test
    @WithMockUser
    public void deleteList_ShouldReturnDeletedList() throws Exception {
        when(listService.deleteList("board123", "Sample List")).thenReturn(sampleListDto);

        mockMvc.perform(delete("/list/{boardKey}/{title}/delete", "board123", "Sample List")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Sample List")));
    }
}
