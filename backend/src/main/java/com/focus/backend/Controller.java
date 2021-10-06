package com.focus.backend;

import com.focus.backend.security.ApplicationUser;
import com.focus.backend.security.SecurityUser;
import com.focus.backend.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
public class Controller {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ApplicationUser helloWorld(){
        return userService.findByUsername("user").get();
    }

    @GetMapping("/pr")
    public Principal getUser(Principal principal){
        return principal;
    }
}
