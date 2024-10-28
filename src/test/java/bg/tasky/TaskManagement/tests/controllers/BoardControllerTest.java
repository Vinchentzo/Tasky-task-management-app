package bg.tasky.TaskManagement.tests.controllers;

import bg.tasky.TaskManagement.controllers.BoardController;
import bg.tasky.TaskManagement.dtos.BoardDto;
import bg.tasky.TaskManagement.services.BoardService;
import bg.tasky.TaskManagement.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoardController.class)
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtService jwtService;

    @MockBean
    private BoardService boardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    public void createBoard_ShouldReturnBoardDto() throws Exception {
        BoardDto boardDto = new BoardDto("Sample Board", "sample-key");

        when(boardService.createBoard(any(BoardDto.class))).thenReturn(boardDto);

        mockMvc.perform(post("/board/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Board"))
                .andExpect(jsonPath("$.key").value("sample-key"));
    }

    @Test
    @WithMockUser
    public void getUserBoards_ShouldReturnListOfBoardDto() throws Exception {
        BoardDto boardDto = new BoardDto("Sample Board", "sample-key");
        List<BoardDto> boards = Collections.singletonList(boardDto);

        when(boardService.getUserBoards()).thenReturn(boards);

        mockMvc.perform(get("/board/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Sample Board"))
                .andExpect(jsonPath("$[0].key").value("sample-key"));
    }

    @Test
    @WithMockUser
    public void getBoardByKey_ShouldReturnBoardDto() throws Exception {
        BoardDto boardDto = new BoardDto("Sample Board", "sample-key");

        when(boardService.getBoardByKey("sample-key")).thenReturn(boardDto);

        mockMvc.perform(get("/board/sample-key"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Board"))
                .andExpect(jsonPath("$.key").value("sample-key"));
    }

    @Test
    @WithMockUser
    public void updateBoardTitle_ShouldReturnUpdatedBoardDto() throws Exception {
        BoardDto boardDto = new BoardDto("Updated Board Title", "sample-key");

        when(boardService.updateBoardTitle(any(BoardDto.class))).thenReturn(boardDto);

        mockMvc.perform(put("/board/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Board Title"))
                .andExpect(jsonPath("$.key").value("sample-key"));
    }

    @Test
    @WithMockUser
    public void removeBoard_ShouldReturnDeletedBoardDto() throws Exception {
        BoardDto boardDto = new BoardDto("Deleted Board", "sample-key");

        when(boardService.deleteBoard(any(BoardDto.class))).thenReturn(boardDto);

        mockMvc.perform(delete("/board/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Deleted Board"))
                .andExpect(jsonPath("$.key").value("sample-key"));
    }

    @Test
    @WithMockUser
    public void removeBoardForUser_ShouldReturnUpdatedBoardDto() throws Exception {
        BoardDto boardDto = new BoardDto("Removed for User", "sample-key");

        when(boardService.deleteBoardForUser(any(BoardDto.class))).thenReturn(boardDto);

        mockMvc.perform(put("/board/user/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Removed for User"))
                .andExpect(jsonPath("$.key").value("sample-key"));
    }
}
