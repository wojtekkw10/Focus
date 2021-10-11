package com.focus.backend.controllers;

import com.focus.backend.UserAware;
import com.focus.backend.model.ApplicationUser;
import com.focus.backend.model.Task;
import com.focus.backend.services.TaskService;
import com.focus.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
        ApplicationUser user = userAware.getLoggedUser(principal);

        System.out.println(user);
        System.out.println(user.getTasks());

        return user.getTasks();
    }

    @PostMapping("/create")
    public Task createTask(Principal principal, @RequestBody Task task){
        ApplicationUser user = userAware.getLoggedUser(principal);

        task.setOwningUser(user);
        Task dbTask = taskService.save(task);

        user.getTasks().add(dbTask);

        userService.save(user);

        return dbTask;
    }
}
