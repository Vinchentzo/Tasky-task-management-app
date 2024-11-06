package bg.tasky.TaskManagement.tests.controllers;

import bg.tasky.TaskManagement.controllers.CardController;
import bg.tasky.TaskManagement.dtos.CardDto;
import bg.tasky.TaskManagement.services.CardService;
import bg.tasky.TaskManagement.services.JwtService;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CardController.class)
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CardService cardService;

    @Autowired
    private ObjectMapper objectMapper;

    private CardDto sampleCardDto;

    @BeforeEach
    public void setup() {
        sampleCardDto = new CardDto("Sample Card", "This is a sample card description.");
    }

    @Test
    @WithMockUser
    public void createCard_ShouldReturnCreatedCard() throws Exception {
        when(cardService.createCard(anyString(), anyString(), any(CardDto.class))).thenReturn(sampleCardDto);

        mockMvc.perform(post("/card/create/{boardKey}/{listTitle}", "board123", "Sample List")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCardDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Sample Card")))
                .andExpect(jsonPath("$.description", is("This is a sample card description.")));
    }

    @Test
    @WithMockUser
    public void getCard_ShouldReturnCard() throws Exception {
        when(cardService.getCardByTitle("board123", "Sample List", "Sample Card")).thenReturn(sampleCardDto);

        mockMvc.perform(get("/card/{boardKey}/{listTitle}/{cardTitle}", "board123", "Sample List", "Sample Card")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Sample Card")))
                .andExpect(jsonPath("$.description", is("This is a sample card description.")));
    }

    @Test
    @WithMockUser
    public void getAllCards_ShouldReturnListOfCards() throws Exception {
        when(cardService.getCardsByList("board123", "Sample List")).thenReturn(Collections.singletonList(sampleCardDto));

        mockMvc.perform(get("/card/{boardKey}/{listTitle}/all", "board123", "Sample List")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Sample Card")))
                .andExpect(jsonPath("$[0].description", is("This is a sample card description.")));
    }

    @Test
    @WithMockUser
    public void changeCardTitle_ShouldReturnUpdatedCard() throws Exception {
        CardDto updatedCardDto = new CardDto("Updated Card Title", "Updated description");

        when(cardService.updateCardTitle("board123", "Sample List", "Sample Card", updatedCardDto)).thenReturn(updatedCardDto);

        mockMvc.perform(patch("/card/{boardKey}/{listTitle}/{oldTitle}/update", "board123", "Sample List", "Sample Card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCardDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Card Title")))
                .andExpect(jsonPath("$.description", is("Updated description")));
    }

    @Test
    @WithMockUser
    public void deleteCard_ShouldReturnDeletedCard() throws Exception {
        when(cardService.deleteCard("board123", "Sample List", "Sample Card")).thenReturn(sampleCardDto);

        mockMvc.perform(delete("/card/{boardKey}/{listTitle}/{cardTitle}/delete", "board123", "Sample List", "Sample Card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Sample Card")))
                .andExpect(jsonPath("$.description", is("This is a sample card description.")));
    }
}
