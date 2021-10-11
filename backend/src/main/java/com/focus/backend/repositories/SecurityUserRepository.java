package com.focus.backend.repositories;

import com.focus.backend.model.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long> {
    Optional<SecurityUser> findByEmail(String username);
}
