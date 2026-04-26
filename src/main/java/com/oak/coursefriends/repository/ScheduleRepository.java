package com.oak.coursefriends.repository;

import com.oak.coursefriends.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUser(User user);

    List<Schedule> findbyUserId(Long userId);
}
