package bg.tasky.TaskManagement.tests.controllers;

import bg.tasky.TaskManagement.controllers.UserController;
import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.services.JwtService;
import bg.tasky.TaskManagement.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void authenticatedUserOkTest() throws Exception {
        UserDto mockUser = new UserDto("John", "Doe", "john.doe", "password");
        when(userService.getAuthenticatedUser()).thenReturn(mockUser);

        mockMvc.perform(get("/user/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    @WithMockUser//(roles = "ADMIN")
    public void deleteUserOkTest() throws Exception {
        mockMvc.perform(delete("/user/delete")
                        .param("username", "john.doe")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateUserOkTest() throws Exception {
        UserDto updatedUser = new UserDto("Jane", "Doe", "john.doe", "newpassword");
        when(userService.updateUser("john.doe", updatedUser)).thenReturn(updatedUser);

        mockMvc.perform(patch("/user/update")
                        .param("username", "john.doe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Jane\", \"lastName\": \"Doe\", \"username\": \"john.doe\", \"password\": \"newpassword\"}")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

}
