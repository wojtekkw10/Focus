package com.focus.backend.controllers;

import com.focus.backend.UserAware;
import com.focus.backend.model.User;
import com.focus.backend.model.LongId;
import com.focus.backend.model.Task;
import com.focus.backend.services.TaskService;
import com.focus.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAware userAware;

    @GetMapping("/all")
    public List<Task> getAll(Principal principal){
        User user = userAware.getLoggedUser(principal);

        return user.getTasks();
    }

    @PostMapping("/create")
    public Task createTask(Principal principal, @RequestBody Task task){
        User user = userAware.getLoggedUser(principal);

        task.setOwningUser(user);
        Task dbTask = taskService.save(task);

        user.getTasks().add(dbTask);

        userService.save(user);

        return dbTask;
    }

    @DeleteMapping("/delete")
    public void deleteTask(Principal principal, @RequestBody LongId taskId){
        User user = userAware.getLoggedUser(principal);

        user.getTasks().removeIf((task) -> Objects.equals(task.getId(), taskId.getId()));

        userService.save(user);
    }
}
