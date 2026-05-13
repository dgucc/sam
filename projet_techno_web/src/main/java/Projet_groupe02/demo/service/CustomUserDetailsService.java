package Projet_groupe02.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Projet_groupe02.demo.model.CustomUserDetails;
import Projet_groupe02.demo.model.User;
import Projet_groupe02.demo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));
        return new CustomUserDetails(user);
    }
}
