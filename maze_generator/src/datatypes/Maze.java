package datatypes;

public class Maze {
	
	private final int width, height;
	private final WallCollection walls = new WallCollection();
	private final NodeCollection nodes = new NodeCollection();
	
	/**
	 * Constructs a new maze with the given width and height
	 * @param width of the maze in nodes
	 * @param height of the maze in nodes
	 */
	public Maze(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Returns the width of the maze, measured in nodes.
	 * @return width in nodes
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the height of the maze, measured in nodes.
	 * @return height in nodes
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Adds the give wall w to the maze
	 * @param w wall to be added
	 */
	public void addWall(Wall w) {
		walls.add(w);
	}
	
	/**
	 * Adds the wall between nodes a and b to the maze
	 * @param a node
	 * @param b node
	 */
	public void addWall(Node a, Node b) {
		Wall w = new Wall(a, b);
		addWall(w);
	}
	
	/**
	 * Adds a wall between the nodes a and b, where a is given by (x1, y1) 
	 * and b is given by (x2, y2)
	 * @param x1 first coordinate of a
	 * @param y1 second coordinate of a
	 * @param x2 first coordinate of b
	 * @param y2 second coordinate of b
	 */
	public void addWall(int x1, int y1, int x2, int y2) {
		Node a = new Node(x1, y1);
		Node b = new Node(x2, y2);
		addWall(a, b);
	}
	
	/**
	 * Removes the given wall w (or its corresponding wall) from the maze.
	 * @param w wall to be removed
	 * @return the wall corresponding to w which was removed, or null if the given wall w was not 
	 * in the maze 
	 */
	public Wall removeWall(Wall w) {
		return walls.remove(w);
	}
	
	/**
	 * Removes the i'th wall in the maze
	 * @param i
	 * @return wall object of the wall which was removed
	 */
	public Wall removeWall(int i) {
		return walls.remove(i);
	}
	
	/**
	 * Removes the wall between the given nodes a and b from the maze.
	 * @param a first node
	 * @param b second node
	 * @return the wall in the maze corresponding to the wall between a and b which was removed, 
	 * or null if there was no wall between node a and b 
	 */
	public Wall removeWall(Node a, Node b) {
		Wall wall = new Wall(a, b);
		return walls.remove(wall);
	}

	/**
	 * Sets a node value, if the given value is 0 the node is deleted from the nodeCollection.
	 * @param node object to set the value to
	 * @param int value to set to the given node
	 */
	public void setNodeValue(Node node, int value) {
		if (value == 0) {
			nodes.remove(node);
			return;
		}
		if (!nodes.contains(node)) {
			nodes.add(node);
		}
		nodes.setNodeValue(node, value);
	}

	/**
	 * Sets a node value, if the given value is 0 the node is deleted from the nodeCollection.
	 * @param x coordinate of the node.
	 * @param y coordinate of the node.
	 * @param int value to set to the given node coordinates.
	 */
	public void setNodeValue(int x, int y, int value) {
		Node node = new Node(x, y);
		setNodeValue(node, value);
	}

	/**
	 * Returns the value assigned to the given node. If the node is not in the nodeCollection its 
	 * value is 0 by default.
	 * @param node object to get the value from
	 * @return int value assigned to the given node
	 */
	public int getNodeValue(Node node) {
		return nodes.getNodeValue(node);
	}

	/**
	 * Returns the value assigned to the given node. If the node is not in the nodeCollection its 
	 * value is 0 by default.
	 * @param x coordinate of the requested node
	 * @param y coordinate of the requested node
	 * @return int value assigned to the given node.
	 */
	public int getNodeValue(int x, int y) {
		Node node = new Node(x, y);
		return nodes.getNodeValue(node);
	}
	
	/**
	 * Fetches the corresponding node object from the nodeCollection.
	 * @param node which is requested.
	 * @return node object corresponding to the given node in the nodeCollection.
	 */
	public Node getNode(Node node) {
		return nodes.get(node);
	}

	/**
	 * Fetches the corresponding node object from the nodeCollection.
	 * @param x coordinate of the requested node
	 * @param y coordinate of the requested node
	 * @return node object corresponding to the given node in the nodeCollection.
	 */
	public Node getNode(int x, int y) {
		Node node = new Node(x, y);
		return getNode(node);
	}
	
	/**
	 * Creates an iterator instance of the collection of walls in the maze.
	 * @return an iterator containing all the maze's walls
	 */
	public Iterable<Wall> getWallsIterator() {
		return walls;
	}

	/**
	 * Creates an iterator instance of the collection of nodes with given values in the maze.
	 * @return an iterator containing all the maze's nodes with given values.
	 */
	public Iterable<Node> getNodesIterator() {
		return nodes;
	}
	
	/**
	 * Returns the amount of walls in the maze
	 * @return int with amount of walls in the maze
	 */
	public int getWallCount() {
		return walls.size();
	}
	
	/**
	 * Returns a string representation of the maze.
	 */
	@Override
	public String toString() {
		return walls.toString();
	}

}



























