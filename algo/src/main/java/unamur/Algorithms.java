package unamur;

import java.util.ArrayList;
import java.util.Random;

public class Algorithms {
    static Random rng = new Random(42);  // Use this for any random elements in your implementations

    static void main(String[] args) {
        // Your code here ↓
        System.out.println("Hello world!");
    }

    /* --- Generate & Test --- */
    interface GT {
        /*
         * Constraints for invaders:
         *   - Each cell has 25% chance of containing an invader
         *   - 1-ceil(boardWidth/3) invaders / row
         *   - 1-ceil(boardWidth/3) invaders / col
         *   - Invader HP between 1 and 99 (inclusive)
         *   - Total invader HP in the city must be between 5 * boardWidth**2 and 10 * boardWidth**2 (inclusive)
         *   - No invader on the central cell (where the player starts)
         *
         * Constraints for buildings:
         *  - number of building is chosen between 1-4
         *  - location of building is random (uniform)
         *  - no building where there is an invader (after rearranging invaders in DC)
         *
         * Additional constraints:
         *  - Nothing can be on the central cell (where the player starts)
         */

        /* Official methods (signature must remain unchanged) */

        static boolean testInvaders(int[][] invaders) {
            /* Returns true if the invaders are valid, false otherwise */
            // Your code here ↓
            return false;
        }

        static void generateInvaders(int[][] invaders) {
            /* Loads *valid* invaders into the invaders array. */
            // Your code here ↓
        }

        static void generateBuildings(int[][] buildings, int[][] invaders) {
            /* Loads buildings at *valid* locations into the buildings array. */
            // Your code here ↓
        }

        /* ↓ If you need additional methods to solve GT, write them here. ↓ */

    }

    /* --- Divide & Conquer --- */
    interface DC {

        /* Official methods (signature must remain unchanged) */
        /**
         * Réorganise en place la grille d'aliens en 4 quadrants selon leur force totale :
         * le quadrant NW reçoit la force la plus haute, SE la deuxième, NE la troisième
         * et SW la plus basse. Cette logique est appliquée récursivement à l'intérieur
         * de chaque quadrant.
         *
         * @requires invadersToSort != null
         * @requires invadersToSort.length == invadersToSort[0].length
         *           && invadersToSort.length % 2 == 1  // carré et impair pour avoir une case centrale
         * @requires toutes les valeurs de invadersToSort sont entre 0 et 99 inclus
         *
         * @ensures les quadrants sont ordonnés NW(max) > SE(2e) > NE(3e) > SW(min)
         * @ensures la somme totale des valeurs de invadersToSort ne change pas
         * @ensures la ligne et colonne centrale de invadersToSort ne changent pas
         * @ensures la même logique est appliquée récursivement à l'intérieur de chaque quadrant
         * @ensures si invadersToSort.length < 3, la grille ne change pas
         * @ensures \result est égal à la somme totale de tous les aliens
         *
         * @throws IllegalArgumentException si invadersToSort == null
         *
         * @param invadersToSort tableau d'aliens à réorganiser en quadrants, modifié en place par la méthode
         * @return la somme totale des forces des aliens
         *
         * @author Mohamed (spécification)
         * @author Mohamed (implémentation)
         * @version 1.1 (29/03/2026)
         */

        static int sortCity(int[][] invadersToSort) {
            /*
             * Modifies invaders in-memory so that sectors are arranged according to the number of invaders inside
             * of them.
             *
             * This function arranges every sector recursively so that the biggest sub-sector is on the top-left (NW),
             * the second biggest on the bottom right (SE), the third on the top right (NE), and the last on the
             * bottom left (SW).
             *
             * Ex:
             * in: 1, 2
             *     3, 4
             * out:
             *     4, 2
             *     1, 3
             *
             * If the number of rows/cols is odd, the middle row/col are ignored.
             */
            // Your code here ↓
            return 0;
        }

        /* ↓ If you need additional methods to solve DC, write them here. ↓ */

    }

