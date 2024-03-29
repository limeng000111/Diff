package com.yoka.diff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.yoka.**")
public class DiffApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiffApplication.class, args);
    }

}
