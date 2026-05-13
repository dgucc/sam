package Projet_groupe02.demo.model;

//Une classe pour contenir les informations dont on a besoin pour créer une synthèse (body d'une requête POST)
public class SynthContent {
    private String titre;
    private String url;
    private int cours_id;


    public SynthContent() {
    }
    
    public SynthContent(String titre, String url, int cours_id) {
        this.titre = titre;
        this.url = url;
        this.cours_id = cours_id;
    }


    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getCours_id() {
        return cours_id;
    }
    public void setCours_id(int cours_id) {
        this.cours_id = cours_id;
    }

    
}
