package pl.pjwstk;

import java.util.Comparator;
import java.util.PriorityQueue;

public class AStar {
    static final int DIAGONAL_COST = 14;
    static final int V_H_COST = 10;
    private Cell[][] grid;
    /**dostępne komórki*/
    private PriorityQueue<Cell> openCells;
    /**niedostępne komórki*/
    boolean [][] closedCells;
    Cell startCell;
    Cell endCell;

    public AStar(int width, int height, Cell startCell, Cell endCell, int[][]blocks) {
        grid = new Cell[width][height];
        closedCells = new boolean[width][height];
//        openCells = new PriorityQueue<Cell>((Cell c1, Cell c2) -> c1.h < c2.h ? -1 : c1.h > c2.h ? 1 : 0);
        openCells = new PriorityQueue<>(Comparator.comparingInt((Cell c) -> c.heuristicCost));

        //inicjalizacja siatki z heurystykami
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
                grid[i][j].heuristicCost = Math.abs(i - endCell.x) + Math.abs(j - endCell.y);
                grid[i][j].solution = false;
            }
        }

        grid[startCell.x][startCell.y].finalCost = 0;

        //we put the block on the grid
        for (int i = 0; i < blocks.length; i++) {
            addBlockOnCell(blocks[i][0], blocks[i][1]);
        }
    }

    private void addBlockOnCell(int i, int j) {
        grid[i][j] = null;
    }

    void updateCostIfNeeded(Cell current, Cell t, int cost) {
        if (t == null || closedCells[t.x][t.y]) return;
        int tFinalCost = t.heuristicCost + cost;
        boolean isOpen = openCells.contains(t);
        if (!isOpen || tFinalCost < t.finalCost) {
            t.finalCost = tFinalCost;
            t.parent = current;

            if (!isOpen) openCells.add(t);
        }
    }

    void process() {
        //we add start location to open list
        openCells.add(startCell);
        Cell current;

        while(true) {
            current = openCells.poll();

            if (current == null) break;
            closedCells[current.x][current.y] = true;
            if (current.equals(endCell)) return;

            Cell t;

            if (current.x - 1>= 0) {
                t = grid[current.x - 1][current.y];
                updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
            }
        }
    }
}
