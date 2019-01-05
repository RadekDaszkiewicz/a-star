import java.awt.geom.Point2D;
import java.util.PriorityQueue;

public class AStar {
	Node startNode;
	Node destinationNode;
	Node[][] grid;
	PriorityQueue<Node> openNodes = new PriorityQueue<>((n1, n2) -> {
		if (n1.finalCost < n2.finalCost) {
			return -1;
		} else if (n1.finalCost > n2.finalCost) {
			return 1;
		} else {
			if (n1.heuristicCost < n2.heuristicCost) {
				return -1;
			} else if (n1.heuristicCost > n2.heuristicCost) {
				return 1;
			} else {
				return 0;
			}
		}
	});

	AStar(Point2D.Double startPoint, Point2D.Double destinationPoint, int size, int[][] blocked) {
		this.grid = new Node[size][size];
		this.destinationNode = new Node(Node.State.DESTINATION, destinationPoint, destinationPoint);
		this.startNode = new Node(Node.State.START, startPoint, destinationPoint);
		this.startNode.gCost = 0.0;     //todo
		this.openNodes.add(this.startNode);
		//inicjalizacja siatki z heurystykami
		for (int x = 0; x < this.grid.length; x++) {
			for (int y = 0; y < this.grid[x].length; y++) {
				this.grid[x][y] = new Node(Node.State.UNVISITED, new Point2D.Double(x, y), destinationPoint);
			}
		}
		// umieszczenie na siatce pola startowego, docelowego oraz pól zablokowanych
		this.grid[(int) startPoint.getX()][(int) startPoint.getY()] = this.startNode;
		this.grid[(int) destinationPoint.getX()][(int) destinationPoint.getY()] = this.destinationNode;
		for (int x = 0; x < blocked.length; x++) {
			this.grid[blocked[x][0]][blocked[x][1]] = new Node(Node.State.BLOCKED, new Point2D.Double(blocked[x][0],blocked[x][1]));
		}
		//dodanie sąsiadów
		for (int x = 0; x < this.grid.length; x++) {
			for (int y = 0; y < this.grid[x].length; y++) {
				setNeighbours(this.grid[x][y]);
			}
		}
	}

	void process() {
		Node current = this.openNodes.poll();
		current.state = Node.State.CLOSED; //todo
		updateNeighbours(current);
		System.out.println(current);
	}

	void updateNeighbours(Node node) {
		node.neighbours.stream()
				.filter(n -> !(n.state == Node.State.CLOSED || n.state == Node.State.BLOCKED))
				.forEach(n -> {
					Double newGCost = node.gCost + n.coordinates.distance(node.coordinates);
					if (n.gCost == null || newGCost < n.gCost) {
						n.updateGCost(newGCost);
						n.parent = node;
						n.state = Node.State.OPEN;
						this.openNodes.add(n);
					}
		});
	}

	void setNeighbours(Node node) {
		if (node.coordinates.getX() - 1 >= 0) {
			node.neighbours.add(this.grid[(int) node.coordinates.getX() - 1][(int) node.coordinates.getY()]);
			if (node.coordinates.getY() - 1 >= 0) {
				node.neighbours.add(this.grid[(int) node.coordinates.getX() - 1][(int) node.coordinates.getY() - 1]);
			}
			if (node.coordinates.getY() + 1 < this.grid[0].length) {
				node.neighbours.add(this.grid[(int) node.coordinates.getX() - 1][(int) node.coordinates.getY() + 1]);
			}
		}
		if (node.coordinates.getY() - 1 >= 0) {
			node.neighbours.add(this.grid[(int) node.coordinates.getX()][(int) node.coordinates.getY() - 1]);
		}
		if (node.coordinates.getY() + 1 < this.grid[0].length) {
			node.neighbours.add(this.grid[(int) node.coordinates.getX()][(int) node.coordinates.getY() + 1]);
		}
		if (node.coordinates.getX() + 1 < this.grid.length) {
			node.neighbours.add(this.grid[(int) node.coordinates.getX() + 1][(int) node.coordinates.getY()]);
			if (node.coordinates.getY() - 1 >= 0) {
				node.neighbours.add(this.grid[(int) node.coordinates.getX() + 1][(int) node.coordinates.getY() - 1]);
			}
			if (node.coordinates.getY() + 1 < this.grid[0].length) {
				node.neighbours.add(this.grid[(int) node.coordinates.getX() + 1][(int) node.coordinates.getY() + 1]);
			}
		}
	}

	void display() {
		System.out.println("\n");
		for (int x = 0; x < this.grid.length; x++) {
			for (int y = 0; y < this.grid[x].length; y++) {
				Node node = this.grid[x][y];
				switch (node.state) {
				case UNVISITED:
					System.out.printf("%02d ", Math.round(node.coordinates.getX()) * 10 + Math.round(node.coordinates.getY()));
					break;
				case OPEN:
					System.out.print("\u001B[32m" + String.format("%02d ", Math.round(node.coordinates.getX()) * 10 + Math.round(node.coordinates.getY())) + "\u001B[0m");
					break;
				case CLOSED:
					System.out.print("\u001B[31m" + String.format("%02d ", Math.round(node.coordinates.getX()) * 10 + Math.round(node.coordinates.getY())) + "\u001B[0m");
					break;
				case BLOCKED:
					System.out.print("\u2588\u2588");
					if (y < this.grid[x].length - 1 && this.grid[x][y + 1].state == Node.State.BLOCKED) {
						System.out.print("\u2588");
					} else {
						System.out.print(" ");
					}
					break;
				case START:
					System.out.print("\u001B[36m" + String.format("%02d ", Math.round(node.coordinates.getX()) * 10 + Math.round(node.coordinates.getY())) + "\u001B[0m");
					break;
				case DESTINATION:
					System.out.print("\u001B[36m" + String.format("%02d ", Math.round(node.coordinates.getX()) * 10 + Math.round(node.coordinates.getY())) + "\u001B[0m");
					break;
				case SOLUTION:
					System.out.print("\u001B[36m" + String.format("%02d ", Math.round(node.coordinates.getX()) * 10 + Math.round(node.coordinates.getY())) + "\u001B[0m");
					break;
				}
			}
			System.out.println();
		}
	}
}
