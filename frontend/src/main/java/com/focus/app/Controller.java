package com.focus.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/")
    String helloWorld(){
        return "Hello World! Frontend 1.1";
    }
}
