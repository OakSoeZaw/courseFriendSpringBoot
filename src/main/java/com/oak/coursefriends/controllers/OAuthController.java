package com.oak.coursefriends.controllers;

import com.oak.coursefriends.model.User;
import com.oak.coursefriends.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class OAuthController {

    private final UserService userService;

    public OAuthController(UserService userService){
        this.userService = userService;
    }
    
    @GetMapping("/api/me")
    public User getCurrentUser(@AuthenticationPrincipal OAuth2User principal){
        if (principal == null) return null;
        
        String githubUsername = principal.getAttribute("login");

        return userService.getUserByUsername(githubUsername)
            .orElseGet(() -> userService.createUser(new User(githubUsername)));
    }
}
