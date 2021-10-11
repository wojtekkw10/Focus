package com.focus.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    @ToString.Exclude
    @JsonIgnore
    private List<Task> tasks;

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public User() {
        //Hibernate
    }

    public Long getId() {
        return id;
    }
}