    interface DP {

        /* Official methods (signature must remain unchanged) */
        // If you need a "global variable" to store your memoized values, place it here ↓

        /**
         * @requires State != null
         * @ensures \result = Sequence_parfaite[5] : les 5 premières actions de la solution qui maximise le score du joueur
         * @throws IllegalArgumentException si State == null
         * 
         @author Samuel Gucciardi (specification)
         @author (implementation)
         @author (tests/debug)
         @version 0.0 (29/03/2026)
         */
        static String perfectSolution(State state) throws IllegalArgumentException{
            /*
             * Returns a string of actions corresponding to the optimal path for the current level, starting from the given state.
             */
            // Your code here ↓
            return "";
        }

        /* ↓ If you need additional methods to solve DP, write them here. ↓ */

    }

    interface GS {

        /* Official methods (signature must remain unchanged) */

        static String greedySolution(State state) {
            /*
             * Given a state, computes a "good but likely not perfect" sequence of actions that
             * solves the level with a good score. It should work by repeatedly selecting the action that gives the
             * best immediate score increase.
             */
            // Your code here ↓
            return "";
        }

        /* ↓ If you need additional methods to solve GS, write them here. ↓ */

    }

    /* --- Common utility functions --- */
    static Action charToAction(char c) throws IllegalArgumentException {
        return switch (c) {
            case 'n' -> Action.BURST_NORTH;
            case 'e' -> Action.BURST_EAST;
            case 's' -> Action.BURST_SOUTH;
            case 'w' -> Action.BURST_WEST;
            default -> throw new IllegalArgumentException("Invalid action character: " + c);
        };
    }

    static Action oppositeAction(Action action) {
        return switch (action) {
            case BURST_NORTH -> Action.BURST_SOUTH;
            case BURST_EAST -> Action.BURST_WEST;
            case BURST_SOUTH -> Action.BURST_NORTH;
            case BURST_WEST -> Action.BURST_EAST;
            default -> throw new IllegalArgumentException("Action is not a burst action.");
        };
    }

    static String actionsToString(ArrayList<Action> actions) {
        StringBuilder sb = new StringBuilder();
        for (Action action : actions) {
            sb.append(switch (action) {
                case BURST_NORTH -> 'n';
                case BURST_EAST -> 'e';
                case BURST_SOUTH -> 's';
                case BURST_WEST -> 'w';
                default -> '?';
            });
        }
        return sb.toString();
    }

