package com.oak.coursefriends.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules;

    public User(){}

    public User(String username){
        this.username = username;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public List<Schedule> getSchedules(){
        return schedules;
    }
    public void setSchedule(List<Schedule> schedules){
        this.schedules = schedules;
    }

}
