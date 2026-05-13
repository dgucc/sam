package Projet_groupe02.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Projet_groupe02.demo.model.Commentaire;
import Projet_groupe02.demo.repository.Commentairepository;

@Service
public class CommentaireService {

    @Autowired //Pour connecter automatiquement au repository défini dans le repertoire repository
    private Commentairepository commentaireRepository;


    //Pour obtenir les informations sur un commentaire
    public Commentaire getCommentaireInfo(int commentaire_id){
        return commentaireRepository.getReferenceById(commentaire_id);
    }

    //Pour obtenir la liste des commentaires d'une synthese
    public List<Commentaire> getCommentaires(int synthese_id) {
        return commentaireRepository.getCommentairesBySyntheseId(synthese_id);
    }

    //Pour poster un nouveau commentaire
    public Commentaire postCommentaire(Commentaire newCommentaire) {
        return commentaireRepository.save(newCommentaire);
    }

    //Pour supprimer un commentaire
    public void deleteCommentaire(Commentaire commentaire){
        commentaireRepository.delete(commentaire);
    }
}
