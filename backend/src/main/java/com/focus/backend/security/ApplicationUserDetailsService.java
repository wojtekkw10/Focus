package com.focus.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ApplicationUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return buildUserForAuthentication(user, List.of(new SimpleGrantedAuthority("USER")));
    }

    private User buildUserForAuthentication(ApplicationUser user,
                                            List<GrantedAuthority> authorities) {
        String username = user.getEmail();
        String password = user.getPassword();
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        SecurityUser securityUser = new SecurityUser(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);

        securityUser.setId(user.getId());

        return securityUser;
    }
}

