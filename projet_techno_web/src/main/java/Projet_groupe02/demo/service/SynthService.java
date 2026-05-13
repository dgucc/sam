package Projet_groupe02.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Projet_groupe02.demo.model.Synthese;
import Projet_groupe02.demo.repository.SynthRepository;

@Service
public class SynthService {
    
    @Autowired
    private SynthRepository repository;

    //Pour obtenir la liste des synthèses d'un cours
    public List<Synthese> getClassSynthList(int cours_id){
        return repository.getClassSynthList(cours_id);
    }

    //Pour obtenir les informations sur une synthese
    public Synthese getSyntheseInfo(int synth_id){
        return repository.getReferenceById(synth_id);
    }

    //Pour obtenir la liste des synthèses personnelles d'un utilisateur
    public List<Synthese> getListPersonnalSyntheses(int user_id){
        return repository.getPersonnalSyntheses(user_id);
    }

    //Pour obtenir la liste des syntheses en favoris d'un utilisateur
    public List<Synthese> getListFavoriteSyntheses(int user_id){
        return repository.getFavoriteSynth(user_id);
    }

    //Pour supprimer une synthèse
    public void deleteSynthese(Synthese synthese){
        repository.delete(synthese);
    }

    //Pour créer une nouvelle synthèse
    public Synthese addNewSynth(Synthese newSynth) {
        return repository.save(newSynth);
    }
}
