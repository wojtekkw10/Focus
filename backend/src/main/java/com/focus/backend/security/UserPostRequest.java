package com.focus.backend.security;

import lombok.Data;

@Data
public class UserPostRequest {
    private String email;
    private String password;
}
