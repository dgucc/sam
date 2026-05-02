package unamur;


/*Generate & Test*/
public class GT implements Algorithms.GT{

    /**
     * Vérifie si le tableau d'invaders respecte toutes les contraintes du jeu. Cette méthode est
     * utilisée pour valider une configuration générée.
     */
    static boolean testInvaders(int[][] invaders) {
        // Vérification basique : le tableau doit exister et être carré
        if (invaders == null || invaders.length == 0 || invaders.length != invaders[0].length) {
            return false;
        }

        int n = invaders.length; // taille du plateau (ex: 10x10)
        int center = n / 2; // position centrale (ex: 5 pour n=10)

        // Règle 1 : La cellule centrale (où commence le joueur) doit être vide
        if (invaders[center][center] != 0) {
            return false;
        }

        // Calcul du nombre maximum d'invaders autorisé par ligne ou colonne
        // Formule : 1 + ceil(n / 3)
        int maxPerRowCol = 1 + (n + 2) / 3;

        int[] rowCount = new int[n]; // nombre d'invaders par ligne
        int[] colCount = new int[n]; // nombre d'invaders par colonne
        int totalHP = 0; // somme totale des points de vie

        // On parcourt tout le tableau
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int hp = invaders[i][j];

                // Règle 2 : Chaque invader doit avoir entre 1 et 99 HP
                if (hp < 0 || hp > 99) {
                    return false;
                }

                if (hp > 0) { // il y a un invader ici
                    totalHP += hp; // on ajoute ses HP au total
                    rowCount[i]++; // on compte +1 pour cette ligne
                    colCount[j]++; // on compte +1 pour cette colonne

                    // Double vérification : interdiction sur la cellule centrale
                    if (i == center && j == center) {
                        return false;
                    }
                }
            }
        }

        // Règle 3 : Pas trop d'invaders par ligne ou par colonne
        // On vérifie aussi qu'il y a AU MOINS 1 alien par ligne et par colonne
        for (int i = 0; i < n; i++) {
            if (rowCount[i] < 1 || rowCount[i] > maxPerRowCol) {
                return false;
            }
            if (colCount[i] < 1 || colCount[i] > maxPerRowCol) {
                return false;
            }
        }

        // Règle 4 : La force totale (somme des HP) doit être dans l'intervalle demandé
        int minTotal = 5 * n * n; // minimum = 5 * largeur²
        int maxTotal = 10 * n * n; // maximum = 10 * largeur²

        return totalHP >= minTotal && totalHP <= maxTotal;
    }

    /**
     * Génère une configuration valide d'invaders dans le tableau. On utilise Algorithms.rng (avec
     * seed 42) pour que les tests soient reproductibles. On boucle jusqu'à obtenir une
     * configuration qui passe le test testInvaders().
     */
    public static void generateInvaders(int[][] invaders) {
        if (invaders == null || invaders.length != invaders[0].length) {
            return;
        }

        int n = invaders.length;
        int center = n / 2;

        // On répète la génération tant que la configuration n'est pas valide
        while (true) {
            // Étape 1 : On vide complètement le tableau
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    invaders[i][j] = 0;
                }
            }

            // Étape 2 : On place des invaders avec ~25% de probabilité par cellule
            // (sauf sur la cellule centrale)
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == center && j == center) {
                        continue; // on saute la cellule centrale
                    }

                    // 25% de chance de placer un invader
                    if (Algorithms.rng.nextDouble() < 0.25) {
                        // HP aléatoire entre 1 et 99
                        invaders[i][j] = Algorithms.rng.nextInt(99) + 1;
                    }
                }
            }

            // Étape 3 : On vérifie si cette configuration est valide
            if (testInvaders(invaders)) {
                break; // Si la configuration valide alors on sort de la boucle
            }
            // Sinon on recommence la génération
        }
    }

    /**
     * Génère entre 1 et 4 bâtiments à des positions valides. Un bâtiment ne peut pas être placé :
     * - sur la cellule centrale
     * - sur une case où il y a déjà un invader
     */
    public static void generateBuildings(int[][] buildings, int[][] invaders) {
        if (buildings == null || invaders == null || buildings.length != invaders.length) {
            return;
        }

        int n = buildings.length;
        int center = n / 2;

        // On commence par vider le tableau des bâtiments
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                buildings[i][j] = 0;
            }
        }

        // On choisit aléatoirement le nombre de bâtiments (entre 1 et 4)
        int numBuildings = Algorithms.rng.nextInt(4) + 1;

        int placed = 0; // nombre de bâtiments déjà placés
        int attempts = 0; // compteur pour éviter une boucle infinie
        final int MAX_ATTEMPTS = 10000; // limite de sécurité

        // On essaie de placer les bâtiments
        while (placed < numBuildings && attempts < MAX_ATTEMPTS) {
            attempts++;

            // On choisit une position aléatoire
            int i = Algorithms.rng.nextInt(n);
            int j = Algorithms.rng.nextInt(n);

            // On vérifie que la position est autorisée
            if (i == center && j == center)
                continue; // pas au centre
            if (invaders[i][j] != 0)
                continue; // pas sur un invader
            if (buildings[i][j] != 0)
                continue; // déjà un bâtiment ici

            // Placement du bâtiment (on met 1 pour indiquer sa présence)
            buildings[i][j] = 1;
            placed++;
        }

        // Sécurité : si on n'a rien pu placer
        if (placed == 0) {
            // On force la pose d'au moins un bâtiment n'importe où (sauf centre + invaders)
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if ((i != center || j != center) && invaders[i][j] == 0) {
                        buildings[i][j] = 1;
                        return;
                    }
                }
            }
        }
    }
}
