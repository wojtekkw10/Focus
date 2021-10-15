package com.focus.backend.services;

import com.focus.backend.model.Task;
import com.focus.backend.model.TaskStatus;
import com.focus.backend.repositories.StatusRepository;
import com.focus.backend.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;

    @Autowired
    private StatusRepository statusRepository;

    public List<Task> findAll(){
        return repository.findAll();
    }

    public Task update(Task task){
        Task updatedTask = repository.getById(task.getId());

        if(task.getDescription() != null) updatedTask.setDescription(task.getDescription());
        if(task.getSubject() != null) updatedTask.setSubject(task.getSubject());
        if(task.getStatus() != null) updatedTask.setStatus(task.getStatus());

        return repository.save(updatedTask);
    }

    public Task save(Task task){
        return repository.save(task);
    }

    public List<TaskStatus> findAllStatuses(){
        return statusRepository.findAll();
    }

    public TaskStatus saveStatus(TaskStatus status){
        return statusRepository.save(status);
    }
}
