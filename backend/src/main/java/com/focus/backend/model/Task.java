package com.focus.backend.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String subject;

    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owning_user_id")
    private User owningUser;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private TaskStatus status;

    public Task() {
        //Hibernate
    }

    public Task(String subject, String description, TaskStatus status) {
        this.subject = subject;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public User getOwningUser() {
        return owningUser;
    }

    public void setOwningUser(User owningUser) {
        this.owningUser = owningUser;
    }

    @JsonGetter("status")
    public Long getJsonStatus(){
        if(status == null) return null;
        return status.getId();
    }
}
