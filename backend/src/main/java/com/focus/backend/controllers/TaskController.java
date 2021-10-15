package com.focus.backend.controllers;

import com.focus.backend.UserAware;
import com.focus.backend.model.*;
import com.focus.backend.services.TaskService;
import com.focus.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public DataGridResponse<Task> getAll(Principal principal){
        User user = userAware.getLoggedUser(principal);

        return new DataGridResponse<>(user.getTasks());
    }

    @PostMapping("/create")
    public Task createTask(Principal principal, @RequestBody Task task) throws StatusNotFoundException{
        User user = userAware.getLoggedUser(principal);

        System.out.println(task);

        task.setOwningUser(user);
        task.setStatus(getStatusForId(task.getStatus().getId()));
        Task dbTask = taskService.save(task);

        user.getTasks().add(dbTask);

        System.out.println(user);

        userService.save(user);

        return dbTask;
    }

    private TaskStatus getStatusForId(Long id) throws StatusNotFoundException {
        return taskService.findAllStatuses().stream().filter((status) -> Objects.equals(status.getId(), id)).findFirst().orElseThrow(StatusNotFoundException::new);
    }

    @PutMapping("/update")
    public Task updateTask(@RequestBody Task task){
        return taskService.update(task);
    }

    @DeleteMapping("/delete")
    public void deleteTask(Principal principal, @RequestBody LongId taskId){
        User user = userAware.getLoggedUser(principal);

        user.getTasks().removeIf((task) -> Objects.equals(task.getId(), taskId.getId()));

        userService.save(user);
    }

    @GetMapping("/statuses/all")
    public DataGridResponse<TaskStatus> getAllStatuses(){
        return new DataGridResponse<>(taskService.findAllStatuses());
    }

    @GetMapping("/statuses/{id}")
    public TaskStatus getStatus(@PathVariable Long id) throws StatusNotFoundException{
        return getStatusForId(id);
    }

    @PostMapping("/statuses/create")
    public TaskStatus createStatus(@RequestBody TaskStatus taskStatus){
        System.out.println(taskStatus);
        return taskService.saveStatus(taskStatus);
    }
}
