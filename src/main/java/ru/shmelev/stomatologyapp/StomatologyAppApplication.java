package ru.shmelev.stomatologyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:database.properties")
public class StomatologyAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(StomatologyAppApplication.class, args);
    }

}
