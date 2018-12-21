
public class Main {

    public static void main(String[] args) {
	// write your code here
        AStar aStar = new AStar(5,5,new Cell(0,0), new Cell(3,2), new int[][] {{0,4}, {2,2}, {3,1}, {3,3}, {2,1}, {2,3}});
        aStar.display();
        aStar.process();
        aStar.displayScores();
        aStar.displaySolution();
    }
}
