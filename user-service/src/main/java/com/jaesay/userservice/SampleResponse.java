package com.jaesay.userservice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SampleResponse {
    private String sampleName;
    private LocalDateTime createdAt;
}
