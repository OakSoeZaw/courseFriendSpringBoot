package com.oak.coursefriends.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oak.coursefriends.model.Course;
import com.oak.coursefriends.model.Schedule;
import com.oak.coursefriends.model.User;
import com.oak.coursefriends.services.ScheduleService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ScheduleService scheduleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testGetScheduleByUserId() throws Exception {
        User user = new User("john");
        Course course = new Course("CS101", "Intro to CS");
        Schedule schedule = new Schedule(user, course);

        when(scheduleService.getScheduleByUserId(1L)).thenReturn(List.of(schedule));

        mockMvc.perform(get("/api/schedules/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.username").value("john"))
                .andExpect(jsonPath("$[0].course.code").value("CS101"));
    }

    @Test
    @WithMockUser
    void testGetScheduleByUserId_empty() throws Exception {
        when(scheduleService.getScheduleByUserId(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/schedules/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @WithMockUser
    void testAddCourseToSchedule() throws Exception {
        User user = new User("john");
        Course course = new Course("CS101", "Intro to CS");
        Schedule schedule = new Schedule(user, course);

        when(scheduleService.addCourseToSchedule(any(Schedule.class))).thenReturn(schedule);

        mockMvc.perform(post("/api/schedules")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schedule)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value("john"))
                .andExpect(jsonPath("$.course.code").value("CS101"));
    }

    @Test
    @WithMockUser
    void testRemoveCourseFromSchedule() throws Exception {
        mockMvc.perform(delete("/api/schedules/1")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(scheduleService, times(1)).removeCourseFromSchedule(1L);
    }

    @Test
    @WithMockUser
    void testGetUsersByCourseCode() throws Exception {
        User user = new User("john");
        Course course = new Course("CS101", "Intro to CS");
        Schedule schedule = new Schedule(user, course);

        when(scheduleService.getUsersByCourseCode("CS101")).thenReturn(List.of(schedule));

        mockMvc.perform(get("/api/schedules/course/CS101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.username").value("john"))
                .andExpect(jsonPath("$[0].course.code").value("CS101"));
    }
}