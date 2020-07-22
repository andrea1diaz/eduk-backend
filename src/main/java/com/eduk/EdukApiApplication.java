package com.eduk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.eduk")
@SpringBootApplication
public class EdukApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdukApiApplication.class, args);
    }

}