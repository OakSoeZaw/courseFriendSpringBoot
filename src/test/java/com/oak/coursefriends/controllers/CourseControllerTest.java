package com.oak.coursefriends.controllers;

import com.oak.coursefriends.model.Course;
import com.oak.coursefriends.services.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Test
    void testGetAllCourses() {
        when(courseService.getAllCourses()).thenReturn(List.of(new Course("CS101", "Intro to CS")));
        List<Course> courses = courseController.getAllCourses();
        assertEquals(1, courses.size());
        assertEquals("CS101", courses.get(0).getCode());
    }

    @Test
    void testGetCourseByCode_found() {
        Course course = new Course("CS101", "Intro to CS");
        when(courseService.getCourseByCode("CS101")).thenReturn(Optional.of(course));
        Optional<Course> result = courseController.getCourseByCode("CS101");
        assertTrue(result.isPresent());
        assertEquals("CS101", result.get().getCode());
    }

    @Test
    void testGetCourseByCode_notFound() {
        when(courseService.getCourseByCode("XX999")).thenReturn(Optional.empty());
        Optional<Course> result = courseController.getCourseByCode("XX999");
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateCourse() {
        Course course = new Course("CS101", "Intro to CS");
        when(courseService.createCourse(course)).thenReturn(course);
        Course created = courseController.createCourse(course);
        assertEquals("CS101", created.getCode());
        assertEquals("Intro to CS", created.getTitle());
    }

    @Test
    void testDeleteCourse() {
        courseController.deleteCourse(1L);
        verify(courseService, times(1)).deleteCourse(1L);
    }
}