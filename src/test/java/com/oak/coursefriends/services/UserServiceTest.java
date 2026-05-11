package com.oak.coursefriends.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oak.coursefriends.repository.UserRepository;
import com.oak.coursefriends.model.User;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetAllUsers(){
        when(userRepository.findAll()).thenReturn(List.of(new User("john"), new User("alice"), new User("mike")));
        List<User> users = userService.getAllUsers();
        assertEquals(3, users.size());
        assertEquals("john", users.get(0).getUsername());
        assertEquals("alice", users.get(1).getUsername());
        assertEquals("mike", users.get(2).getUsername());
    }

    @Test
    void testCreateUser(){
        User user = new User("john");
        when(userRepository.save(user)).thenReturn(user);
        User created = userService.createUser(user);
        assertEquals("john", created.getUsername());
    }

    @Test
    void testGetUserByUsername_found(){
        User user = new User("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserByUsername("john");
        assertTrue(result.isPresent());
        assertEquals("john", result.get().getUsername());
    }

    @Test
    void testGerUsername_notFound(){
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        Optional<User> result = userService.getUserByUsername("unknown");
        assertFalse(result.isPresent());
    }
    
    @Test
    void testDeleteUser(){
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    
}
