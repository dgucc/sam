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

import Projet_groupe02.demo.model.CustomUserDetails;
import Projet_groupe02.demo.model.FavSynth;
import Projet_groupe02.demo.model.Synthese;
import Projet_groupe02.demo.model.User;
import Projet_groupe02.demo.service.FavoriteSynthService;
import Projet_groupe02.demo.service.SynthService;

@RestController
@CrossOrigin(origins = "*") //Bloque les CORPS en acceptant tout les noms de domaines (obligatoire pour FireFox)
public class FavoriteSynthController {
    
    @Autowired
    FavoriteSynthService service;

    @Autowired
    SynthService synthService;

    //Pour savoir si une synthèse est en favoris ou non
    @GetMapping("/isFavoriteSynth/{synth_id}")
    public ResponseEntity<?> isFavoriteSynth(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int synth_id){
        return new ResponseEntity<>(service.isFavoriteSynth(userDetails.getId(), synth_id), HttpStatus.OK);
    }

    //Pour ajouter une synthèse en favoris
    @PostMapping("/newFavoriteSynth/{synth_id}")
    public ResponseEntity<?> newFavoriteSynth(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int synth_id){
        boolean isAlreadyFavorite = service.isFavoriteSynth(userDetails.getId(), synth_id);
        if(isAlreadyFavorite){
            return null;
        }
        User user = userDetails.getUser();
        Synthese synthese = synthService.getSyntheseInfo(synth_id);

        FavSynth favCours = service.newFavoriteSynth(user, synthese);
        return new ResponseEntity<>(favCours, HttpStatus.CREATED);
    }
    
    //Pour retirer une synthèse des favoris
    @DeleteMapping("/retrieveFavoriteSynth/{synth_id}")
    public ResponseEntity<Void> retrieveFavoriteSynth(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int synth_id){
        User user = userDetails.getUser();
        Synthese synthese = synthService.getSyntheseInfo(synth_id);

        service.retrieveFavoriteSynth(user, synthese);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
