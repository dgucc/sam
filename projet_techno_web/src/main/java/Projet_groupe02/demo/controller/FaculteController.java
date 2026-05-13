package Projet_groupe02.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import Projet_groupe02.demo.service.FaculteService;

@RestController
@CrossOrigin(origins = "*") //Bloque les CORPS en acceptant tout les noms de domaines (obligatoire pour FireFox)
public class FaculteController {
  
    @Autowired
    private FaculteService service;

    //Pour obtenir la liste des facultes
    @GetMapping("/facultes")
    public ResponseEntity<?> getListFacultes(){
        return new ResponseEntity<>(service.getListFacultes(), HttpStatus.OK);
    }

    //Pour obtenir les informations sur une faculte
    @GetMapping("/faculte_info/{faculte_id}")
    public ResponseEntity<?> getFaculteInfo(@PathVariable int faculte_id){
        return new ResponseEntity<>(service.getFaculteInfo(faculte_id), HttpStatus.OK);
    }
}
