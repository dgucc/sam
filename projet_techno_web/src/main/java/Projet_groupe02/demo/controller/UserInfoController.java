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
import Projet_groupe02.demo.model.User;
import Projet_groupe02.demo.service.UserService;

@RestController
@CrossOrigin(origins = "*") //Bloque les CORPS en acceptant tout les noms de domaines (obligatoire pour FireFox)
public class UserInfoController {
    
    @Autowired
    private UserService service;

    /*========================================================
    POUR LES ACTIONS CREATE    
    ========================================================*/
    

    /*========================================================
    POUR LES ACTIONS UPDATE   
    ========================================================*/


    /*========================================================
    POUR LES ACTIONS READ   
    ========================================================*/
    //Pour que l'utilisateur accède à ses infos
    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails){

        int user_id = userDetails.getId();
        User user = service.getUserInfo(user_id);
        user.setPassword(":)"); //Pour ne pas retourner le mot de passe même scripté
        
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Pour obtenir les informations d'un utilisateur
    @GetMapping("/userInfo/{user_id}")
    public ResponseEntity<?> getOtherUserInfo(@PathVariable int user_id){
        User user = service.getUserInfo(user_id);
        user.setPassword(null);
        
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /*========================================================
    POUR LES ACTIONS DELETE   
    ========================================================*/


    
    /*========================================================
    POUR LES TEST : A SUPPRIMER ULTERIEUREMENT
    ========================================================*/
}
