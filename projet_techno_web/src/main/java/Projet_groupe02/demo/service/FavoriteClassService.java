package Projet_groupe02.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Projet_groupe02.demo.model.Cours;
import Projet_groupe02.demo.model.FavCours;
import Projet_groupe02.demo.model.FavCoursId;
import Projet_groupe02.demo.model.User;
import Projet_groupe02.demo.repository.UserFavoriteClassRepository;

@Service
public class FavoriteClassService {
    
    @Autowired
    UserFavoriteClassRepository userFavoriteClassRepository;

    //Pour ajouter un cours en favoris
    public FavCours newFavotireClass(User user, Cours cours) {
        return userFavoriteClassRepository.save(new FavCours(new FavCoursId(cours.getId(), user.getId()), cours, user));
    }

    //Pour retirer un cours en favoris
    public void retrieveFavoriteClass(User user, Cours cours){
        userFavoriteClassRepository.delete(new FavCours(new FavCoursId(cours.getId(), user.getId()), cours, user));
    }

    //Pour savoir si un cours est en favoris ou non
    public boolean isFavoriteClass(int user_id, int class_id){
        List<FavCours> answer = userFavoriteClassRepository.isFavClass(class_id, user_id);
        if(answer.size() == 0){return false;}
        else{return true;}
    }
}
