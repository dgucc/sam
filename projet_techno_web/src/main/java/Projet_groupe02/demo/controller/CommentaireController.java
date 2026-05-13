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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Projet_groupe02.demo.model.Commentaire;
import Projet_groupe02.demo.model.CommentaireContent;
import Projet_groupe02.demo.model.CustomUserDetails;
import Projet_groupe02.demo.model.Synthese;
import Projet_groupe02.demo.model.User;
import Projet_groupe02.demo.service.CommentaireService;
import Projet_groupe02.demo.service.SynthService;

@RestController
@CrossOrigin(origins = "*") //Bloque les CORPS en acceptant tout les noms de domaines (obligatoire pour FireFox)
public class CommentaireController {

    @Autowired
    private CommentaireService service;

    @Autowired SynthService synthService;

    /*========================================================
    POUR LES ACTIONS CREATE    
    ========================================================*/
    @PostMapping("/PostComment/{synthese_id}")
    public ResponseEntity<?> postCommentaire(@RequestBody CommentaireContent commentaireContent, @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int synthese_id) {
        Synthese synthese = synthService.getSyntheseInfo(synthese_id);
        User author = userDetails.getUser();

        Commentaire newCommentaire = new Commentaire(commentaireContent.getContenu(), author, synthese);
        return new ResponseEntity<>(service.postCommentaire(newCommentaire), HttpStatus.CREATED);
    }

    /*========================================================
    POUR LES ACTIONS UPDATE   
    ========================================================*/



    /*========================================================
    POUR LES ACTIONS READ   
    ========================================================*/
    @GetMapping("/commentaires/{synthese_id}")
    public ResponseEntity<?> getCommentaires(@PathVariable int synthese_id) {
        return new ResponseEntity<>(service.getCommentaires(synthese_id), HttpStatus.OK);
    }

    /*========================================================
    POUR LES ACTIONS DELETE   
    ========================================================*/
    @DeleteMapping("/DeleteComment/{commentaire_id}")
    public ResponseEntity<?> deleteCommentaire(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int commentaire_id){
        String role = userDetails.getUser().getRole();
        Commentaire commentaire = service.getCommentaireInfo(commentaire_id);
        if(role == "admin" || userDetails.getUser().getId() == commentaire.getUser().getId()){ //"Si c'est un admin ou l'auteur du commentaire alors il peut le supprimer"
            service.deleteCommentaire(commentaire);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
