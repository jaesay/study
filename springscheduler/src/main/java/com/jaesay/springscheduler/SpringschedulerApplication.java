package com.jaesay.springscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringschedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringschedulerApplication.class, args);
    }

}
