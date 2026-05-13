package Projet_groupe02.demo.repository;

import Projet_groupe02.demo.model.FavCours;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavoriteClassRepository extends JpaRepository<FavCours, Integer> {
    // L'interface est vide : toutes les méthodes de base sont héritées.

    @Query(value="SELECT * FROM fav_cours where user_id = :user_id and cours_id = :cours_id", nativeQuery = true)
    List<FavCours> isFavClass(@Param("cours_id") int cours_id, @Param("user_id") int user_id);
}
