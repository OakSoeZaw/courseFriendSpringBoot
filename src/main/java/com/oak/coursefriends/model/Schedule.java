package com.oak.coursefriends.model;

import jakarta.persistence.*;

@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_code", nullable = false)
    private Course course;

    public Schedule() {
    }

    public Schedule(User user, Course course) {
        this.user = user;
        this.course = course;
    }

    // getter and setter
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setCourseCode(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

}
