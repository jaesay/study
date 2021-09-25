package com.jaesay.aservice;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SampleResponse {
    private String sampleName;
    private LocalDateTime createdAt;
}
