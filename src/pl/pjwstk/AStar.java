import java.util.Comparator;
import java.util.PriorityQueue;

public class AStar {
	static final int DIAGONAL_COST = 14;
	static final int V_H_COST = 10;
	private Cell[][] grid;
	/**
	 * dostępne komórki
	 */
	private PriorityQueue<Cell> openCells;
	/**
	 * niedostępne komórki
	 */
	boolean[][] closedCells;
	Cell startCell;
	Cell endCell;

	public AStar(int width, int height, Cell startCell, Cell endCell, int[][] blocks) {
		this.grid = new Cell[width][height];
		this.startCell = startCell;
		this.endCell = endCell;
		this.closedCells = new boolean[width][height];
//        openCells = new PriorityQueue<Cell>((Cell c1, Cell c2) -> c1.h < c2.h ? -1 : c1.h > c2.h ? 1 : 0);
		this.openCells = new PriorityQueue<>(Comparator.comparingInt((Cell c) -> c.heuristicCost));

		//inicjalizacja siatki z heurystykami
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[i].length; j++) {
				this.grid[i][j] = new Cell(i, j);
				this.grid[i][j].heuristicCost = Math.abs(i - endCell.x) + Math.abs(j - endCell.y);
				this.grid[i][j].solution = false;
			}
		}

		this.grid[startCell.x][startCell.y].finalCost = 0;

		//we put the block on the grid
		for (int i = 0; i < blocks.length; i++) {
			addBlockOnCell(blocks[i][0], blocks[i][1]);
		}
	}

	private void addBlockOnCell(int i, int j) {
		this.grid[i][j] = null;
	}

	void updateCostIfNeeded(Cell current, Cell t, int cost) {
		if (t == null || this.closedCells[t.x][t.y]) {
			return;
		}
		int tFinalCost = t.heuristicCost + cost;
		boolean isOpen = this.openCells.contains(t);
		if (!isOpen || tFinalCost < t.finalCost) {
			t.finalCost = tFinalCost;
			t.parent = current;

			if (!isOpen) {
				this.openCells.add(t);
			}
		}
	}

	void process() {
		//we add start location to open list
		this.openCells.add(this.startCell);
		Cell current;

		while (true) {
			current = this.openCells.poll();

			if (current == null) {
				break;
			}
			this.closedCells[current.x][current.y] = true;
			if (current.equals(this.endCell)) {
				return;
			}

			Cell t;

			if (current.x - 1 >= 0) {
				t = this.grid[current.x - 1][current.y];
				updateCostIfNeeded(current, t, current.finalCost + V_H_COST);

				if (current.y - 1 >= 0) {
					t = this.grid[current.x - 1][current.y - 1];
					updateCostIfNeeded(current, t, current.finalCost + DIAGONAL_COST);
				}

				if (current.y + 1 < this.grid[0].length) {
					t = this.grid[current.x - 1][current.y + 1];
					updateCostIfNeeded(current, t, current.finalCost + DIAGONAL_COST);
				}
			}
			if (current.y - 1 >= 0) {
				t = this.grid[current.x][current.y - 1];
				updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
			}
			if (current.y + 1 < this.grid[0].length) {
				t = this.grid[current.x][current.y + 1];
				updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
			}
			if (current.x + 1 < this.grid.length) {
				t = this.grid[current.x + 1][current.y];
				updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
				if (current.y - 1 >= 0) {
					t = this.grid[current.x + 1][current.y - 1];
					updateCostIfNeeded(current, t, current.finalCost + DIAGONAL_COST);
				}

				if (current.y + 1 < this.grid[0].length) {
					t = this.grid[current.x + 1][current.y + 1];
					updateCostIfNeeded(current, t, current.finalCost + DIAGONAL_COST);
				}
			}

		}
	}

	void display() {
		System.out.println("Grid: ");

		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[i].length; j++) {
				if (i == this.startCell.x && j == this.startCell.y) {
					System.out.print("ST ");
				} else if (i == this.endCell.x && j == this.endCell.y) {
					System.out.print("EN ");
				} else if (this.grid[i][j] != null) {
					System.out.printf("%-3d ", 0);
				} else {
					System.out.print("BL ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	void displayScores() {
		System.out.println("\nScores dla komórek: ");

		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[i].length; j++) {
				if (this.grid[i][j] != null) {
					System.out.printf("%-3d ", this.grid[i][j].finalCost);
				} else {
					System.out.print("BL ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	void displaySolution() {
		if (this.closedCells[this.endCell.x][this.endCell.y]) {
			//we track back the path
			System.out.println("Droga: ");
			Cell current = this.grid[this.endCell.x][this.endCell.y];
			System.out.println(current);
			this.grid[current.x][current.y].solution = true;

			while(current.parent != null) {
				System.out.println(" -> " + current.parent);
				this.grid[current.parent.x][current.parent.y].solution = true;
				current = current.parent;
			}

			System.out.println("\n");

			for (int i = 0; i < this.grid.length; i++) {
				for (int j = 0; j < this.grid[i].length; j++) {
					if (i == this.startCell.x && j == this.startCell.y) {
						System.out.print("ST ");
					} else if (i == this.endCell.x && j == this.endCell.y) {
						System.out.print("EN ");
					} else if (this.grid[i][j] != null) {
						System.out.printf("%-3d ", this.grid[i][j].solution ? "X" : "0"); //jedyna zmiana w tym forze
					} else {
						System.out.print("BL ");
					}
				}
				System.out.println();
			}
			System.out.println();
		} else {
			System.out.println("Nie możliwy pafffsd");
		}
	}
}
