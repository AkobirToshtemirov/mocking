package com.akobir.mocking;

import com.akobir.mocking.controller.AuthUserController;
import com.akobir.mocking.dto.*;
import com.akobir.mocking.service.AuthUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;

@WebMvcTest(AuthUserController.class)
class AuthUserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthUserService userService;

    @Test
    public void testCreateUser() throws Exception {
        AuthUserCreateDTO createDTO = new AuthUserCreateDTO("username", "email", "password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/authuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(userService, Mockito.times(1)).create(Mockito.any(AuthUserCreateDTO.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        AuthUserUpdateDTO updateDTO = new AuthUserUpdateDTO(1L, "username", "email", "password");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/authuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService, Mockito.times(1)).update(Mockito.any(AuthUserUpdateDTO.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        String userId = "user123";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/authuser/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(userService, Mockito.times(1)).delete(userId);
    }

    @Test
    public void testGetUser() throws Exception {
        String userId = "user123";
        AuthUserGetDTO userDTO = new AuthUserGetDTO(1L, "username", "email");

        Mockito.when(userService.get(userId)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/authuser/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDTO.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDTO.username()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userDTO.email()));

        Mockito.verify(userService, Mockito.times(1)).get(userId);
    }

    @Test
    public void testListUsers() throws Exception {
        String username = "testUsername";
        String email = "testEmail";

        AuthUserCriteria criteria = new AuthUserCriteria(username, email);

        AuthUserGetDTO user1 = new AuthUserGetDTO(1L, "username1", "email1");
        AuthUserGetDTO user2 = new AuthUserGetDTO(2L, "username2", "email2");

        List<AuthUserGetDTO> userList = Arrays.asList(user1, user2);

        Mockito.when(userService.list(criteria)).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/authuser")
                        .param("username", username)
                        .param("email", email))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(userList.size()));

        Mockito.verify(userService, Mockito.times(1)).list(criteria);
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
