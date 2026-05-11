package com.oak.coursefriends.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.oak.coursefriends.model.*;
import com.oak.coursefriends.services.CourseService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/{code}")
    public Optional<Course> getCourseByCode(@PathVariable String code){
        return courseService.getCourseByCode(code);
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course){
        return courseService.createCourse(course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
    }
}
