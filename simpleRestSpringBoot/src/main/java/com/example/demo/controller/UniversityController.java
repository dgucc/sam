package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.University;
import com.example.demo.service.UniversityService;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UniversityController {

    @GetMapping("/universities")
    public List<University> getListOfUniversities() {
        return UniversityService.getListOfUniversities();
    }
}