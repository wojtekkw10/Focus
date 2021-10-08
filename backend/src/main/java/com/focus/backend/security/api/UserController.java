package com.focus.backend.security.api;

import com.focus.backend.security.model.ApplicationUser;
import com.focus.backend.security.model.UserPostRequest;
import com.focus.backend.security.services.UserService;
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
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/create")
    public ApplicationUser create(@RequestBody UserPostRequest request){
       return userService.save(new ApplicationUser(request.getEmail(), request.getPassword()));
    }

    @GetMapping("/current")
    public ApplicationUser getCurrentUser(Principal principal){
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @PostMapping("/login")
    public ApplicationUser login(Principal principal){
        if(principal == null) throw new UsernameNotFoundException("User is not logged in");
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
