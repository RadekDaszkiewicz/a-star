package pl.pjwstk;

import java.util.List;

import static java.lang.System.out;

public class AStar {
    Cell[][] grid;
    Integer startCell;
    Integer endCell;
    List<Integer> unavailableCells;

    AStar(int size) {
        this.grid = new Cell[size][size];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y] = new Cell(x, y);
            }
        }
    }

    void printNumberedGrid() {
        out.println("\nPonumerowana siatka: ");
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (startCell != null && grid[x][y].number == startCell) {
//                    System.out.print("\u2605 ");// Star
                    out.print("\u24C8 ");// S
                } else if (endCell != null && grid[x][y].number == endCell) {
                    out.print("\u2691  ");// flaga

                } else if (unavailableCells.contains(grid[x][y].number)) {
                    System.out.print("\u2588\u2588 ");// full block
                } else {
                    out.print(String.format("%02d" , grid[x][y].number) + " ");
                }
            }
            out.println();
        }
    }

}
