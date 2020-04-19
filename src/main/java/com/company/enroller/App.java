package com.company.enroller;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        //SpringApplication app = new SpringApplication(App.class);
        //app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        //app.run(args); 
    }
}