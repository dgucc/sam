package Projet_groupe02.demo.repository;

import Projet_groupe02.demo.model.Synthese;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SynthRepository extends JpaRepository<Synthese, Integer> {
    // L'interface est vide : toutes les méthodes de base sont héritées.

    @Query(value="SELECT * FROM synthese WHERE author_id = :user_id", nativeQuery=true)
    List<Synthese> getPersonnalSyntheses(@Param("user_id") int user_id);

    @Query(value="SELECT * FROM synthese WHERE id in (SELECT synth_id FROM fav_synth WHERE user_id = :user_id)", nativeQuery=true)
    List<Synthese> getFavoriteSynth(@Param("user_id") int user_id);

    @Query(value="SELECT * FROM synthese where cours_id = :cours_id", nativeQuery = true)
    List<Synthese> getClassSynthList(@Param("cours_id") int cours_id);
}