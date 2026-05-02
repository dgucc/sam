package unamur;


import java.util.Scanner;

/**
 * @overview : la classe qui va permettre d'afficher le jeu sur le terminal
 */
public class Game {
    public static void print(Object o){
        System.out.println(o);
    }
    
    public static void main(String[] args){
        //=======================================================
        //Phase d'initialisation
        //=======================================================
        int size = 5;
        int[] gundamPos = {size/2, size/2};
        int[][] invaders = new int[size][size];
        int[][] buildings = new int[size][size];
        int score_goal;

        boolean using_zqsd = false;
        System.out.println("Voulez-vous jouer en mode de commande ZQSD ? (oui/non)");
        // String answer = read_input();
        String input;

        Scanner scanIn = new Scanner(System.in);
        input = scanIn.nextLine();

        if(input.equals("oui")){
            using_zqsd = true;
        }

        State game = new State(gundamPos, 10, buildings, invaders, null, 0, 1, 0, 0, 0);

        //=======================================================
        //Boucle de jeu
        //=======================================================
        while(game.gundamElec > 0){
            //=======================================================
            //Phase entre les niveaux
            //=======================================================
            game.levelScore = 0;
            game = create_level(game);

            print_game_info(game);

            //Si le joueur veut utiliser un indice
            if(game.nbClue > 0){
                print("  === utiliser un indice (restant : " + game.nbClue + ")? (oui/non) ===");
                input = scanIn.nextLine();
                if(input.equals("oui")){
                    String hint = DP.perfectSolution(game);
                    game.nbClue -= 1;
                    //On affiche les 3 premières actions de la solution parfaite
                    for(int i = 0 ; i < 3 && i < hint.length() ; i++){
                        print("voici l'indice : " + hint.charAt(i));
                    }
                }
            }
            //Pour le score objectif
            score_goal = Algorithms.pathScore(game, GS.greedySolution(game));

            print("#################################################################################################");
            //=======================================================
            //Phase de niveau
            //=======================================================
            while(!DP.is_game_over(game)){
                print_level_info(game, score_goal);
                print("Tapez la prochaine direction :");
                input = scanIn.nextLine();

                //Si le joueur joue en ZQSD
                if(using_zqsd){
                    input = Algorithms.ZQSDtoNEWS(input);
                }
                //Si on fait une action
                Algorithms.applyAction(game, Algorithms.charToAction(input.charAt(0)));
            }
            print("************************************************************************************************************************");
            print("FIN DU NIVEAU " + game.nbLevel);
            print("************************************************************************************************************************");
            //Si le joueur a fait pire que le score à battre, on arrête la partie
            if(game.levelScore < score_goal){
                print("Le score objectif n'a pas été vaincu : FIN DU JEU");
                break;
            }
            //On recharge les batteries
            game.gundamElec += game.levelScore/30;

            //On donne éventuellement un indice supplémentaire
            if(no_building_destoyed(game)){game.nbClue += 1;}
        }

        scanIn.close();
    }




    /**
     * Permet d'afficher dans le terminal l'état du jeu
     * @requires state != null
     * @modify System.out
     * @effects affiche dans le terminal l'état du jeu
     * @param state l'état actuel du jeu
     */
    public static void print_state(State state){
        for(int y = state.invaders.length - 1 ; y >= 0 ; y--){
            //On désinne une ligne
            for(int i = 0 ; i < state.invaders.length ; i++){System.out.print("======");}
            System.out.println("=");

            //On affiche le symbole de ce qui se trouve sur la case
            for(int x = 0 ; x < state.invaders.length ; x++){
                //Si c'est un alien
                if(state.invaders[x][y] > 0){
                    System.out.print("|  I  ");
                    continue;
                }
                //Si c'est un building propre
                if(state.buildings[x][y] == 1){
                    System.out.print("|  B  ");
                    continue;
                }
                //Si c'est un building cassé
                if(state.buildings[x][y] == -1){
                    System.out.print("|  BC ");
                    continue;
                }
                //Si c'est le robot
                if(state.gundamPos[0] == x && state.gundamPos[1] == y){
                    System.out.print("|  🤖 ");
                    continue;
                }
                //Si il n'y a rien
                System.out.print("|     ");
            }
            System.out.println("|");

            //On affiche la valeur de ce qui se trouve sur la case
            for(int x = 0 ; x < state.invaders.length ; x++){
                //Si c'est un alien
                if(state.invaders[x][y] > 0){
                    if(state.invaders[x][y] < 10){System.out.print("|  " + state.invaders[x][y] + "  ");}
                    else{System.out.print("|  " + state.invaders[x][y] + " ");}
                    continue;
                }
                //Si c'est le robot
                if(state.gundamPos[0] == x && state.gundamPos[1] == y){
                    if(state.gundamElec >= 10){System.out.print("|  " + state.gundamElec + " ");}
                    else{System.out.print("|  " + state.gundamElec + "  ");}
                    continue;
                }
                //Si il n'y a rien
                System.out.print("|     ");
            }
            System.out.println("|");
        }
    }

    /**
     * Créé un niveau de dimensions size x size
     * @modifies game
     * @effects modifie gundamPos, invaders et buildings pour créer un nouveau niveau
     * @param game l'état du jeu que l'on veut modifier
     * @param size la dimension de la ville
     */
    public static State create_level(State game){
        int size = (game.nbLevel/3)*2 + 5; //Les dimensions du niveaux augmentent de 2 tous les trois niveaux

        int[] gundamPos = {size/2, size/2};
        int[][] invaders = new int[size][size];
        int[][] buildings = new int[size][size];

        GT.generateInvaders(invaders);
        DC.sortCity(invaders);
        GT.generateBuildings(buildings, invaders);

        print_state(new State(gundamPos, game.gundamElec, buildings, invaders, null, 0, game.nbClue, game.nbLevel, game.gameScore, game.levelScore));

        return new State(gundamPos, game.gundamElec, buildings, invaders, null, 0, game.nbClue, game.nbLevel, game.gameScore, game.levelScore);
    }

    //Affiche les informations pendant le niveau
    public static void print_level_info(State game, int score_goal){
        print("Score à vaincre : " + score_goal);
        print("Score actuel : " + game.levelScore);

        print_state(game);
    }

    //Affiche les informations entre les niveaux
    public static void print_game_info(State game){
        print("#################################################################################################");
        print("Niveau actuel : " + game.nbLevel);
        print("Score total : " + game.gameScore);
        print("#################################################################################################");
    }

    //Pour donner un nouvel indice au joueur
    public static boolean no_building_destoyed(State game){
        for(int i = 0 ; i < game.buildings.length ; i++){
            for(int j = 0 ; j < game.buildings.length ; j++){
                if(game.buildings[i][j] == -1){return false;}
            }
        }
        return true;
    }
}
