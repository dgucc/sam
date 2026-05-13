package Projet_groupe02.demo.repository;

import Projet_groupe02.demo.model.FavSynth;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavoriteSynthRepository extends JpaRepository<FavSynth, Integer> {
    // L'interface est vide : toutes les méthodes de base sont héritées.

    @Query(value="SELECT * FROM fav_synth where synth_id = :synth_id and user_id = :user_id", nativeQuery = true)
    List<FavSynth> isFavSynth(@Param("synth_id") int synth_id, @Param("user_id") int user_id);
}
