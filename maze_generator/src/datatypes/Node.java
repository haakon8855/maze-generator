package datatypes;

public class Node {
	
	private int x, y;
	private int value;
	
	/**
	 * Creates a node with coordinates x and y
	 * @param x and y coordinates for the node
	 */
	public Node(int x, int y) {
		if (!isValidNode(x, y)) {
			throw new IllegalArgumentException("Both coords must be positive, got x: "+x+"y: "+y);
		}
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a node with coordinates x and y
	 * @param x and y coordinates for the node
	 * @param int value the node is given
	 */
	public Node(int x, int y, int value) {
		this(x, y);
		this.value = value;
	}
	
	/**
	 * @return true if both coordinates are valid
	 */
	public static boolean isValidNode(int x, int y) {
		return x >= 0 && y >= 0;
		
	}
	
	/**
	 * @return the node's x-coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return the node's y-coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the node's value
	 * @param int value the node will be given
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Returns the value this node is given
	 * @return int value this node is currently given
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Gets the neighbouring nodes to the south and east of this node
	 * @return Node-array of the south and east neighbouring nodes.
	 */
	public Node[] getSouthEastNeighboursArray(int width, int height) {
		Node[] neighbours = {null, null};
		if (x < width-1) {
			neighbours[0] = new Node(x+1, y);
		}
		if (y < height-1) {
			neighbours[1] = new Node(x, y+1);
		}
		return neighbours;
	}

	/**
	 * Gets all neighbouring nodes to this node
	 * @return Collection<Node> of the neighbouring nodes. Maximum 4
	 */
	public NodeCollection getNeighbours(int width, int height) {
		NodeCollection neighbours = new NodeCollection();
		if (x > 0) {
			neighbours.add(new Node(x-1, y));
		}
		if (x < width-1) {
			neighbours.add(new Node(x+1, y));
		}
		if (y > 0) {
			neighbours.add(new Node(x, y-1));
		}
		if (y < height-1) {
			neighbours.add(new Node(x, y+1));
		}
		return neighbours;
	}
	
	/**
	 * @param b node with which we check if it is a neighbour
	 * @return true if node b is next to this node
	 */
	public boolean isNextTo(Node b) {
		return (( (this.x == b.getX()) && (this.y == b.getY()+1 || this.y == b.getY()-1) )
			 || ( (this.y == b.getY()) && (this.x == b.getX()+1 || this.x == b.getX()-1) ));
	}
	
	/**
	 * Checks if two nodes are equal. Two nodes are equal if the have the same coordinates
	 * @param b node with which we check if this node is equal to
	 * @return true if the two nodes are equal
	 */
	public boolean equals(Node b) {
		return this.x == b.getX() && this.y == b.getY();
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}



















