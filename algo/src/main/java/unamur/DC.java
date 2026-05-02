package unamur;

/* --- Divide & Conquer --- */
public class DC implements Algorithms.DC{

    /**
     * réorganise en place la grille d'aliens en 4 quadrants selon leur force totale :
     * NW reçoit le plus fort, SE le deuxième, NE le troisième et SW le plus faible.
     * La même logique est appliquée récursivement dans chaque quadrant.
     *
     * @requires grille != null
     * @requires grille.length == grille[0].length
     * @requires grille.length % 2 == 1
     * @ensures la somme totale des valeurs de la grille ne change pas
     * @ensures \result >= 0
     *
     * @param grille la grille d'aliens à réorganiser
     * @return la somme totale des forces des aliens
     *
     * @author Mohamed (spécification)
     * @author Mohamed (implémentation)
     * @version 1.2
     */
    public static int sortCity(int[][] grille) {
        if (grille == null) throw new IllegalArgumentException();

        int total = sommeTotale(grille);
        trierQuadrants(grille, 0, grille.length - 1, 0, grille[0].length - 1);
        return total;
    }

    /**
     * Trie récursivement une zone de la grille en 4 quadrants,
     * en les réorganisant selon leur force totale.
     *
     * @requires grille != null
     * @requires ligneDebut >= 0 && ligneFin < grille.length
     * @requires colDebut >= 0 && colFin < grille[0].length
     * @requires ligneDebut <= ligneFin && colDebut <= colFin
     *
     * @param grille la grille complète
     * @param ligneDebut première ligne de la zone
     * @param ligneFin dernière ligne de la zone
     * @param colDebut première colonne de la zone
     * @param colFin dernière colonne de la zone
     *
     * @author Mohamed (spécification)
     * @author Mohamed (implémentation)
     * @version 1.2
     */
    static void trierQuadrants(int[][] grille,
                               int ligneDebut, int ligneFin,
                               int colDebut, int colFin) {

        int nbLignes = ligneFin - ligneDebut + 1;
        int nbColonnes = colFin - colDebut + 1;

		if (nbLignes <= 1 || nbColonnes <= 1) return;  // rien à découper

		// Détermination du centre de la zone pour la découper en 4 quadrants
        int ligneMilieu = ligneDebut + (ligneFin - ligneDebut) / 2;       
        int colMilieu = colDebut + (colFin - colDebut) / 2;

        int finHaut = ligneMilieu;
        if (nbLignes % 2 != 0) finHaut--;

        int debutBas = ligneMilieu + 1;

        int finGauche = colMilieu;
        if (nbColonnes % 2 != 0) finGauche--;

        int debutDroite = colMilieu + 1;

		// vérifie que la zone est valide, si hors limite ou vide on arrête la récursion
        if (finHaut < ligneDebut || finGauche < colDebut ||
            debutBas > ligneFin || debutDroite > colFin) return; 


		// Création des 4 quadrants avec leurs bornes et leur force totale
		Quadrant nw = new Quadrant(
                ligneDebut, finHaut, colDebut, finGauche,
                sommeZone(grille, ligneDebut, finHaut, colDebut, finGauche)
        );

        Quadrant se = new Quadrant(
                debutBas, ligneFin, debutDroite, colFin,
                sommeZone(grille, debutBas, ligneFin, debutDroite, colFin)
        );

        Quadrant ne = new Quadrant(
                ligneDebut, finHaut, debutDroite, colFin,
                sommeZone(grille, ligneDebut, finHaut, debutDroite, colFin)
        );

        Quadrant sw = new Quadrant(
                debutBas, ligneFin, colDebut, finGauche,
                sommeZone(grille, debutBas, ligneFin, colDebut, finGauche)
        );

        Quadrant[] quadrants = {nw, se, ne, sw};

        trierParSelection(quadrants);

		// Copie de la grille pour éviter d’écraser les valeurs lors du déplacement
        int[][] copie = copierGrille(grille);

        copierBloc(copie, grille, quadrants[0], ligneDebut, colDebut);      // NW
        copierBloc(copie, grille, quadrants[1], debutBas, debutDroite);     // SE
        copierBloc(copie, grille, quadrants[2], ligneDebut, debutDroite);   // NE
        copierBloc(copie, grille, quadrants[3], debutBas, colDebut);        // SW

        trierQuadrants(grille, ligneDebut, finHaut, colDebut, finGauche);
        trierQuadrants(grille, debutBas, ligneFin, debutDroite, colFin);
        trierQuadrants(grille, ligneDebut, finHaut, debutDroite, colFin);
        trierQuadrants(grille, debutBas, ligneFin, colDebut, finGauche);
    }

    /**
     * calcule la somme des valeurs dans une sous-zone rectangulaire.
     *
     * @param grille la grille
     * @param ligneDebut début ligne
     * @param ligneFin fin ligne
     * @param colDebut début colonne
     * @param colFin fin colonne
     * @return somme des valeurs
     */
    static int sommeZone(int[][] grille,
                         int ligneDebut, int ligneFin,
                         int colDebut, int colFin) {

        int total = 0;
        for (int i = ligneDebut; i <= ligneFin; i++) {
            for (int j = colDebut; j <= colFin; j++) {
                total += grille[i][j];
            }
        }
        return total;
    }

    /**
     * trie les quadrants du plus grand au plus petit
     * selon leur force totale avec un tri par sélection.
     *
     * @param quadrants tableau de quadrants
     */
    static void trierParSelection(Quadrant[] quadrants) {
        for (int i = 0; i < quadrants.length - 1; i++) {
            int max = i;

            for (int j = i + 1; j < quadrants.length; j++) {
                if (quadrants[j].forceTotale > quadrants[max].forceTotale) {
                    max = j;
                }
            }

            Quadrant temp = quadrants[i];
            quadrants[i] = quadrants[max];
            quadrants[max] = temp;
        }
    }

    /**
     * Copie toute la grille dans un nouveau tableau.
     *
     * @param grille la grille à copier
     * @return une copie de la grille
     */
    static int[][] copierGrille(int[][] grille) {
        int[][] copie = new int[grille.length][grille[0].length];

        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                copie[i][j] = grille[i][j];
            }
        }

        return copie;
    }

    /**
     * Copie un quadrant depuis une grille source vers une destination donnée.
     *
     * @param source grille source
     * @param destination grille destination
     * @param q quadrant à copier
     * @param ligneDest ligne de destination
     * @param colDest colonne de destination
     */
    static void copierBloc(int[][] source, int[][] destination,
                           Quadrant q,
                           int ligneDest, int colDest) {

        for (int i = 0; i <= q.ligneFin - q.ligneDebut; i++) {
            for (int j = 0; j <= q.colFin - q.colDebut; j++) {
                destination[ligneDest + i][colDest + j] =
                        source[q.ligneDebut + i][q.colDebut + j];
            }
        }
    }

    /**
     * Calcule la somme totale de la grille.
     *
     * @param grille la grille
     * @return somme totale
     */
    static int sommeTotale(int[][] grille) {
        return sommeZone(grille, 0, grille.length - 1, 0, grille[0].length - 1);
    }

    /**
     * Représente un quadrant avec sa force totale et ses bornes.
     */
    static class Quadrant {
        int ligneDebut, ligneFin;
        int colDebut, colFin;
        int forceTotale;

        Quadrant(int ligneDebut, int ligneFin,
                 int colDebut, int colFin,
                 int forceTotale) {

            this.ligneDebut = ligneDebut;
            this.ligneFin = ligneFin;
            this.colDebut = colDebut;
            this.colFin = colFin;
            this.forceTotale = forceTotale;
        }
    }
}
