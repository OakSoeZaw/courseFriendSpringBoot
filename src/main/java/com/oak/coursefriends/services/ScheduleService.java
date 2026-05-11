package com.oak.coursefriends.services;

import com.oak.coursefriends.model.*;
import com.oak.coursefriends.repository.*;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ScheduleService {

    private static final Logger log = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getScheduleByUserId(Long userId){
        log.info("Fetching schedule for user id: {}", userId);
        return scheduleRepository.findByUser_Id(userId);
    }

    public List<Schedule> getUsersByCourseCode(String courseCode){
        return scheduleRepository.findByCourse_Code(courseCode);
    }

    public Schedule addCourseToSchedule(Schedule schedule){
        log.info("Adding course to schdule for user id: {}", schedule.getUser().getId());
        return scheduleRepository.save(schedule);
    }
    public void removeCourseFromSchedule(Long id){
        log.warn("Removing schedule entry id: {}", id);
        scheduleRepository.deleteById(id);
    }
}
