package com.jaesay.userservice;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
