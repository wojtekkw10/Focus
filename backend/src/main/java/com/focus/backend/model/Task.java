package com.focus.backend.model;

import javax.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subject;
    private String description;

    @ManyToOne
    @JoinColumn(name = "owning_user_id")
    private User owningUser;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public Task() {
        //Hibernate
    }

    public Task(String subject, String description, Status status) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getOwningUser() {
        return owningUser;
    }

    public void setOwningUser(User owningUser) {
        this.owningUser = owningUser;
    }
}
