package Projet_groupe02.demo.repository;

import Projet_groupe02.demo.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // L'interface est vide : toutes les méthodes de base sont héritées.

    Optional<User> findByUsername(String username);

    @Query(value="SELECT * FROM users WHERE id = :user_id", nativeQuery = true)
    User getUserInfo(@Param("user_id") int user_id);
}
