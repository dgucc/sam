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

import Projet_groupe02.demo.model.CustomUserDetails;
import Projet_groupe02.demo.model.SynthContent;
import Projet_groupe02.demo.model.Synthese;
import Projet_groupe02.demo.model.User;
import Projet_groupe02.demo.model.Cours;

import Projet_groupe02.demo.service.ClassService;
import Projet_groupe02.demo.service.SynthService;

@RestController
@CrossOrigin(origins = "*") //Bloque les CORPS en acceptant tout les noms de domaines (obligatoire pour FireFox)
public class SynthController {
    @Autowired
    private SynthService service;

    @Autowired
    private ClassService classService;

    /*========================================================
    POUR LES ACTIONS CREATE    
    ========================================================*/
    @PostMapping("/new_synth")
    public ResponseEntity<?> addNewSynth(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody SynthContent requestBody){
        System.out.println(requestBody.getCours_id() + " " + requestBody.getTitre() + " " + requestBody.getUrl());
        User author = customUserDetails.getUser();
        Cours cours = classService.getCoursInfo(requestBody.getCours_id());
        Synthese newSynth = new Synthese( cours, author, requestBody.getTitre(), requestBody.getUrl());

        return new ResponseEntity<>(service.addNewSynth(newSynth), HttpStatus.CREATED);
    }

    /*========================================================
    POUR LES ACTIONS UPDATE   
    ========================================================*/


    /*========================================================
    POUR LES ACTIONS READ   
    ========================================================*/
    //Pour obtenir la liste des synthèses d'un cours
    @GetMapping("/ClassSynthList/{class_id}")
    public ResponseEntity<?> getSynthInfo(@PathVariable int class_id){
        return new ResponseEntity<>(service.getClassSynthList(class_id), HttpStatus.OK);
    }

    //Pour obtenir les informations sur une synthèse
    @GetMapping("/SynthInfo/{synth_id}")
    public ResponseEntity<?> getSyntheseInfo(@PathVariable int synth_id){
        return new ResponseEntity<>(service.getSyntheseInfo(synth_id), HttpStatus.OK);
    }

    //Pour obtenir la liste des synthèses en favoris
    @GetMapping("/favoriteSynthList")
    public ResponseEntity<?> getListFavoriteSyntheses(@AuthenticationPrincipal CustomUserDetails userDetails){
        int user_id = userDetails.getId();
        return new ResponseEntity<>(service.getListFavoriteSyntheses(user_id), HttpStatus.OK); //Le HttpStatus.OK est là pour envoyer le signal 200
    }

    //Pour obtenir la liste des synthèses personnelles
    @GetMapping("/personnalSynth")
    public ResponseEntity<?> getListPersonnalSyntheses(@AuthenticationPrincipal CustomUserDetails userDetails){
        int user_id = userDetails.getId();
        return new ResponseEntity<>(service.getListPersonnalSyntheses(user_id), HttpStatus.OK);
    }

    /*========================================================
    POUR LES ACTIONS DELETE   
    ========================================================*/
    @DeleteMapping("/deleteSynth/{synth_id}")
    public ResponseEntity<Void> deleteSynthese(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int synth_id){
        String role = userDetails.getUser().getRole();
        Synthese synthese = service.getSyntheseInfo(synth_id);
        if(role == "admin" || userDetails.getUser().getId() == synthese.getUser().getId()){ //"Si c'est un admin ou l'auteur de la synthèse alors il peut la supprimer"
            service.deleteSynthese(synthese);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

