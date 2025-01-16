package com.example.webphase3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WebPhase3Application {

    public static void main(String[] args) {
        SpringApplication.run(WebPhase3Application.class, args);
    }

}
