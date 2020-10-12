package datatypes;

public class Wall {
	
	private Node a, b;

	/**
	 * Constructs a new wall with two nodes
	 * @param a first node
	 * @param b second (and neighbouring) node
	 */
	public Wall(Node a, Node b) {
		if (!isValidWall(a, b)) {
			throw new IllegalArgumentException("Nodes must be neighbours, got " + a + " and " + b);
		}
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Checks if two nodes can have a wall between them, i.e. they are neighbours.
	 * @param a first node
	 * @param b second node
	 * @return true if a wall can be constructed between the two nodes (true if node a is next to
	 * node b
	 */
	public static boolean isValidWall(Node a, Node b) {
		return a.isNextTo(b);
	}

	/**
	 * Returns the first node on one side of the wall
	 * @return Node object of the node on one side
	 */
	public Node getA() {
		return a;
	}
	
	/**
	 * Returns the second node on the other side of the wall
	 * @return Node object of the node on the other side
	 */
	public Node getB() {
		return b;
	}
	
	/**
	 * Checks if two walls are equal.
	 * @param b wall with which we check if this wall is equal to
	 * @return true if this node and node b has the same set of nodes
	 */
	public boolean equals(Wall b) {
		return (this.a.equals(b.getA()) && this.b.equals(b.getB())) ||
			   (this.a.equals(b.getB()) && this.b.equals(b.getA()));
	}
	
	@Override
	public String toString() {
		return a + " | " + b;
	}

}
