package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.University;
import com.example.demo.repository.UniversityRepository;

@Service
public class UniversityService {

    @Autowired
    private UniversityRepository repository;

    public List<University> getListOfUniversities() {
        return repository.getListOfUniversities();
    }
}