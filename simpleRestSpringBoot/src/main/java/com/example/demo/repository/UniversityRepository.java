package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.University;

import java.util.List;



@Repository
public interface UniversityRepository extends JpaRepository<University, Long>{

/**
 * - save()
 * - findAll()
 * - findById()
 * - delete(<>)
 * - deleteAll()
 * 
 */
}

