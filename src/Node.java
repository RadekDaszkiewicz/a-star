import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Node {

	enum State {
		UNVISITED, OPEN, BLOCKED, CLOSED, START, DESTINATION, SOLUTION
	}

	State state;
	final Point2D.Double coordinates;
	Node parent;
	final Set<Node> neighbours = new HashSet<Node>();
	double heuristicCost; // dystans od punktu docelowego
	Double gCost; // dystans od punktu startowego
	Double finalCost; // heuristicCost + gCost
//
//	public Node(Point2D.Double coordinates) {
//		this.coordinates = coordinates;
//	}

	public Node(State state, Point2D.Double coordinates) {
		this.state = state;
		this.coordinates = coordinates;
	}

	public Node(State state, Point2D.Double coordinates, Point2D.Double destination) {
		this.state = state;
		this.coordinates = coordinates;
		setHeuristicCost(destination);
		roundCosts();
	}

	void updateGCost(double gCost) {
		this.gCost = gCost;
		this.finalCost = this.heuristicCost + this.gCost;
		roundCosts();
	}
//
//	void setHeuristicCost(Node destination) {
//		this.heuristicCost = this.coordinates.distance(destination.coordinates);
//	}

	void setHeuristicCost(Point2D.Double destination) {
		this.heuristicCost = this.coordinates.distance(destination);
	}

	void markAsSolutionNode() {
		this.state = State.SOLUTION;
		if (this.parent != null) {
			this.parent.markAsSolutionNode();
		}
	}

//	String createNeighboursData() {
//		StringBuilder builder = new StringBuilder();
//		if (this.neighbours != null) {
//			this.neighbours.stream()
//					.filter(n -> n.state != State.BLOCKED)
//					.forEach(Node::toString);
//		}
//		return builder.toString();
//	}

	void roundCosts() {
		this.gCost = this.gCost != null ? new BigDecimal(this.gCost).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() : null;
		this.heuristicCost = new BigDecimal(this.heuristicCost).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.finalCost = this.finalCost != null ? new BigDecimal(this.finalCost).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() : null;
	}

//	public void printParent() {
//		if (this.parent != null) {
//			System.out.println(this.parent);
//			this.parent.printParent();
//		}
//	}

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
		StringBuilder builder = new StringBuilder();
		switch (this.state) {
		case OPEN:
			builder.append("\u001B[32m");
			break;
		case CLOSED:
			builder.append("\u001B[31m");
			break;
		case START:
			builder.append("\u001B[36m");
			break;
		case DESTINATION:
			builder.append("\u001B[36m");
			break;
		case SOLUTION:
			builder.append("\u001B[36m");
			break;
		}
		builder.append("[")
				.append(Math.round(this.coordinates.getX()))
				.append(Math.round(this.coordinates.getY()))
				.append("]: g(x) = ")
				.append(this.gCost)
				.append(", h(x) = ")
				.append(this.heuristicCost)
				.append(", f(x) = ")
				.append(this.finalCost)
				.append("\u001B[0m");
		return builder.toString();
	}
}
