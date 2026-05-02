package unamur;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestAlgorithmsPublic {

    /* --- Generate & Test --- */

    @Test
    void GT_generateInvadersModifiesInvaders() {
        // init
        int[][] invaders = new int[5][5];

        // call method(s)
        GT.generateInvaders(invaders);

        // assert: at least one cell must have been set to a non-zero value
        boolean modified = false;
        for (int[] row : invaders)
            for (int cell : row)
                if (cell != 0) { modified = true; break; }

        assertTrue(modified, "generateInvaders should place at least one invader.");
}

    @Test
    void GT_generateBuildingsModifiesBuildings() {
        // init
        int[][] invaders = new int[5][5];
        int[][] buildings = new int[5][5];

        // call method(s)
        GT.generateInvaders(invaders);
        GT.generateBuildings(buildings, invaders);

        // assert: at least one building must have been placed
        boolean modified = false;
        for (int[] row : buildings)
            for (int cell : row)
                if (cell != 0) { modified = true; break; }

        assertTrue(modified, "generateBuildings should place at least one building.");
    }

    @Test
    void GT_generateBuildingsNoOverlapWithInvaders() {
        // init: full pipeline — generate invaders, sort city, then place buildings
        int[][] invaders  = new int[9][9];
        int[][] buildings = new int[9][9];

        // call method(s)
        GT.generateInvaders(invaders);
        DC.sortCity(invaders);
        GT.generateBuildings(buildings, invaders);

        // assert: no cell contains both a building and an invader
        int center = invaders.length / 2;
        for (int col = 0; col < buildings.length; col++) {
            for (int row = 0; row < buildings[col].length; row++) {
                boolean hasBuilding = buildings[col][row] != 0;
                boolean hasInvader  = invaders[col][row]  >  0;
                assertFalse(hasBuilding && hasInvader,
                    "Cell (" + col + "," + row + ") has both a building and an invader.");
                assertFalse(hasBuilding && col == center && row == center,
                    "A building was placed on the center cell (gundam spawn).");
            }
        }
    }

    @Test
    void GT_generateInvadersCreatesValidInvaders() {
        // init
        int[][] invaders = new int[5][5];

        // call method(s)
        GT.generateInvaders(invaders);
        boolean res = GT.testInvaders(invaders);

        // assert
        assertTrue(res, "generateInvaders should produce a grid that passes testInvaders.");
    }

    @Test
    void GT_validInvaders() {
        // init
        int[][] invaders = {
            {20,  0, 20,  0,  0},
            { 0, 20,  0,  0, 20},
            {20,  0,  0, 20,  0},
            { 0,  0, 20,  0, 20},
            { 0, 20,  0, 20,  0},
        };

        // call method(s)
        boolean res = GT.testInvaders(invaders);

        // assert
        assertTrue(res, "Valid 5x5 invaders grid should be accepted.");
    }

    @Test
    void GT_validInvaders_variousCityDimensions() {
        // init
        int[][] smallCity = {
            {28,  0,  0},
            { 0,  0, 30},
            { 0, 28,  0},
        };
        int[][] largeCity = {
            {20,  0, 20,  0,  0},
            { 0, 20,  0,  0, 20},
            {20,  0,  0, 20,  0},
            { 0,  0, 20,  0, 20},
            { 0, 20,  0, 20,  0},
        };

        // call method(s)
        boolean res1 = GT.testInvaders(smallCity);
        boolean res2 = GT.testInvaders(largeCity);

        // assert
        assertTrue(res1, "Valid 3x3 invaders grid should be accepted.");
        assertTrue(res2, "Valid 5x5 invaders grid should be accepted.");
    }

    @Test
    void GT_invalidInvaders_valueOutOfRange() {
        // init
        int[][] invaders = {
            {50,  0, 50,  0,   0},
            { 0, 50,  0,  0,  50},
            {50,  0,  0, 50,   0},
            { 0,  0, 50,  0, 100},  // 100 is invalid
            { 0, 50,  0, 50,   0},
        };

        // call method(s)
        boolean res = GT.testInvaders(invaders);

        // assert
        assertFalse(res, "Invader with HP=100 should make the grid invalid.");
    }

    @Test
    void GT_invalidInvaders_overpopulatedRow() {
        // init: 5x5 grid where row 0 contains 3 invaders (> ceil(5/3)=2)
        int[][] invaders = {
            {10, 10, 10,  0,  0},  // 3 invaders → invalid (max is 2)
            { 0, 10,  0,  0, 10},
            {10,  0,  0, 10,  0},
            { 0,  0, 10,  0, 10},
            { 0, 10,  0, 10,  0},
        };

        // call method(s)
        boolean res = GT.testInvaders(invaders);

        // assert
        assertFalse(res, "Row with 3 invaders should make the grid invalid (max is ceil(5/3)=2).");
    }

    @Test
    void GT_invalidInvaders_overpopulatedCol() {
        // init: 5x5 grid — rows all have ≤ ceil(5/3)=2 invaders, but col 0 has 3 (rows 0,1,3)
        int[][] invaders = {
            {10,  0, 10,  0,  0},  // col 0: rows 0,1,3 = 3 invaders → invalid (max is 2)
            {10, 10,  0,  0,  0},
            { 0,  0,  0, 10, 10},
            {10,  0,  0, 10,  0},
            { 0, 10,  0,  0, 10},
        };

        // call method(s)
        boolean res = GT.testInvaders(invaders);

        // assert
        assertFalse(res, "Column with 3 invaders should make the grid invalid (max is ceil(5/3)=2).");
    }

    @Test
    void GT_invalidInvaders_underpopulatedCity() {
        // init
        // total HP=21 < 45 (= 5 * 3²)
        int[][] invaders = {
            { 1,  0,  0},
            { 0,  0, 10},
            { 0, 10,  0},
        };

        // call method(s)
        boolean res = GT.testInvaders(invaders);

        // assert
        assertFalse(res, "City whose total HP is below the minimum threshold should be invalid.");
    }

    @Test
    void GT_invalidInvaders_overpopulatedCity() {
        // init
        // total HP=150 > 90 (= 10 * 3²)
        int[][] invaders = {
                { 0,  50,  0},
                { 0,   0, 50},
                { 50,  0,  0},
        };

        // call method(s)
        boolean res = GT.testInvaders(invaders);

        // assert
        assertFalse(res, "City whose total HP is over the maximum threshold should be invalid.");
    }

    /* --- Divide & Conquer --- */


    @Test
    void DC_minimalCity() {
        // init: 3x3 city generated by GT.generateInvaders (seed-independent run)
        // Corners: TL=14, TR=0, BL=0, BR=0 → already in sorted order (TL is biggest)
        int[][] invaders = {
            { 3,  0,  1},
            { 0,  0,  0},
            { 4,  0,  2},
        };
        int[][] expected = {
            { 1,  0,  4},
            { 0,  0,  0},
            { 3,  0,  2},
        };

        // call method(s)
        DC.sortCity(invaders);

        // assert
        for (int i = 0; i < expected.length; i++)
            assertArrayEquals(expected[i], invaders[i], "Row " + i + " mismatch after sortCity on 3x3 grid.");
    }

    @Test
    void DC_normalCity() {
        // init: 9x9 city generated by GT.generateInvaders
        int[][] invaders = {
            { 33,   0,  90,   0,   0,   0,   0,   0,  84},
            { 80,   0,   0,  20,   0,   0,   0,   0,   0},
            {  0,   0,   0,   0,  67,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   4,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,  71},
            {  0,  92,   0,   0,   0,  96,  15,   0,   0},
            {  0,   0,   0,   0,  72,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,  85,   0},
            {  0,   0,   0,   0,  61,   0,  67,   0,   0},
        };
        int[][] expected = {
            {  0,   0,   0,  84,   0,   0,   0,   0,  96},
            {  0,   0,   0,   0,   0,   0,   0,  15,   0},
            {  0,   4,   0,   0,  67,   0,  85,   0,  67},
            {  0,   0,   0,   0,   0,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,  71},
            {  0,   0,   0,  80,   0,   0,   0,   0,  92},
            {  0,   0,  33,   0,  72,   0,   0,   0,   0},
            {  0,  90,   0,   0,   0,   0,   0,   0,   0},
            { 20,   0,   0,   0,  61,   0,   0,   0,   0},
        };

        // call method(s)
        DC.sortCity(invaders);

        // assert
        for (int i = 0; i < expected.length; i++)
            assertArrayEquals(expected[i], invaders[i], "Row " + i + " mismatch after sortCity on 9x9 grid.");
    }

    @Test
    void DC_biggerCity() {
        // init: 15x15 city generated by GT.generateInvaders
        int[][] invaders = {
            {  0,   0,   0,   0,   0,   0,   0,   0,  81,   0,   0,   9,  15,   0,  45},
            {  0,   0,  15,   0,   0,   0,  84,   0,   0,   0,   0,   0,   0,  25,   0},
            {  0,   0,   0,   0,   0,   0,  84,   0,   0,   0,  31,   0,  63,  39,   0},
            {  0,   0,  52,   0,  81,   0,   0,   0,   0,  69,   0,   0,   0,   0,   2},
            {  0,   0,   0,   0,   0,  20,   0,  21,   0,  91,   0,   0,   0,   0,   0},
            { 45,   0,   0,  34,   0,   0,   0,   0,   0,  13,  30,   0,   0,   0,   0},
            {  0,   0,   0,  91,   8,   0,   0,   0,   0,  67,   0,   0,   0,   0,  71},
            { 36,   0,   0,   0,  25,   0,   0,   0,   0,   0,   0,   0,  55,  42,   0},
            { 21,   0,   0,   0,  88,   0,   0,   0,   0,   0,   0,   0,   0,   0,  58},
            { 14,   0,   0,   0,   0,  74,   0,   0,  89,   0,   0,   0,  98,   0,   0},
            {  0,   0,  41,   0,   0,   0,  86,   0,  26,  85,   0,   0,   3,   0,   0},
            { 34,  93,   0,   0,  42,  39,  74,   0,   0,   0,   0,   0,   0,   0,   0},
            {  0,   0,   0,  16,   0,   0,   0,   0,   0,   0,   0,  30,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  55,   0,   0,  14},
            {  0,   0,  49,  31,   0,  12,   0,   0,   0,   0,   0,   0,   0,   0,   0},
        };
        int[][] expected = {
            {  0,   0,   0,   0,   0,   0,  58,   0,   0,  91,   0,   9,   0,   0,  63},
            {  0,   0,  14,   0,  98,   0,   0,   0,   0,  13,  30,   0,   0,  25,   0},
            {  0,   0,   0,   0,   3,   0,   0,   0,   0,  67,   0,   0,  45,  39,  15},
            {  0,   0,   0,   0,   0,   0,   0,   0,   0,  69,   0,   0,   0,   0,   2},
            {  0,   0,  26,  30,   0,   0,   0,  21,   0,   0,  81,   0,   0,   0,  71},
            { 89,   0,   0,  55,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
            {  0,  85,   0,   0,   0,   0,   0,   0,  31,   0,   0,   0,   0,   0,   0},
            { 36,   0,   0,   0,  25,   0,   0,   0,   0,   0,   0,   0,  55,  42,   0},
            {  0,   0,   0,   0,   0,   0,  88,   0,   0,   0,   0,   0,   0,   0,  84},
            {  0,   0,   0,   0,   0,  74,   0,   0,  45,   0,   0,   0,   0,   0,  84},
            {  0,  12,   0,   0,  86,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
            { 34,  93,   0,   0,  42,  39,  74,   0,   0,   0,  52,   0,  81,   0,   0},
            {  0,   0,  41,  16,   0,   0,  49,   0,   0,  20,   8,   0,   0,   0,   0},
            { 14,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  34,   0,   0,  15},
            { 21,   0,   0,  31,   0,   0,   0,   0,   0,   0,   0,  91,   0,   0,   0},
        };

        // call method(s)
        DC.sortCity(invaders);

        // assert
        for (int i = 0; i < expected.length; i++)
            assertArrayEquals(expected[i], invaders[i], "Row " + i + " mismatch after sortCity on 15x15 grid.");
    }

    /* --- Greedy Search --- */

    @Test
    void GS_validMoves() {
        // init: 9×9 DC_normalCity sorted grid (DC_normalCity expected output), no buildings
        int[][] invaders = {
            { 96,   0,   0,   0,   0,  84,   0,   0,   0},
            {  0,  15,   0,   0,   0,   0,   0,   0,   0},
            { 67,   0,  85,   0,  67,   0,   0,   4,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,  71},
            { 92,   0,   0,   0,   0,  92,   0,   0,   0},
            {  0,   0,   0,   0,  72,   0,  33,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,  90,   0},
            {  0,   0,   0,   0,  61,   0,   0,   0,  20},
        };
        int[][] buildings = new int[9][9];
        State state = new State(new int[]{4, 4}, 10, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String sol = GS.greedySolution(state);

        // assert: every character is a valid direction, no turn-around, no repeated direction
        char prev = 0;
        for (char c : sol.toCharArray()) {
            assertTrue(c == 'n' || c == 'e' || c == 's' || c == 'w',
                    "Solution contains invalid action '" + c + "'.");
            assertNotEquals(c, prev,
                    "Solution contains two consecutive identical actions '" + c + "'.");
            assertFalse(c == 'n' && prev == 's' || c == 's' && prev == 'n' ||
                        c == 'e' && prev == 'w' || c == 'w' && prev == 'e',
                    "Solution contains a turn-around ('" + c + "' directly after '" + prev + "').");
            prev = c;
        }
    }

    @Test
    void GS_minimalCity() {
        // init: 3×3 city (invaders[col][row])
        //   (1,2)=70 directly north of center, (2,1)=50 directly east, (2,2)=80 in the NE corner.
        // Greedy: burst 1 picks N (score 70 > 50 east), burst 2 picks E (adds 80 → total 150).
        int[][] invaders = {
            { 0,  0,  0},   // col 0: no invaders
            { 0,  0, 70},   // col 1: (1,2)=70 — directly north of center (1,1)
            { 0, 50, 80},   // col 2: (2,1)=50 east, (2,2)=80 NE corner
        };
        int[][] buildings = new int[3][3];
        State state = new State(new int[]{1, 1}, 10, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String sol = GS.greedySolution(state);
        int score = Algorithms.pathScore(state, sol);

        // assert
        assertEquals("ne", sol, "Greedy should pick N (best immediate: 70) then E (adds 80, total 150).");
        assertEquals(150, score, "Score should be 150.");
    }

    @Test
    void GS_normalCity() {
        // init: 9×9 DC_normalCity sorted grid, gundam at center (4,4), full battery
        int[][] invaders = {
            { 96,   0,   0,   0,   0,  84,   0,   0,   0},
            {  0,  15,   0,   0,   0,   0,   0,   0,   0},
            { 67,   0,  85,   0,  67,   0,   0,   4,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,  71},
            { 92,   0,   0,   0,   0,  92,   0,   0,   0},
            {  0,   0,   0,   0,  72,   0,  33,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,  90,   0},
            {  0,   0,   0,   0,  61,   0,   0,   0,  20},
        };
        int[][] buildings = new int[9][9];
        State state = new State(new int[]{4, 4}, 10, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String sol = GS.greedySolution(state);
        int score = Algorithms.pathScore(state, sol);

        // assert
        assertEquals("neswneswn", sol, "Greedy should follow path 'neswneswn' on this 9×9 city.");
        assertEquals(810, score, "Greedy score should be 810 on this 9×9 city.");
    }

    @Test
    void GS_biggerCity() {
        // init: 15×15 DC_biggerCity sorted grid, gundam at center (7,7), full battery
        int[][] invaders = {
            { 63,   0,   0,   9,   0,  91,   0,   0,  58,   0,   0,   0,   0,   0,   0},
            {  0,  25,   0,   0,   0,  13,  30,   0,  98,   0,   0,   0,   0,   0,  14},
            { 15,  39,  45,   0,   0,  67,   0,   0,   0,   0,   3,   0,   0,   0,   0},
            {  0,  69,   0,   0,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0},
            { 71,   0,   0,   0,  81,   0,   0,  21,   0,   0,   0,  30,  26,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  55,  89,   0,   0},
            {  0,   0,   0,   0,   0,   0,  31,   0,   0,   0,   0,   0,   0,  85,   0},
            { 36,   0,   0,   0,  25,   0,   0,   0,   0,   0,   0,   0,  55,  42,   0},
            { 84,   0,   0,   0,   0,   0,   0,   0,  88,   0,   0,   0,   0,   0,   0},
            {  0,   0,  84,   0,  45,   0,   0,   0,   0,  74,   0,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  86,   0,   0,  12,   0},
            {  0,   0,  52,   0,  81,   0,   0,   0,  34,  93,   0,   0,  42,  39,  74},
            {  0,   0,   0,   0,   8,  20,   0,   0,  49,   0,   0,  16,  41,   0,   0},
            {  0,   0,  15,  34,   0,   0,   0,   0,   0,   0,   0,   0,  14,   0,   0},
            {  0,   0,   0,  91,   0,   0,   0,   0,   0,   0,   0,  31,   0,   0,  21},
        };
        int[][] buildings = new int[15][15];
        State state = new State(new int[]{7, 7}, 10, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String sol = GS.greedySolution(state);
        int score = Algorithms.pathScore(state, sol);

        // assert
        assertEquals("nenwn", sol, "Greedy should follow path 'nenwn' on this 15×15 city.");
        assertEquals(306, score, "Greedy score should be 306 on this 15×15 city.");
    }

    @Test
    void GS_tightOnElec() {
        // init: 9×9 DC_normalCity input (pre-sort), only 3 elec → can't exit the board
        int[][] invaders = {
            { 33,   0,  90,   0,   0,   0,   0,   0,  84},
            { 80,   0,   0,  20,   0,   0,   0,   0,   0},
            {  0,   0,   0,   0,  67,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   4,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,  71},
            {  0,  92,   0,   0,   0,  96,  15,   0,   0},
            {  0,   0,   0,   0,  72,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,  85,   0},
            {  0,   0,   0,   0,  61,   0,  67,   0,   0},
        };
        int[][] buildings = new int[9][9];
        // Only 3 elec: 3 bursts, max displacement = 1+3 = 4 cells from center.
        // Not enough to exit a 9×9 board (center is 4 cells from border).
        State state = new State(new int[]{4, 4}, 3, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String sol = GS.greedySolution(state);
        int score = Algorithms.pathScore(state, sol);

        // assert: level ends with GameOver (not LevelOver), but greedy still scores points
        assertEquals("nes", sol, "Greedy should choose path 'nes' to maximize score before running out of elec.");
        assertEquals(168, score, "Score should be 168 even without exiting the board.");
    }

    @Test
    void GS_prioritize() {
        // Scenario 1: N > S when both yield the same immediate score
        // prevAction=EAST → only N and S available; equal invaders at (2,1) and (2,3)
        int[][] inv1 = {
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 50, 0, 50, 0},   // col 2: (2,1)=50 south of center, (2,3)=50 north of center
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
        };
        State state1 = new State(new int[]{2, 2}, 10, new int[5][5], inv1, Action.BURST_EAST, 2, 0, 1, 0, 0);

        // call method(s)
        String sol1 = GS.greedySolution(state1);

        // assert
        assertFalse(sol1.isEmpty(), "Greedy should produce a non-empty solution.");
        assertEquals('n', sol1.charAt(0), "N should be prioritized over S when both scores are equal.");

        // Scenario 2: W > E when both yield the same immediate score
        // prevAction=NORTH → only E and W available; equal invaders at (1,2) and (3,2)
        int[][] inv2 = {
            {0, 0, 0, 0, 0},
            {0, 0, 50, 0, 0},   // col 1: (1,2)=50 — west of center (2,2)
            {0, 0, 0, 0, 0},
            {0, 0, 50, 0, 0},   // col 3: (3,2)=50 — east of center (2,2)
            {0, 0, 0, 0, 0},
        };
        State state2 = new State(new int[]{2, 2}, 10, new int[5][5], inv2, Action.BURST_NORTH, 2, 0, 1, 0, 0);

        // call method(s)
        String sol2 = GS.greedySolution(state2);

        // assert
        assertFalse(sol2.isEmpty(), "Greedy should produce a non-empty solution.");
        assertEquals('w', sol2.charAt(0), "W should be prioritized over E when both scores are equal.");
    }

    @Test
    void GS_missOptimalSol() {
        // init: 3×3 city (invaders[col][row]) — gundam at (1,1)
        //   (0,2)=90 : reachable by going N then W  (optimal path "nw", score=90)
        //   (1,0)=5  : directly south               (greedy picks S first: score=5)
        //   (2,0)=10 : reachable by going S then E  (greedy continues: score=5+10=15)
        //   (2,1)=3  : directly east                (less attractive than S)
        int[][] invaders = {
            { 0,  0, 90},   // col 0: (0,2)=90
            { 5,  0,  0},   // col 1: (1,0)=5 directly south of center
            {10,  3,  0},   // col 2: (2,0)=10, (2,1)=3
        };
        int[][] buildings = new int[3][3];
        State state = new State(new int[]{1, 1}, 10, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String greedySol = GS.greedySolution(state);
        int greedyScore = Algorithms.pathScore(state, greedySol);
        int optimalScore = Algorithms.pathScore(state, "nw");   // known optimal path

        // assert
        assertEquals("se", greedySol, "Greedy should pick S (score 5) then E (adds 10), missing the NW path.");
        assertEquals(15, greedyScore, "Greedy score should be 15 (5+10).");
        assertEquals(90, optimalScore, "Known optimal path 'nw' should score 90.");
        assertTrue(greedyScore < optimalScore, "Greedy must miss the optimal solution in this configuration.");
    }

    @Test
    void GS_onGeneratedCity() {
        // init: generate a realistic 9×9 city with GT then arrange with DC
        int[][] invaders = new int[9][9];
        GT.generateInvaders(invaders);
        DC.sortCity(invaders);
        int[][] buildings = new int[9][9];
        GT.generateBuildings(buildings, invaders);
        State state = new State(new int[]{4, 4}, 10, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String sol = GS.greedySolution(state);
        int score = Algorithms.pathScore(state, sol);

        // assert
        assertFalse(sol.isEmpty(), "Greedy should always produce a non-empty solution on a valid city.");
        assertTrue(score > 0, "Greedy solution should always achieve a positive score on a valid city.");
    }

    /* --- Dynamic Programming --- */

    @Test
    void DP_validMoves() {
        // init: 9×9 DC_normalCity sorted grid, no buildings (same as GS_validMoves)
        int[][] invaders = {
            { 96,   0,   0,   0,   0,  84,   0,   0,   0},
            {  0,  15,   0,   0,   0,   0,   0,   0,   0},
            { 67,   0,  85,   0,  67,   0,   0,   4,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,  71},
            { 92,   0,   0,   0,   0,  92,   0,   0,   0},
            {  0,   0,   0,   0,  72,   0,  33,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,  90,   0},
            {  0,   0,   0,   0,  61,   0,   0,   0,  20},
        };
        int[][] buildings = new int[9][9];
        State state = new State(new int[]{4, 4}, 10, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String sol = DP.perfectSolution(state);

        // assert: every character is a valid direction, no turn-around, no repeated direction
        char prev = 0;
        for (char c : sol.toCharArray()) {
            assertTrue(c == 'n' || c == 'e' || c == 's' || c == 'w',
                    "Solution contains invalid action '" + c + "'.");
            assertNotEquals(c, prev,
                    "Solution contains two consecutive identical actions '" + c + "'.");
            assertFalse(c == 'n' && prev == 's' || c == 's' && prev == 'n' ||
                        c == 'e' && prev == 'w' || c == 'w' && prev == 'e',
                    "Solution contains a turn-around ('" + c + "' directly after '" + prev + "').");
            prev = c;
        }
    }

    @Test
    void DP_minimalCity() {
        // init: 3×3 city (invaders[col][row]) — same as GS_minimalCity
        //   (1,2)=70 directly north of center, (2,1)=50 directly east, (2,2)=80 in the NE corner.
        // DP must find the optimal "ne" path (score 150).
        int[][] invaders = {
            { 0,  0,  0},   // col 0: no invaders
            { 0,  0, 70},   // col 1: (1,2)=70 — directly north of center (1,1)
            { 0, 50, 80},   // col 2: (2,1)=50 east, (2,2)=80 NE corner
        };
        int[][] buildings = new int[3][3];
        State state = new State(new int[]{1, 1}, 10, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String sol = DP.perfectSolution(state);
        int score = Algorithms.pathScore(state, sol);

        // assert
        assertEquals(150, score, "DP should find optimal score 150 on this 3×3 city.");
    }

    @Test
    void DP_normalCity() {
        // init: 9×9 DC_normalCity sorted grid, gundam at center (4,4), full battery
        int[][] invaders = {
            { 96,   0,   0,   0,   0,  84,   0,   0,   0},
            {  0,  15,   0,   0,   0,   0,   0,   0,   0},
            { 67,   0,  85,   0,  67,   0,   0,   4,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,  71},
            { 92,   0,   0,   0,   0,  92,   0,   0,   0},
            {  0,   0,   0,   0,  72,   0,  33,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,  90,   0},
            {  0,   0,   0,   0,  61,   0,   0,   0,  20},
        };
        int[][] buildings = new int[9][9];
        State state = new State(new int[]{4, 4}, 10, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String sol = DP.perfectSolution(state);
        int score = Algorithms.pathScore(state, sol);

        // assert: DP finds the optimal score (equal to greedy on this city)
        assertEquals(810, score, "DP should find optimal score 810 on this 9×9 city.");
    }

    @Test
    void DP_biggerCity() {
        // init: 15×15 DC_biggerCity sorted grid, gundam at center (7,7), full battery
        int[][] invaders = {
            { 63,   0,   0,   9,   0,  91,   0,   0,  58,   0,   0,   0,   0,   0,   0},
            {  0,  25,   0,   0,   0,  13,  30,   0,  98,   0,   0,   0,   0,   0,  14},
            { 15,  39,  45,   0,   0,  67,   0,   0,   0,   0,   3,   0,   0,   0,   0},
            {  0,  69,   0,   0,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0},
            { 71,   0,   0,   0,  81,   0,   0,  21,   0,   0,   0,  30,  26,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  55,  89,   0,   0},
            {  0,   0,   0,   0,   0,   0,  31,   0,   0,   0,   0,   0,   0,  85,   0},
            { 36,   0,   0,   0,  25,   0,   0,   0,   0,   0,   0,   0,  55,  42,   0},
            { 84,   0,   0,   0,   0,   0,   0,   0,  88,   0,   0,   0,   0,   0,   0},
            {  0,   0,  84,   0,  45,   0,   0,   0,   0,  74,   0,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  86,   0,   0,  12,   0},
            {  0,   0,  52,   0,  81,   0,   0,   0,  34,  93,   0,   0,  42,  39,  74},
            {  0,   0,   0,   0,   8,  20,   0,   0,  49,   0,   0,  16,  41,   0,   0},
            {  0,   0,  15,  34,   0,   0,   0,   0,   0,   0,   0,   0,  14,   0,   0},
            {  0,   0,   0,  91,   0,   0,   0,   0,   0,   0,   0,  31,   0,   0,  21},
        };
        int[][] buildings = new int[15][15];
        State state = new State(new int[]{7, 7}, 10, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String sol = DP.perfectSolution(state);
        int score = Algorithms.pathScore(state, sol);

        // assert: DP beats greedy (306) and finds the optimal score (837)
        assertEquals(837, score, "DP should find optimal score 837 on this 15×15 city.");
        assertTrue(score > 306, "DP must beat greedy (306) on this 15×15 city.");
    }

    @Test
    void DP_tightOnElec() {
        // init: 9×9 pre-sort input grid, only 3 elec — gundam cannot exit the board.
        // DP finds "ene" (score 178), beating greedy's "nes" (score 168).
        int[][] invaders = {
            { 33,   0,  90,   0,   0,   0,   0,   0,  84},
            { 80,   0,   0,  20,   0,   0,   0,   0,   0},
            {  0,   0,   0,   0,  67,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   4,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,   0,  71},
            {  0,  92,   0,   0,   0,  96,  15,   0,   0},
            {  0,   0,   0,   0,  72,   0,   0,   0,   0},
            {  0,   0,   0,   0,   0,   0,   0,  85,   0},
            {  0,   0,   0,   0,  61,   0,  67,   0,   0},
        };
        int[][] buildings = new int[9][9];
        State state = new State(new int[]{4, 4}, 3, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String sol = DP.perfectSolution(state);
        int score = Algorithms.pathScore(state, sol);

        // assert
        assertEquals(178, score, "DP should find optimal score 178 with only 3 elec.");
        assertTrue(score > 168, "DP must beat greedy (168) with only 3 elec.");
    }

    @Test
    void DP_findOptimalSol() {
        // init: 3×3 city (invaders[col][row]) — gundam at (1,1)
        //   (0,2)=90 : reachable by going N then W  (optimal path "nw", score=90)
        //   (1,0)=5  : directly south               (greedy picks S first: score=5)
        //   (2,0)=10 : reachable by going S then E  (greedy continues: score=5+10=15)
        //   (2,1)=3  : directly east
        int[][] invaders = {
            { 0,  0, 90},   // col 0: (0,2)=90
            { 5,  0,  0},   // col 1: (1,0)=5 directly south of center
            {10,  3,  0},   // col 2: (2,0)=10, (2,1)=3
        };
        int[][] buildings = new int[3][3];
        State state = new State(new int[]{1, 1}, 10, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String perfectSol = DP.perfectSolution(state);
        int perfectScore  = Algorithms.pathScore(state, perfectSol);
        int greedyScore   = Algorithms.pathScore(state, "se");   // known greedy path

        // assert: DP finds the NW solution (score 90), not the greedy SE path (score 15)
        assertEquals(90, perfectScore, "DP should find optimal score 90, not greedy's 15.");
        assertTrue(perfectScore > greedyScore, "DP must beat greedy on this city.");
    }

    @Test
    void DP_onGeneratedBigCity() {
        // init: generate a realistic 9×9 city with GT then arrange with DC
        int[][] invaders = new int[9][9];
        GT.generateInvaders(invaders);
        DC.sortCity(invaders);
        int[][] buildings = new int[9][9];
        GT.generateBuildings(buildings, invaders);
        State state = new State(new int[]{4, 4}, 10, buildings, invaders, null, 1, 0, 1, 0, 0);

        // call method(s)
        String gsSol = GS.greedySolution(state);
        int gsScore  = Algorithms.pathScore(state, gsSol);
        String dpSol = DP.perfectSolution(state);
        int dpScore  = Algorithms.pathScore(state, dpSol);

        // assert: DP must always match or beat greedy on any valid city
        assertFalse(dpSol.isEmpty(), "DP should always produce a non-empty solution on a valid city.");
        assertTrue(dpScore >= gsScore,
                "DP must always match or beat greedy (dpScore=" + dpScore + ", gsScore=" + gsScore + ").");
    }
}