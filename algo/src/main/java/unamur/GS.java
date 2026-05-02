package unamur;


import java.util.ArrayList;

public class GS implements Algorithms.GS{

    /** 
     * Algorithme qui calcule une sequence de mouvements pour resoudre le niveau a partir de l'etat donne, en appliquant une 
     * strategie gloutonne c'est a dire qu'a chaque etape on vas chercher a effectuer une action dont le gain de score immediat est maximal 
     * 
     * ce choix est localement optimal il fournit uniquement une approximation du score maximal.
     *  
     * Lorsque deux actions mènent à la meme augmentation de score, l’algorithme applique l’ordre de préférence suivant :
     * Nord > Sud > Ouest > Est
     * @requires state != null
     * @requires state.invaders != null && state.buildings != null
     * @ensures \result != null
     * @ensures \result contient uniquement les caracteres 'n', 's', 'e', 'w'
     * @ensuresle niveau est terminé apres avoir appliqué \result a state
     * 
     * @param state : l'etat actuelle du jeu . il n'est pas modifié par la methode 
     * @return : sequence de mouvements sous forme de string ('n' = nord, 's'=sud, 'e'=est, 'w'=ouest)
     *           renvoie "" si le niveau est deja terminé.
     
    
    @author Maya ( specification)
    @author Maya (implementation)
    @author Maya (tests/debug)
    @version 1.0 (29/03/2026)
    */

    public static String greedySolution(State state) {
        // on travaille sur une copie pour ne pas modifier l'etat original
        State current = state.clone();
        StringBuilder result = new StringBuilder();

        // continuer tant que le robot a de l'electrecite
        while (current.gundamElec > 0) {
            // trouver les actions autorisees
            ArrayList<Action> actions = availableActions(current);

            //choisir l'action qui rapporte le plus de gain
            Action best = bestAction(current, actions);

            if (best == null) break; // aucune action disponible

            //appliquer l'action et noter le mouvement effectuer 
            int levelBefore = current.nbLevel;
            Algorithms.applyAction(current, best);
            
            result.append(switch (best) {
                case BURST_NORTH -> 'n';
                case BURST_SOUTH -> 's';
                case BURST_EAST -> 'e';
                case BURST_WEST -> 'w';
                default -> '?';
            });
            //si le robot est sorti du secteur alors c'est la fin du niveau
            if (current.nbLevel != levelBefore) break;
        }
        return result.toString();

    }
    /** renvoie la liste des mouvements possibles selon l'etat actuel. 
     * un mouvement est interdit si c'est la meme direction qu'avant ou 
     * bien son opposee
     * si c'est le tout premier mouvement ( debut ) alors les 4 directions sont disponibles
     * 
     * @requires state != null
     * @ensures \result != null
     * @ensures \result.size() >= 2 && \result.size() <= 4
     * 
     * @param state : l'etat courant du jeu
     * @return : liste des actions autorisees
     * 
     * @author Maya Bouhamdani (specification)
     * @author Maya Bouhamdani (implementation)
     * @author Maya Bouhamdani ( tests/debug)
     * @version 1.0 (02/04/2026)
     */
    static ArrayList<Action> availableActions(State state){
        ArrayList<Action> actions = new ArrayList<>(); //liste vide qui contiendra les directions autorisees

        Action[] directions = {
            Action.BURST_NORTH,
            Action.BURST_SOUTH,
            Action.BURST_EAST,
            Action.BURST_WEST
        };
        for (Action a : directions) {
            if ( state.prevAction == null) {
                //premier coup :
                actions.add(a); // toutes les directions sont autorisées

            }
            else if (a!= state.prevAction && !isOpposite(a, state.prevAction)){
                actions.add(a); // pas la meme direction qu'avant et pas la direction opposee
            }

        }
        return actions;

    }

    /**
     * Verifie si deux directions sont opposees (Nord-Sud ou Est-Ouest)
     * @param a la premiere direction
     * @param b la deuxieme direction
     * @return true si les directions sont opposees
     */
    static boolean isOpposite(Action a, Action b){
        return (a == Action.BURST_NORTH && b == Action.BURST_SOUTH) ||
               (a == Action.BURST_SOUTH && b == Action.BURST_NORTH) ||
               (a == Action.BURST_EAST && b == Action.BURST_WEST) ||
               (a == Action.BURST_WEST && b == Action.BURST_EAST);
    }
    /**
     * Renvoie l'action qui rapporte le plus de gain directement
     * en cas d'egalite, l'ordre de preference est : Nord > Sud > Ouest > Est.
     * 
     * @requires state != null
     * @requires actions != null && actions.size() > 0
     * @ensures \result != null
     * @ensures \result est contenu dans actions
     * 
     * @param state : l'etat courant du jeu
     * @param actions : liste des actions disponibles 
     * @return : l'action qui rapporte le gain immediat maximal
     * 
     * @author Maya Bouhamdani(specification)
     * @author Maya Bouhamdani ( implementation )
     * @author Maya Bouhamdani (tests/debug)
     * @version 1.0 (07/04/2026)
     */
    static Action bestAction(State state, ArrayList<Action> actions){
        Action best = null;
        int bestGain = -1;

        //on parcourt dans l'ordre de priorite : N > S > O> E
        //comme ca en cas d'egalite, nord sera choisi automatiquement puis sud ...etc
        Action[] priority = { //l'ordre de priorite 
            Action.BURST_NORTH,
            Action.BURST_SOUTH,
            Action.BURST_WEST,
            Action.BURST_EAST,
        };
        for ( Action prio : priority) {
            //on saute si cette direction n'est pas disponible
            if (!actions.contains(prio)) continue;

            // on simule l'action sur une copie
            State copy = state.clone();
            Algorithms.applyAction(copy, prio);
            //on calcule le gain
            int gain = copy.levelScore - state.levelScore;
            //en cas d'egalite on garde le premier trouve 
            if ( gain > bestGain) {
                bestGain = gain;
                best = prio ;
            }
        }
        return best; // on renvoie la meilleure action trouvée
    }
}