package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.demo.model.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long>{

    @Query(value="SELECT ID,NAME FROM DEMO.UNIVERSITY",nativeQuery=true)
    List<University> getListOfUniversities();
}
