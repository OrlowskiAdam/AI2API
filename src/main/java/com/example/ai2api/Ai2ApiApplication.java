package com.example.ai2api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableAsync
public class Ai2ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ai2ApiApplication.class, args);
    }

}
