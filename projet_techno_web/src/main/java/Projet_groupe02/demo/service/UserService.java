package Projet_groupe02.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Projet_groupe02.demo.model.User;
import Projet_groupe02.demo.repository.UserRepository;

@Service
public class UserService {
    @Autowired //Pour connecter automatiquement au repository défini dans le repertoire repository
    private UserRepository repository;

    //Pour obtenir l'email, le nom et le rôle d'un utilisateur
    public User getUserInfo(int user_id){
        return repository.getUserInfo(user_id);
    }
}
