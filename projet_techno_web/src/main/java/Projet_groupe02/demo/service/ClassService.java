package Projet_groupe02.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Projet_groupe02.demo.model.Cours;
import Projet_groupe02.demo.repository.CoursRepository;

@Service
public class ClassService {
    
    @Autowired //Pour connecter automatiquement au repository défini dans le repertoire repository
    private CoursRepository repository;

    //Pour obtenir la liste des cours d'une faculte
    public List<Cours> getListFaculteClass(int faculte_id){
        return repository.getFaculteClass(faculte_id);
    }

    //Pour obtenir la liste des cours en favoris d'un utilisateur
    public List<Cours> getListFavoriteClass(int user_id){
        return repository.getFavoriteClass(user_id);
    }
    //Pour obtenir les informations d'un cours
    public Cours getCoursInfo(int cours_id){
        return repository.getReferenceById(cours_id);
    }

    /*
    POUR LES TEST : A SUPPRIMER ULTERIEUREMENT
    */
    public List<Cours> getListClasses(){
        return repository.findAll();
    }
}
