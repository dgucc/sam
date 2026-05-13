package Projet_groupe02.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import Projet_groupe02.demo.model.Cours;
import Projet_groupe02.demo.model.CustomUserDetails;
import Projet_groupe02.demo.model.FavCours;
import Projet_groupe02.demo.model.User;
import Projet_groupe02.demo.service.ClassService;
import Projet_groupe02.demo.service.FavoriteClassService;

@RestController
@CrossOrigin(origins = "*") //Bloque les CORPS en acceptant tout les noms de domaines (obligatoire pour FireFox)
public class FavoriteClassController {
    
    @Autowired
    FavoriteClassService service;

    @Autowired
    ClassService classService;

    //Pour savoir si une synthèse est en favoris ou non
    @GetMapping("/isFavoriteClass/{class_id}")
    public ResponseEntity<?> isFavoriteClass(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int class_id){
        return new ResponseEntity<>(service.isFavoriteClass(userDetails.getId(), class_id), HttpStatus.OK);
    }

    //Pour ajouter un cours en favoris
    @PostMapping("/newFavoriteClass/{cours_id}")
    public ResponseEntity<?> newFavoriteClass(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int cours_id){
        User user = userDetails.getUser();
        Cours cours = classService.getCoursInfo(cours_id);

        FavCours favCours = service.newFavotireClass(user, cours);
        return new ResponseEntity<>(favCours, HttpStatus.CREATED);
    }

    //Pour retirer un cours des favoris
    @DeleteMapping("/retrieveFavoriteClass/{cours_id}")
    public ResponseEntity<Void> retrieveFavoriteClass(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int cours_id){
        User user = userDetails.getUser();
        Cours cours = classService.getCoursInfo(cours_id);

        service.retrieveFavoriteClass(user, cours);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
