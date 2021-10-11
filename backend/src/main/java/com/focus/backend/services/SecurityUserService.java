package com.focus.backend.services;

import com.focus.backend.model.SecurityUser;
import com.focus.backend.repositories.SecurityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUserService {
    @Autowired
    private SecurityUserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<SecurityUser> findByUsername(String username){
        return repository.findByEmail(username);
    }

    public SecurityUser save(SecurityUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }
}
