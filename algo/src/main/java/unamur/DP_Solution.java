package unamur;

/**
 * @overview : une classe pour stocker les informations relatives à une solution (utilisé pour la classe DP)
 */
public class DP_Solution{
    private int pb;
    private String best_seq;

    //Constructeurs
    DP_Solution(int pb, String best_seq){
        this.pb = pb;
        this.best_seq = best_seq;
    }

    DP_Solution(DP_Solution solution){
        this.pb = solution.pb;
        this.best_seq = solution.best_seq;
    }

    //Getters
    public int get_pb(){return this.pb;}
    public String get_best_seq(){return this.best_seq;}

    //Setters
    public void set_pb(int new_pb){this.pb = new_pb;}
    public void set_best_seq(String new_best_seq){this.best_seq = new_best_seq;}

    //Fonctions statiques
    /**
     * Retourne la solution qui possède le meilleur score
     * 
     * @requires sol1 != null && sol2 != null
     * @param sol1 : la première solution
     * @param sol2 : la deuxième solution
     * @return la solution avec le meilleur score
     */
    static public DP_Solution best_solution(DP_Solution sol1, DP_Solution sol2){
        if(sol1.get_pb() >= sol2.get_pb()){
            return sol1;
        }
        else{
            return sol2;
        }
    }
}