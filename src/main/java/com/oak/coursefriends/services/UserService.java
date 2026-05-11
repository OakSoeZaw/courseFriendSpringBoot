package com.oak.coursefriends.services;

import com.oak.coursefriends.repository.*;

import org.springframework.stereotype.Service;
import com.oak.coursefriends.model.*;
import java.util.Optional;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username){
        log.info("Looking up user by username: {}", username);
        return userRepository.findByUsername(username);
    }

    public User createUser(User user){
        log.info("Creating user: {}", user.getUsername());
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        log.warn("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }

}