    /**
     * Met à jour l'état du jeu selon une action
     *
     * @modifies this
     * @effects l'état du jeu selon l'action du joueur c'est à dire :
     * - le changement de la position du joueur selon son nombre de charge depuis le début du niveau
     * - la potentielle destruction de building si un d'entre eux a été percuté par le robot
     * - la potentielle mort de un ou plusieurs aliens si certains d'entre eux ont été percuté par le robot
     * - modifie le score total du joueur ainsi que le score du niveau
     * - le nombre de charge depuis le début du niveau est augmenté de 1
     * - la batterie du robot diminue de 1
     * - l'action précédente devient celle qui est donnée en paramètre
     * Si le joueur sort des limites de la ville :
     *      - lui accorde un indice en plus si aucun building n'a été détruit
     *      - augmente de 1 le numéro du niveau
     * @requires state != null
     * @throws IllegalArgumentException si state == null
     *
     * @param state : l'état du jeu avant l'action
     * @param action : l'action du joueur
     *
     * @author Samuel Gucciardi (specification)
     * @author Maya Bouhamdani (implementation)
     * @author (tests/debug)
     * @version 1.0 (02/04/2026)
     */
    static void applyAction(State state, Action action) throws IllegalArgumentException {
        if (state == null) throw new IllegalArgumentException("state == null");

        // Déterminer le déplacement selon la direction
        int dx = 0, dy = 0;
        switch (action) {
            case BURST_NORTH -> dy = 1;
            case BURST_SOUTH -> dy = -1;
            case BURST_EAST  -> dx = 1;
            case BURST_WEST  -> dx = -1;
            default -> throw new IllegalArgumentException("Action invalide");
        }

        int distance = state.nbBurst + 1;
        int boardSize = state.invaders.length;
        int scoreGained = 0;
        int finalX = state.gundamPos[0];
        int finalY = state.gundamPos[1];

        // Avancer case par case
        for (int step = 1; step <= distance; step++) {
            int newX = state.gundamPos[0] + dx * step;
            int newY = state.gundamPos[1] + dy * step;

            // Sorti du plateau → fin du niveau
            if (newX < 0 || newX >= boardSize || newY < 0 || newY >= boardSize) {
                state.gundamPos[0] = -1;
                state.gundamPos[1] = -1;
                state.levelScore += scoreGained;
                state.gameScore += scoreGained;
                state.nbBurst++;
                state.gundamElec--;
                state.prevAction = action;
                state.nbLevel++;
                return;
            }

            // Bâtiment → on s'arrête sur la case précédente
            if (state.buildings[newX][newY] == 1) {
                state.buildings[newX][newY] = -1;
                state.gundamPos[0] = finalX;
                state.gundamPos[1] = finalY;
                state.levelScore += scoreGained;
                state.gameScore += scoreGained;
                state.nbBurst++;
                state.gundamElec--;
                state.prevAction = action;
                return;
            }

            // Alien → on l'écrase et on continue
            if (state.invaders[newX][newY] > 0) {
                scoreGained += state.invaders[newX][newY];
                state.invaders[newX][newY] = 0;
            }

            // Mettre à jour la dernière case valide
            finalX = newX;
            finalY = newY;
        }

        // Arrivée normale
        state.gundamPos[0] = finalX;
        state.gundamPos[1] = finalY;
        state.levelScore += scoreGained;
        state.gameScore += scoreGained;
        state.nbBurst++;
        state.gundamElec--;
        state.prevAction = action;
}

    /**
     * Retourne le score d'une série d'action
     * @param startingState l'état initial
     * @param nextActions la séquence d'action
     * @return le score final si les actions sont valides et 0 sinon
     * 
     * @author Samuel Gucciardi (specification)
     * @author Samuel Gucciardi (implementation)
     * @author (tests/debug)
     * @version 1.1 (27/04/2026)
    */
    static int pathScore(State startingState, String nextActions) {
        State copy = startingState.clone(); //On créé une copie pour ne pas modifier l'état principale
        int score = 0;

        //On exécute consécutivement chaque action de la liste d'action
        for(int i = 0 ; i < nextActions.length() ; i++){
            score += actionScore(copy, Algorithms.charToAction(nextActions.charAt(i)));
            Algorithms.applyAction(copy, Algorithms.charToAction(nextActions.charAt(i)));
        }

        return score;
    }

    /**
     * Retourne le score d'une seule action
     * @param startingState l'état initial
     * @param action l'action
     * @return le score obtenu après cette action
     * 
     * @author Samuel Gucciardi (specification)
     * @author Samuel Gucciardi (implementation)
     * @author (tests/debug)
     * @version 2.0 (27/04/2026)
    */
   private static int actionScore(State state, Action action){
    State copy = state.clone();
    Algorithms.applyAction(copy, action);
    int gain = copy.levelScore - state.levelScore;
    return gain;
   }

   static String actionToString(Action action){
        switch(action){
            case BURST_NORTH : return "n";
            case BURST_EAST : return "e";
            case BURST_SOUTH : return "s";
            case BURST_WEST : return "w";
            default : return "?";
        }
    }

    //Pour pouvoir déplacer le robot avec des contrôles zqsd classique
    static String ZQSDtoNEWS(String ZQSD){
        switch(ZQSD){
            case "z" : return "n";
            case "d" : return "e";
            case "s" : return "s";
            case "q" : return "w";
            default : return "?";
        }
    }
}
