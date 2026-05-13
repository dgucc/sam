package Projet_groupe02.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import Projet_groupe02.demo.model.CustomUserDetails;
import Projet_groupe02.demo.service.ClassService;

@RestController
@CrossOrigin(origins = "*") //Bloque les CORPS en acceptant tout les noms de domaines (obligatoire pour FireFox)
public class ClassController {
    @Autowired
    private ClassService service;

    /*========================================================
    POUR LES ACTIONS CREATE    
    ========================================================*/
    

    /*========================================================
    POUR LES ACTIONS UPDATE   
    ========================================================*/


    /*========================================================
    POUR LES ACTIONS READ   
    ========================================================*/
    //Pour obtenir la liste des cours d'une faculte
    @GetMapping("/FaculteClassList/{faculte_id}")
    public ResponseEntity<?> getListFaculteClass(@PathVariable int faculte_id){
        return new ResponseEntity<>(service.getListFaculteClass(faculte_id), HttpStatus.OK);
    }

    //Pour obtenir la liste des cours en favoris de l'utilisateur
    @GetMapping("/favoriteClassList")
    public ResponseEntity<?> getListFavoriteClass(@AuthenticationPrincipal CustomUserDetails userDetails){
        int user_id = userDetails.getId();
        return new ResponseEntity<>(service.getListFavoriteClass(user_id), HttpStatus.OK);
    }

    //Pour avoir les informations d'un cours
    @GetMapping("/CoursInfo/{cours_id}")
    public ResponseEntity<?> getCoursInfo(@PathVariable int cours_id){
        return new ResponseEntity<>(service.getCoursInfo(cours_id), HttpStatus.OK);
    }

    /*========================================================
    POUR LES ACTIONS DELETE   
    ========================================================*/


    
    /*========================================================
    POUR LES TEST : A SUPPRIMER ULTERIEUREMENT
    ========================================================*/
    //Pour obtenir tous les cours de la bdd
    @GetMapping("/test/coursList")
    public ResponseEntity<?> getListClasses(){
        return new ResponseEntity<>(service.getListClasses(), HttpStatus.OK);
    }
}
