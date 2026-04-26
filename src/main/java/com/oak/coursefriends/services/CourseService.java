package com.oak.coursefriends.services;

import com.oak.coursefriends.model.*;
import com.oak.coursefriends.repository.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    
    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id){
        return courseRepository.findById(id);
    }

    public Optional<Course> getCourseByCode(String code){
        return courseRepository.findByCode(code);
    }

    public Course createCourse(Course course){
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id){
        courseRepository.deleteById(id);
    }
}
