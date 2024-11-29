package com.example.game2048;

import java.util.Random;

public class Game2048Manager {
    private static final int GRID_SIZE = 4; // The size of the grid (4x4)
    private int[][] grid;
    private boolean gameWon = false;

    public Game2048Manager() {
        grid = new int[GRID_SIZE][GRID_SIZE];
        spawnTile();
        spawnTile();
    }

    public int[][] getGrid() {
        return grid;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean move(String direction) {
        boolean moved = false;
        switch (direction) {
            case "UP":
                moved = moveUp();
                break;
            case "DOWN":
                moved = moveDown();
                break;
            case "LEFT":
                moved = moveLeft();
                break;
            case "RIGHT":
                moved = moveRight();
                break;
        }

        if (moved) {
            spawnTile(); // Spawn a new tile if the grid changed
            checkGameWon();
        }
        return moved;
    }

    private void spawnTile() {
        Random random = new Random();
        int value = random.nextInt(10) < 9 ? 2 : 4; // 90% chance for 2, 10% for 4
        int x, y;

        // Find a random empty spot
        do {
            x = random.nextInt(GRID_SIZE);
            y = random.nextInt(GRID_SIZE);
        } while (grid[x][y] != 0);

        grid[x][y] = value;
    }

    private void checkGameWon() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 2048) {
                    gameWon = true; // Mark game as won when 2048 is reached
                    return;
                }
            }
        }
    }

    private boolean moveUp() {
        boolean moved = false;

        for (int col = 0; col < GRID_SIZE; col++) {
            int[] column = new int[GRID_SIZE];
            for (int row = 0; row < GRID_SIZE; row++) {
                column[row] = grid[row][col];
            }

            int[] mergedColumn = mergeTiles(column);
            for (int row = 0; row < GRID_SIZE; row++) {
                if (grid[row][col] != mergedColumn[row]) {
                    moved = true;
                }
                grid[row][col] = mergedColumn[row];
            }
        }

        return moved;
    }

    private boolean moveDown() {
        boolean moved = false;

        for (int col = 0; col < GRID_SIZE; col++) {
            int[] column = new int[GRID_SIZE];
            for (int row = 0; row < GRID_SIZE; row++) {
                column[row] = grid[GRID_SIZE - 1 - row][col];
            }

            int[] mergedColumn = mergeTiles(column);
            for (int row = 0; row < GRID_SIZE; row++) {
                if (grid[GRID_SIZE - 1 - row][col] != mergedColumn[row]) {
                    moved = true;
                }
                grid[GRID_SIZE - 1 - row][col] = mergedColumn[row];
            }
        }

        return moved;
    }

    private boolean moveLeft() {
        boolean moved = false;

        for (int row = 0; row < GRID_SIZE; row++) {
            int[] line = grid[row];
            int[] mergedLine = mergeTiles(line);

            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col] != mergedLine[col]) {
                    moved = true;
                }
                grid[row][col] = mergedLine[col];
            }
        }

        return moved;
    }

    private boolean moveRight() {
        boolean moved = false;

        for (int row = 0; row < GRID_SIZE; row++) {
            int[] line = new int[GRID_SIZE];
            for (int col = 0; col < GRID_SIZE; col++) {
                line[col] = grid[row][GRID_SIZE - 1 - col];
            }

            int[] mergedLine = mergeTiles(line);
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][GRID_SIZE - 1 - col] != mergedLine[col]) {
                    moved = true;
                }
                grid[row][GRID_SIZE - 1 - col] = mergedLine[col];
            }
        }

        return moved;
    }

    private int[] mergeTiles(int[] tiles) {
        int[] newTiles = new int[GRID_SIZE];
        int pos = 0;

        for (int i = 0; i < GRID_SIZE; i++) {
            if (tiles[i] != 0) {
                if (pos > 0 && newTiles[pos - 1] == tiles[i]) {
                    // Merge tiles of the same value
                    newTiles[pos - 1] *= 2;
                } else {
                    // Move the tile
                    newTiles[pos] = tiles[i];
                    pos++;
                }
            }
        }

        return newTiles;
    }
}
