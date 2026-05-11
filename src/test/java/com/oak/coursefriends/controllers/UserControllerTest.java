package com.oak.coursefriends.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oak.coursefriends.repository.UserRepository;
import com.oak.coursefriends.services.UserService;
import com.oak.coursefriends.model.User;
import com.oak.coursefriends.controllers.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetAllUsers(){
        when(userService.getAllUsers()).thenReturn(List.of(new User("john"), new User("soney")));
        List<User> users = userController.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testCreateUser(){
        User user = new User("john");
        when(userService.createUser(user)).thenReturn(user);
        User created = userController.createUser(user);
        assertEquals("john", created.getUsername());
    }
    
    @Test
    void testDeleteUser(){
        userController.deleterUser(1L);
        verify(userService, times(1)).deleteUser(1L);
    }
}
