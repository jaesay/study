package com.jaesay.demokubernetes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyappPropertiesTest {

    @Autowired
    private MyappProperties myappProperties;

    @Test
    void getName_shouldSucceed() {
        System.out.println(myappProperties.getName());
        assertEquals("test\ntest", myappProperties.getName());
    }
}