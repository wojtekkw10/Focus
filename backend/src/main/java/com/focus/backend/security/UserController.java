package com.focus.backend.security;

import com.focus.backend.security.ApplicationUser;
import com.focus.backend.security.UserPostRequest;
import com.focus.backend.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ApplicationUser create(@RequestBody UserPostRequest request){
        return userService.save(new ApplicationUser(request.getEmail(), request.getPassword()));
    }

    @GetMapping("/current")
    public ApplicationUser getCurrentUser(Principal principal){
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
