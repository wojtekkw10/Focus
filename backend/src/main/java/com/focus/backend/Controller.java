package com.focus.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/")
    public String helloWorld(){
        //personRepository.save(new Person("Jan", "Kowalski"));

        System.out.println(personRepository);
        return "Successfully added an entity to db";
    }
}
