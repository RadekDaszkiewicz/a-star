package pl.pjwstk;

public class Cell {

    int x, y;
    Integer number;
    Cell parent;
    /**
     * droga pomiędzy wierzchołkiem początkowym a x.
     * Dokładniej: suma wag krawędzi, które należą już do ścieżki plus waga krawędzi łączącej aktualny węzeł z x.
     */
    int finalCost;
    /**
     * przewidywana przez heurystykę droga od x do wierzchołka docelowego.
     */
    //---------------------
    int heuristicCost;
    /**czy komórka jest częścią najkrótszej drogi*/
    boolean solution;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.number = x * 10 + y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
