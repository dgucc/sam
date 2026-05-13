package Projet_groupe02.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Projet_groupe02.demo.model.FavSynth;
import Projet_groupe02.demo.model.FavSynthId;
import Projet_groupe02.demo.model.Synthese;
import Projet_groupe02.demo.model.User;
import Projet_groupe02.demo.repository.UserFavoriteSynthRepository;

@Service
public class FavoriteSynthService {
    
    @Autowired
    UserFavoriteSynthRepository userFavoriteSynthRepository;

    //Pour ajouter une synthèse en favoris
    public FavSynth newFavoriteSynth(User user, Synthese synthese) {
        return userFavoriteSynthRepository.save(new FavSynth(new FavSynthId(synthese.getId(), user.getId()), synthese, user));
    }

    //Pour retirer une synhtèse en favoris
    public void retrieveFavoriteSynth(User user, Synthese synthese){
        userFavoriteSynthRepository.delete(new FavSynth(new FavSynthId(synthese.getId(), user.getId()), synthese, user));
    }

    //Pour savoir si une synthèse est en favoris ou non
    public boolean isFavoriteSynth(int user_id, int synth_id){
        List<FavSynth> answer = userFavoriteSynthRepository.isFavSynth(synth_id, user_id);
        if(answer.size() == 0){return false;}
        else{return true;}
    }
}
