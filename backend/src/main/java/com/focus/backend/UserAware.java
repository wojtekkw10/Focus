package com.focus.backend;

import com.focus.backend.model.ApplicationUser;
import com.focus.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class UserAware {
    @Autowired
    private UserService userService;

    public ApplicationUser getLoggedUser(Principal principal){
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
