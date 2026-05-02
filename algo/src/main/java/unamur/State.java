package unamur;

import java.util.Arrays;

/*
* State object : contains everything related to a game's current state
* */
public class State {
    public int[] gundamPos;  // Current position of the gundam in the city. (0, 0) is the bottom left cell of the city.
    public int gundamElec;  // Current battery level of the gundam, corresponding to one burst move in the city

    // Board filled with 0s when there is no building, 1 when there is a building, -1 when there is a damaged building
    // buildings[0][0] is bottom left (SW) of the city
    // buildings[buildings.length-1][buildings[0].length-1] is top right (NE) of the city (math convention)
    public int[][] buildings;

    // Board filled with 0s when there is no invader, and a number between 1-99 when there is an invader, depending on
    // the invader's strength
    // Same ordering as buildings
    public int[][] invaders;

    public Action prevAction;  // Last action from the player, as this limits possibilities for the next action.
    public int nbBurst;  // Number of burst moves made in the current level

    int nbClue;  // Number of clues available to the player
    public int nbLevel;  // Current level number, starting at 1 and going up
    public int gameScore;  // Total score
    public int levelScore;  // Score for the current level

    public State(int[] gundamPos, int gundamElec, int[][] buildings, int[][] invaders, Action prevAction, int nbBurst, int nbClue, int nbLevel, int previousScore, int levelScore) {
        this.gundamPos = gundamPos;
        this.gundamElec = gundamElec;
        this.buildings = buildings;
        this.invaders = invaders;
        this.prevAction = prevAction;
        this.nbBurst = nbBurst;
        this.nbClue = nbClue;
        this.nbLevel = nbLevel;
        this.gameScore = previousScore;
        this.levelScore = levelScore;
    }

    public State clone() {
        // Return a deep copy of the state
        int[][] buildingsCopy = new int[buildings.length][];
        int[][] invadersCopy = new int[invaders.length][];
        for (int i = 0; i < buildings.length; i++) {
            buildingsCopy[i] = Arrays.copyOf(buildings[i], buildings[i].length);
            invadersCopy[i] = Arrays.copyOf(invaders[i], invaders[i].length);
        }
        return new State(Arrays.copyOf(gundamPos, gundamPos.length), gundamElec, buildingsCopy, invadersCopy, prevAction, nbBurst, nbClue, nbLevel, gameScore, levelScore);
    }

    // Hash method
    @Override
    public int hashCode() {
        return 13 * nbBurst + 29 * levelScore + 113 * invaders.length + 229 * (prevAction == null ? 17 : prevAction.ordinal()) + 349 * gundamPos[0] + 463 * gundamPos[1] + 601 * gundamElec;
    }
}
