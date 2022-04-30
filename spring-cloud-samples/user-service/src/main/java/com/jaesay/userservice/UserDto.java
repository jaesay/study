package com.jaesay.userservice;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String encryptedPassword;
    private List<SampleResponse> sampleResponses;
}
