package com.focus.backend.services;

import com.focus.backend.model.Task;
import com.focus.backend.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskService {
    @Autowired
    private TaskRepository repository;

    public List<Task> findAll(){
        return repository.findAll();
    }

    public Task save(Task task){
        return repository.save(task);
    }
}
