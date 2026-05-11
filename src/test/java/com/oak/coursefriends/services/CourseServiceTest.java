package com.oak.coursefriends.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oak.coursefriends.repository.CourseRepository;
import com.oak.coursefriends.model.Course;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void testGetAllCourses(){
        when(courseRepository.findAll()).thenReturn(List.of(new Course("CS101", "Intro to CS"), new Course("MATH201", "Calculus II")));
        List<Course> courses = courseService.getAllCourses();
        assertEquals(2, courses.size());
        assertEquals("CS101", courses.get(0).getCode());
        assertEquals("Intro to CS", courses.get(0).getTitle());
        assertEquals("MATH201", courses.get(1).getCode());
        assertEquals("Calculus II", courses.get(1).getTitle());
    }

    @Test 
    void testCreateCourse(){
        Course course = new Course("CS101", "Intro to CS");
        when(courseRepository.save(course)).thenReturn(course);
        Course created = courseService.createCourse(course);
        assertEquals("CS101", created.getCode());
        assertEquals("Intro to CS", created.getTitle());
    }

    @Test
    void testGetCourseByCode_found(){
        Course course = new Course("CS101", "Intro to CS");
        when(courseRepository.findByCode("CS101")).thenReturn(Optional.of(course));
        Optional<Course> result = courseService.getCourseByCode("CS101");
        assertTrue(result.isPresent());
    }



    @Test
    void testGetCourseByCode_notFound(){
        when(courseRepository.findByCode("XX999")).thenReturn(Optional.empty());
        Optional<Course> result = courseService.getCourseByCode("XX999");
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteCourse(){
        courseService.deleteCourse(1L);
        verify(courseRepository, times(1)).deleteById(1L);
    }
    
}
