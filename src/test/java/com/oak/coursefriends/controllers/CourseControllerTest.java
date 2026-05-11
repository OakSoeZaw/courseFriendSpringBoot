package com.oak.coursefriends.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oak.coursefriends.model.Course;
import com.oak.coursefriends.services.CourseService;
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

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testGetAllCourses() throws Exception {
        when(courseService.getAllCourses()).thenReturn(List.of(new Course("CS101", "Intro to CS")));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("CS101"))
                .andExpect(jsonPath("$[0].title").value("Intro to CS"));
    }

    @Test
    @WithMockUser
    void testGetCourseByCode_found() throws Exception {
        Course course = new Course("CS101", "Intro to CS");
        when(courseService.getCourseByCode("CS101")).thenReturn(Optional.of(course));

        mockMvc.perform(get("/api/courses/CS101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CS101"));
    }

    @Test
    @WithMockUser
    void testGetCourseByCode_notFound() throws Exception {
        when(courseService.getCourseByCode("XX999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/courses/XX999"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testCreateCourse() throws Exception {
        Course course = new Course("CS101", "Intro to CS");
        when(courseService.createCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/api/courses")
                .with(oauth2Login())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CS101"));
    }

    @Test
    @WithMockUser
    void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/api/courses/1")
                .with(oauth2Login())
                .with(csrf()))
                .andExpect(status().isOk());

        verify(courseService, times(1)).deleteCourse(1L);
    }
}