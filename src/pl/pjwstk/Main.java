package pl.pjwstk;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        AStar aStar = new AStar(10,10,new Cell(0,0), new Cell(3,2), new int[][] {{0,4}, {2,2}, {3,1}, {3,3}, {2,1}, {2,3}});
//        aStar.display();
//        aStar.process();
//        aStar.displayScores();
//        aStar.displaySolution();
        int gridSize = 10;
        AStar aStar = new AStar(gridSize);
//        aStar.printNumberedGrid();
//        System.out.println("Wybierz punkt startowy:");
//        Scanner in = new Scanner(System.in);
//        aStar.startCell = in.nextInt();
        aStar.startCell = 11;
//        System.out.println("Wybierz punkt docelowy:");
//        aStar.endCell = in.nextInt();
        aStar.endCell = 78;
//        System.out.println("Wybierz niedostępne komórki:");
//        String unavailableCells = in.nextLine();
        aStar.unavailableCells = Arrays.asList(21, 22, 23, 42, 43, 44, 45, 27, 37, 47, 57, 67, 77, 87);
        aStar.printNumberedGrid();
    }

}
