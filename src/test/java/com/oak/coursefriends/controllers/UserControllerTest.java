package com.oak.coursefriends.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oak.coursefriends.model.User;
import com.oak.coursefriends.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testGetAllUsers() throws Exception{
        when(userService.getAllUsers()).thenReturn(List.of(new User("john"), new User("soney")));
        
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].username").value("john"))
            .andExpect(jsonPath("$[1].username").value("soney"));
    }

    @Test
    @WithMockUser
    void testGetUserById_found() throws Exception {
        User user = new User("john");
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    @WithMockUser
    void testGetUserByUsername_found() throws Exception {
        User user = new User("john");
        when(userService.getUserByUsername("john")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/username/john"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("john"));
    }


    @Test
    @WithMockUser
    void testGetUserByUsername_notFound() throws Exception {
        when(userService.getUserByUsername("unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/username/unknown"))
            .andExpect(status().isOk());
    }

    @Test
    void testCreateUser() throws Exception {
        User user = new User("john");
        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
            .with(oauth2Login())
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1")
            .with(oauth2Login())
            .with(csrf()))
            .andExpect(status().isOk());
        
        verify(userService, times(1)).deleteUser(1L);
    }

}
