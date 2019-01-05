import java.awt.geom.Point2D;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Point2D.Double startPoint = new Point2D.Double(3,3);
        Point2D.Double destinationPoint = new Point2D.Double(9,9);
        int[][] blocked = new int[][]{{3,8}, {4,8}, {5,8}, {6,4}, {6,5}, {6,6}, {6,7}, {6,8}};
        AStar aStar =  new AStar(startPoint, destinationPoint, 10, blocked);
        aStar.display();
        Scanner input = new Scanner(System.in);
        input.nextLine();
        while (aStar.openNodes.size() > 0) {
            aStar.process();
            aStar.display();
            input.nextLine();
        }
    }
}
