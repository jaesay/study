package com.jaesay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class ToyProject3Application {

	public static void main(String[] args) {
		SpringApplication.run(ToyProject3Application.class, args);
	}

}

