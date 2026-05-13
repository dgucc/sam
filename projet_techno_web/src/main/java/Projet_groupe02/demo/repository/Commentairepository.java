package Projet_groupe02.demo.repository;

import Projet_groupe02.demo.model.Commentaire;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Commentairepository extends JpaRepository<Commentaire, Integer> {

    @Query(value = "SELECT * FROM commentaire WHERE synthese_id = :synthese_id ORDER BY created_at ASC", nativeQuery = true)
    List<Commentaire> getCommentairesBySyntheseId(@Param("synthese_id") int synthese_id);
}
