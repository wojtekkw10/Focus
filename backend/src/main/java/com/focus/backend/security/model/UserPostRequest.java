package com.focus.backend.security.model;

import lombok.Data;

@Data
public class UserPostRequest {
    private String email;
    private String password;
}
