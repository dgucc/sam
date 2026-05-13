package Projet_groupe02.demo.repository;

import java.util.List;

// import Projet_groupe02.demo.model.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Projet_groupe02.demo.model.Cours;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Integer> {
    // L'interface est vide : toutes les méthodes de base sont héritées.

    @Query(value="SELECT * FROM cours WHERE id in (SELECT cours_id FROM fav_cours WHERE user_id = :user_id)", nativeQuery=true)
    List<Cours> getFavoriteClass(@Param("user_id") int user_id);

    @Query(value="SELECT * FROM cours WHERE id in (select cours_id FROM cours_to_faculte WHERE faculte_id = :faculte_id)", nativeQuery=true)
    List<Cours> getFaculteClass(@Param("faculte_id") int faculte_id);
}