package com.oak.coursefriends.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oak.coursefriends.repository.ScheduleRepository;
import com.oak.coursefriends.model.Schedule;
import com.oak.coursefriends.model.User;
import com.oak.coursefriends.model.Course;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {
    
    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    void testGetScheduleByUserId(){
        User user = new User("john");
        Course course = new Course("CS101", "Intro to CS");
        Schedule schedule = new Schedule(user, course);
        when(scheduleRepository.findByUser_Id(1L)).thenReturn(List.of(schedule));
        List<Schedule> result = scheduleService.getScheduleByUserId(1L);
        assertEquals(1, result.size());
    }

    @Test 
    void testAddCourseToSchedule(){
        User user = new User("john");
        Course course = new Course("CS101", "Intro to CS");
        Schedule schedule = new Schedule(user, course);
        when(scheduleRepository.save(schedule)).thenReturn(schedule);
        Schedule result = scheduleService.addCourseToSchedule(schedule);
        assertEquals(schedule, result);
    }

    @Test
    void testRemoveCourseFromSchedule(){
        scheduleService.removeCourseFromSchedule(1L);
        verify(scheduleRepository, times(1)).deleteById(1L);
    }


}
