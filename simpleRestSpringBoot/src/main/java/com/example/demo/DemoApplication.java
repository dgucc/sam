package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class DemoApplication {

    @GetMapping("/")
    public String home(){
        return "Hello World!<br>See <a href='http://localhost:8080/universities'>Universities</a>";
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}