import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class Node {

	public enum State {
		UNVISITED, OPEN, BLOCKED, CLOSED, START, DESTINATION, SOLUTION
	}

	State state;
	final Point2D.Double coordinates;
	Node parent;
	final Set<Node> neighbours = new HashSet<Node>();
	double heuristicCost; // dystans od punktu docelowego
	Double gCost; // dystans od punktu startowego
	Double finalCost; // heuristicCost + gCost

	public Node(Point2D.Double coordinates) {
		this.coordinates = coordinates;
	}

	public Node(State state, Point2D.Double coordinates) {
		this.state = state;
		this.coordinates = coordinates;
	}

	public Node(State state, Point2D.Double coordinates, Point2D.Double destination) {
		this.state = state;
		this.coordinates = coordinates;
		setHeuristicCost(destination);
	}

	void updateGCost(double gCost) {
		this.gCost = gCost;
		this.finalCost = this.heuristicCost + this.gCost;
	}

	void setHeuristicCost(Node destination) {
		this.heuristicCost = this.coordinates.distance(destination.coordinates);
	}

	void setHeuristicCost(Point2D.Double destination) {
		this.heuristicCost = this.coordinates.distance(destination);
	}

	void setAsPath() {
		this.state = State.SOLUTION;
		if (parent != null) {
			parent.setAsPath();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Node)) {
			return false;
		}

		Node node = (Node) o;

		return this.coordinates.equals(node.coordinates);

	}

	@Override
	public int hashCode() {
		return this.coordinates.hashCode();
	}

	@Override
	public String toString() {
		return "Węzeł: " + Math.round(coordinates.getX()) + Math.round(coordinates.getY()) +
				"\nStan: " + state +
				"\nRodzic:" + (parent != null ? Math.round(parent.coordinates.getX()) * 10 + Math.round(parent.coordinates.getY()) : "brak") +
				"\nKoszt g(x) =" + gCost +
				"\nKoszt h(x) =" + heuristicCost +
				"\nKoszt łączny =" + finalCost +
				"\nLiczba sąsiadów:" + neighbours.size();
	}
}
