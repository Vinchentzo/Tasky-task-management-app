//package bg.tasky.TaskManagement.controllers;
//
//import bg.tasky.TaskManagement.dtos.UserDto;
//import bg.tasky.TaskManagement.services.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = UserController.class)
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @Test
//    void authenticatedUser_ShouldReturnAuthenticatedUser() throws Exception {
//        // Arrange
//        UserDto mockUser = new UserDto("John", "Doe", "johndoe", "encrypted_password");
//        when(userService.getAuthenticatedUser()).thenReturn(mockUser);
//
//        // Act & Assert
//        mockMvc.perform(get("/user/me")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName").value("John"))
//                .andExpect(jsonPath("$.lastName").value("Doe"))
//                .andExpect(jsonPath("$.username").value("johndoe"));
//    }
//}

//package bg.tasky.TaskManagement.controllers;
//
//import bg.tasky.TaskManagement.dtos.UserDto;
//import bg.tasky.TaskManagement.entities.UserEntity;
//import bg.tasky.TaskManagement.services.JwtService;
//import bg.tasky.TaskManagement.services.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@WebMvcTest(controllers = UserController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//class UserControllerTest {
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private JwtService jwtService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    @Test
//    void authenticatedUserOkTest() throws Exception {
//        UserDto userDto = new UserDto("John", "Doe", "john_doe", "password");
//        when(userService.getAuthenticatedUser()).thenReturn(userDto);
//
//        mockMvc.perform(get("/user/me"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName").value("John"))
//                .andExpect(jsonPath("$.lastName").value("Doe"))
//                .andExpect(jsonPath("$.username").value("john_doe"));
//    }
//
//    @Test
//    void deleteUserOkTest() throws Exception {
//        when(userService.deleteUser("john_doe")).thenReturn(1L);
//
//        mockMvc.perform(delete("/user/delete?username=john_doe"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(1L));
//    }
//
//    @Test
//    void updateUserOkTest() throws Exception {
//        UserDto updatedUser = new UserDto("Jane", "Doe", "john_doe", "new_password");
//        when(userService.updateUser(any(String.class), any(UserDto.class))).thenReturn(updatedUser);
//
//        mockMvc.perform(patch("/user/update?username=john_doe")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"firstName\": \"Jane\", \"lastName\": \"Doe\", \"username\": \"john_doe\", \"password\": \"new_password\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName").value("Jane"))
//                .andExpect(jsonPath("$.lastName").value("Doe"))
//                .andExpect(jsonPath("$.username").value("john_doe"));
//    }
//}

package bg.tasky.TaskManagement.tests.controllers;

import bg.tasky.TaskManagement.controllers.UserController;
import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.services.JwtService;
import bg.tasky.TaskManagement.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
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
