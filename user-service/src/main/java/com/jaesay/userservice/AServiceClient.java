package com.jaesay.userservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// @FeignClient annotation that we had used in Feign actually packs in a default setup for the load balancer client which round-robins our request
@FeignClient("a-service")
public interface AServiceClient {

    @GetMapping("/a-service/{userId}/samples")
    List<SampleResponse> getSamples(@PathVariable Long userId);
}
