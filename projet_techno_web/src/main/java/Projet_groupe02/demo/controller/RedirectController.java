package Projet_groupe02.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@CrossOrigin(origins = "*") //Bloque les CORPS en acceptant tout les noms de domaines (obligatoire pour FireFox)
public class RedirectController {

    //Pour rediriger vers la page d'une faculté en ajoutant à l'url un query parameter pour avoir l'id de la faculte
    @GetMapping("/faculte/{faculte_id}")
    public String redirect_to_faculte(@PathVariable int faculte_id) {
        return "redirect:/faculte_page.html?id=" + faculte_id;
    }

    //Pour rediriger vers la page d'une synthèse
    @GetMapping("/Synthese/{synth_id}")
    public String redirect_to_synthese(@PathVariable int synth_id) {
        return "redirect:/synthese_page.html?id=" + synth_id;
    }

    //Pour rediriger vers la page d'un cours
    @GetMapping("/cours/{cours_id}")
    public String redirect_to_class(@PathVariable int cours_id) {
        return "redirect:/course_page.html?id=" + cours_id;
    }

    //Pour rediriger vers la page des synthèses personnelles
    @GetMapping("/my_synth")
    public String redirect_to_personnal_synth(){
        return "redirect:/my_synth_page.html";
    }

    
    /*========================================================
    POUR LES TEST : A SUPPRIMER ULTERIEUREMENT
    ========================================================*/
}
