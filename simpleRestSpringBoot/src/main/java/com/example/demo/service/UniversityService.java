package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.University;

@Service
public class UniversityService {

    public static List<University> getListOfUniversities() {
        return Arrays.asList(
                new University("Faculté universitaire de Namur"),
                new University("Université catholique de Louvain"),
                new University("Université libre de Bruxelles")
        );
    }
}
