import java.awt.geom.Point2D;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Point2D.Double startPoint = new Point2D.Double(3,3);
        Point2D.Double destinationPoint = new Point2D.Double(9,9);
        int[][] blocked = new int[][]{{3,8}, {4,8}, {5,8}, {6,4}, {6,5}, {6,6}, {6,7}, {6,8}};
//        Point2D.Double startPoint = new Point2D.Double(1,1);
//        Point2D.Double destinationPoint = new Point2D.Double(0,9);
//        int[][] blocked = new int[][]{
//                {0,3}, {0,6},
//                {1,3}, {1,5}, {1,6}, {1,8},
//                {2,3}, {2,1}, {2,5}, {2,8},
//                {3,1}, {3,5}, {3,7}, {3,8},
//                {4,1}, {4,2}, {4,3}, {4,4}, {4,5}, {4,7}, {4,8},
//                {5,1}, {5,5}, {5,8},
//                {6,1}, {6,3}, {6,5}, {6,6}, {6,8},
//                {7,3}, {7,8},
//                {8,1}, {8,2}, {8,3}, {8,4}, {8,5}, {8,6}, {8,7}, {8,8},
//        };
        AStar aStar =  new AStar(startPoint, destinationPoint, 10, blocked);
        aStar.display();
        Scanner input = new Scanner(System.in);
        input.nextLine();
        while (!aStar.solutionFound && aStar.openNodes.size() > 0) {
            aStar.process();
            aStar.display();
            input.nextLine();
        }
    }
}
