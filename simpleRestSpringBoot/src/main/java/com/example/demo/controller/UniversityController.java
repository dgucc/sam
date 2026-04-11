package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.University;
import com.example.demo.service.UniversityService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UniversityController {

    @Autowired
    private UniversityService service;
    
    @Value("${message.hello}")
    private String hello;

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return new ResponseEntity<>(hello, HttpStatus.OK);
    }


    @PostMapping("/universities")
    public ResponseEntity<?> addUniversity(@RequestBody University university) {
        try {
            return new ResponseEntity<University>(service.addUniversity(university), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/universities")
    public ResponseEntity<?> addUniversity(@RequestBody University university) {
        try {
            return new ResponseEntity<University>(service.addUniversity(university), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/universities")
    public ResponseEntity<?> getListOfUniversities() {
       try {
           return new ResponseEntity<>(service.getListOfUniversities(), HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}