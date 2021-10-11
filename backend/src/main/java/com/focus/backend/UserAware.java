package com.focus.backend;

import com.focus.backend.model.SecurityUser;
import com.focus.backend.model.User;
import com.focus.backend.services.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class UserAware {
    @Autowired
    private SecurityUserService userService;

    public SecurityUser getLoggedSecurityUser(Principal principal){
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getLoggedUser(Principal principal){
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found")).getUser();
    }
}
