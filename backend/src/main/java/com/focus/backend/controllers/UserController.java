package com.focus.backend.controllers;

import com.focus.backend.UserAware;
import com.focus.backend.model.SecurityUser;
import com.focus.backend.model.User;
import com.focus.backend.security.UserPostRequest;
import com.focus.backend.services.SecurityUserService;
import com.focus.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserAware userAware;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/create")
    public SecurityUser create(@RequestBody UserPostRequest request){
       User user = userService.save(new User());

       return securityUserService.save(new SecurityUser(request.getEmail(), request.getPassword(), user));
    }

    @GetMapping("/current")
    public User getCurrentUser(Principal principal){
        return userAware.getLoggedUser(principal);
    }

    @PostMapping("/login")
    public SecurityUser login(Principal principal){
        if(principal == null) throw new UsernameNotFoundException("User is not logged in");
        return userAware.getLoggedSecurityUser(principal);
    }

    @PostMapping("/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }

    @GetMapping("/mail")
    public void sendMail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("wojciech.kwasniewicz@dxc.com");
        message.setSubject("Subject");
        message.setText("Your account has been <b>created</b>");
        mailSender.send(message);
    }
}
