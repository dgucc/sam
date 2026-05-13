package Projet_groupe02.demo.repository;

import Projet_groupe02.demo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    // L'interface est vide : toutes les méthodes de base sont héritées.
}
