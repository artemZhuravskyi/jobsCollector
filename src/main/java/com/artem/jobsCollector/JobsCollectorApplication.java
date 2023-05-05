package com.artem.jobsCollector;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class JobsCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobsCollectorApplication.class, args);
    }
}
