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

    public University addUniversity(University university){
        return repository.save(university);
    }

    public void deleteUniversity(Long id){
        repository.deleteById(id);
    }

    public List<University> getListOfUniversities() {
        return repository.findAll();
    }

    
}