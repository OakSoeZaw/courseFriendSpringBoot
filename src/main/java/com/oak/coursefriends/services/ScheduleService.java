package com.oak.coursefriends.services;

import com.oak.coursefriends.model.*;
import com.oak.coursefriends.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getScheduleByUserId(Long userId){
        return scheduleRepository.findByUser_Id(userId);
    }

    public List<Schedule> getUsersByCourseCode(String courseCode){
        return scheduleRepository.findByCourse_Code(courseCode);
    }

    public Schedule addCourseToSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }
    public void removeCourseFromSchedule(Long id){
        scheduleRepository.deleteById(id);
    }
}
