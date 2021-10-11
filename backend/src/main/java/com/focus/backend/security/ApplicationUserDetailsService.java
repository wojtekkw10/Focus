package com.focus.backend.security;

import com.focus.backend.model.SecurityUser;
import com.focus.backend.model.User;
import com.focus.backend.services.SecurityUserService;
import com.focus.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class ApplicationUserDetailsService implements UserDetailsService {
    @Autowired
    private SecurityUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return buildUserForAuthentication(user, List.of(new SimpleGrantedAuthority("USER")));
    }

    private UserDetails buildUserForAuthentication(SecurityUser user,
                                                   List<GrantedAuthority> authorities) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .authorities(authorities)
                .build();
    }
}

