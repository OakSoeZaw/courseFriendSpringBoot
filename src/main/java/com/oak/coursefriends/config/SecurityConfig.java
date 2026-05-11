package com.oak.coursefriends.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/*.css", "/*.js", "/error").permitAll()
                .anyRequest().authenticated()
            )

            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/schedule.html", true)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            );
        
        return http.build();
    }
}
