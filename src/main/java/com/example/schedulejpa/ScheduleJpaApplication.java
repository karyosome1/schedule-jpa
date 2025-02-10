package com.example.schedulejpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleJpaApplication.class, args);
    }

}
