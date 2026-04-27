package com.oak.coursefriends.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.oak.coursefriends.model.*;
import com.oak.coursefriends.services.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @GetMapping("user/{userId}")
    public List<Schedule> getScheduleByUserId(@PathVariable Long userId){
        return scheduleService.getScheduleByUserId(userId);
    }

    @PostMapping
    public Schedule addCourseToSchedule(@RequestBody Schedule schedule){
        return scheduleService.addCourseToSchedule(schedule);
    }

    @DeleteMapping("/{id}")
    public void removeCourseFromSchdeule(@PathVariable Long id){
        scheduleService.removeCourseFromSchedule(id);
    }

    @GetMapping("/course/{courseCode}")
    public List<Schedule> getUsersByCourseCode(@PathVariable String courseCode){
        return scheduleService.getUsersByCourseCode(courseCode);
    }
}
