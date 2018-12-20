package pl.pjwstk;

public class Cell {

    int x, y;
    Cell parent;
    /**
     * droga pomiędzy wierzchołkiem początkowym a x.
     * Dokładniej: suma wag krawędzi, które należą już do ścieżki plus waga krawędzi łączącej aktualny węzeł z x.
     */
    /**
     * przewidywana przez heurystykę droga od x do wierzchołka docelowego.
     */
    //---------------------
    int finalCost;
    int heuristicCost;
    /**czy komórka jest częścią najkrótszej drogi*/
    boolean solution;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
