package Projet_groupe02.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Projet_groupe02.demo.model.Faculte;
import Projet_groupe02.demo.repository.FaculteRepository;

@Service
public class FaculteService {

    @Autowired //Pour connecter automatiquement au repository défini dans le repertoire repository
    private FaculteRepository repository;

    //Pour obtenir la liste des facultes
    public List<Faculte> getListFacultes(){
        return repository.findAll();
    }

    //Pour obtenir les informations d'une faculte
    public Faculte getFaculteInfo(int faculte_id){
        return repository.getReferenceById(faculte_id);
    }
}