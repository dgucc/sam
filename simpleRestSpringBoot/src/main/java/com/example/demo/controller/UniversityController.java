package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.University;
import com.example.demo.service.UniversityService;

@RestController
@CrossOrigin(origins = "*")
public class UniversityController {

    @Autowired
    private UniversityService service;
    

    @GetMapping("/universities")
    public ResponseEntity<?> getListOfUniversities() {
       try {
           return new ResponseEntity<>(service.getListOfUniversities(), HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}