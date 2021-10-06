package com.focus.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public Optional<ApplicationUser> findByUsername(String username){
        return repository.findByEmail(username);
    }

    public ApplicationUser save(ApplicationUser user){
        return repository.save(user);
    }
}
