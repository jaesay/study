package com.jaesay.aservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/a-service/")
public class SampleController {

    @GetMapping("/hello")
    public String hello() {
        return "hello, A service";
    }

    @GetMapping("/{userId}/samples")
    public List<SampleResponse> getSamples(@PathVariable Long userId) {
        return List.of(SampleResponse.builder()
                        .sampleName("sample name1")
                        .createdAt(LocalDateTime.now())
                        .build(),
                SampleResponse.builder()
                        .sampleName("sample name2")
                        .createdAt(LocalDateTime.now())
                        .build());
    }
}
