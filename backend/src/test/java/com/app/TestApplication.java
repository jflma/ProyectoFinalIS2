package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {
    // Main class for test context if main app is not picked up found or needed
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
