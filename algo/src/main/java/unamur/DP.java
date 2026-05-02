package unamur;

public class DP implements Algorithms.DP{
    /* Official methods (signature must remain unchanged) */
        // If you need a "global variable" to store your memoized values, place it here ↓

        /**
         * Returns a string of actions corresponding to the optimal path for the current level, starting from the given state.
         * 
         * @requires State != null
         * @ensures \result = Sequence_parfaite[5] : les 5 premières actions de la solution qui maximise le score du joueur
         * 
         @author Samuel Gucciardi (specification)
         @author (implementation)
         @author (tests/debug)
         @version 0.0 (29/03/2026)
         */
        static String perfectSolution(State state){
            State copy;
            Action[] direction = {Action.BURST_NORTH, Action.BURST_EAST, Action.BURST_SOUTH, Action.BURST_WEST};
            DP_Solution[] solution = {null, null, null, null};

            for(int i = 0 ; i < direction.length ; i++){
                //On crée une copie de l'état actuelle pour y appliquer une des quatres directions de départ possible
                copy = state.clone();
                Algorithms.applyAction(copy, direction[i]);

                //On lance une appel récursif sur cette première direction
                // solution[i] = new Solution(0, util.actionToString(direction[i]));
                solution[i] = perfectLocalSolution(copy, new DP_Solution(0, Algorithms.actionToString(direction[i])));
            }

            //On retourne la meilleur des 4 solutions
            DP_Solution perfect_solution = new DP_Solution(solution[0]);
            for(int i = 1 ; i < solution.length ; i++){
                perfect_solution = DP_Solution.best_solution(perfect_solution, solution[i]);
            }
            return perfect_solution.get_best_seq();
        }

        /* ↓ If you need additional methods to solve DP, write them here. ↓ */
        /**
         * Stocke la meilleur solution locale dans local_best_solution
         * @modifies local_best_solution
         * @effects insère dans local_best_solution la meilleur solution locale
         * @param state l'état actuel du jeu
         * @param actions les actions déjà entreprises jusqu'à présent
         * @param local_best_solution la solution où il faut stocker la meilleur solution locale
         * 
         @author Samuel Gucciardi (specification)
         @author Samuel Gucciardi (implementation)
         @author (tests/debug)
         @version 1.0 (04/04/2026)
         */
        private static DP_Solution perfectLocalSolution(State state, DP_Solution local_best_solution){
            //Cas limite
            if(is_game_over(state)){
              return local_best_solution;
            }
            //Cas récursif
            else{
                //On copie les éléments qui vont être modifiés
                DP_Solution sol1 = new DP_Solution(local_best_solution);
                State state_copy = state.clone();
                //Si l'action précédente allait vers le haut ou vers le bas
                if(state.prevAction == Action.BURST_NORTH || state.prevAction == Action.BURST_SOUTH){
                    // -- Première solution possible (BURST_EAST) --
                    //On modifie ces copies
                    sol1 = next_solution(state_copy, new DP_Solution(sol1), 'e');

                    // -- Deuxième solution possible (BURST_WEST) --
                    local_best_solution = next_solution(state, new DP_Solution(local_best_solution), 'w');
                }
                //Si l'action précédente allait vers la gauche ou vers la droite
                else{
                    // -- Première solution possible (BURST_NORTH) --
                    //On modifie ces copies
                    sol1 = next_solution(state_copy, new DP_Solution(sol1), 'n');

                    // -- Deuxième solution possible (BURST_SOUTH) --
                    local_best_solution = next_solution(state, new DP_Solution(local_best_solution), 's');
                }
                //On injecte dans local_best_solution la meilleur des deux solutions
                local_best_solution = DP_Solution.best_solution(sol1, local_best_solution);

                return local_best_solution;
            }
        }

        /**
         * Retourne la solution optimale si on part dans cette direction
         * 
         * @modifies solution
         * @effects insère dans solution la solution optimale que l'on pourra avoir si on part dans cette direction
         * @param state l'état actuel du jeu
         * @param actions les actions entreprises jusqu'à présent
         * @param solution la solution qui va être modifié
         * @param direction la direction que l'on va prendre pour la prochaine action
         * 
         @author Samuel Gucciardi (specification)
         @author Samuel Gucciardi (implementation)
         @author (tests/debug)
         @version 1.1 (27/04/2026)
         */
        private static DP_Solution next_solution(State state, DP_Solution solution, char direction){
            String str_direction = Character.toString(direction); //On converti le char en string

            solution.set_pb(solution.get_pb() + Algorithms.pathScore(state, str_direction));
            solution.set_best_seq(solution.get_best_seq() + direction);
            Algorithms.applyAction(state, Algorithms.charToAction(direction));
            
            solution = perfectLocalSolution(state, new DP_Solution(solution));

            return solution;
        }

    /**
     * Indique si le niveau est terminé ou non
     * @param state : l'état de la partie
     * @return : True si la configuration de state indique que le niveau est terminé, false sinon
     * 
     * @author Samuel Gucciardi (specification)
     * @author Samuel Gucciardi (implementation)
     * @author (tests/debug)
     * @version 2.0 (17/04/2026)
     */
    static boolean is_game_over(State state){
        //Si le robot n'a plus de batterie
        if(state.gundamElec <= 0){
            return true;
        }
        //Si le robot est en dehors des limites du niveau
        else if(state.gundamPos[0] < 0 || state.gundamPos[1] < 0 || state.gundamPos[0] >= state.invaders.length || state.gundamPos[1] >= state.invaders.length){
            return true;
        }
        else if(state.invaders.length == 0){
            return true;
        }
        else{
            return false;
        }
    }
}