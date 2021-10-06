package com.focus.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<ApplicationUser> findByUsername(String username){
        return repository.findByEmail(username);
    }

    public ApplicationUser save(ApplicationUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }
}
