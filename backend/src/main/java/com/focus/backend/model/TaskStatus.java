package com.focus.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TaskStatus {
    @Id
    private Long id;

    String status;
}
