package com.oak.coursefriends.services;

import com.oak.coursefriends.model.*;
import com.oak.coursefriends.repository.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;
    
    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(){
        log.info("Fetching all courses");
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id){
        return courseRepository.findById(id);
    }

    public Optional<Course> getCourseByCode(String code){
        return courseRepository.findByCode(code);
    }

    public Course createCourse(Course course){
        log.info("Creating course: {}", course.getCode());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id){
        log.warn("Deleting course with id: {}", id);
        courseRepository.deleteById(id);
    }
}
