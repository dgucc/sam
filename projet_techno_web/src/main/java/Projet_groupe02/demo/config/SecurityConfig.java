package Projet_groupe02.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import Projet_groupe02.demo.model.User;
import Projet_groupe02.demo.repository.UserRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    //Intercepte les requêtes http avant le controller
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        logger.info(http.toString());

        return http
                // Pour envoyer un token csrf à l'utilisateur afin de se protéger des attaques csrf
                // .csrf(csrf -> csrf
                //             .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //     )
                .csrf(csrf -> csrf.disable()) //Pour débugger
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()) //On vérifie que l'utilisateur est identifié pour n'importe quelle requête
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .build();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        logger.info(http.toString());
//        return http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
//                .formLogin(form -> form
//                        .loginPage("/Authentication.html") // Spécifier le chemin vers la page de login personnalisée
////                        .defaultSuccessUrl("/faculte_page.html") // Rediriger après une connexion réussie
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .permitAll())
//                .build();
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole("admin");
                admin.setEmail("samuel.gucciardi@student.unamur.be");

                userRepository.save(admin);
                System.out.println("Utilisateur admin créé");
            }
            if (userRepository.findByUsername("cad").isEmpty()){
                User casual = new User();
                casual.setUsername("cad");
                casual.setPassword(passwordEncoder.encode("cad"));
                casual.setRole("casual");
                casual.setEmail("ensenfou@gmail.com");

                userRepository.save(casual);
            }
        };
    }
}
