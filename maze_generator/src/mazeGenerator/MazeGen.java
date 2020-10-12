package mazeGenerator;

import datatypes.Maze;
import datatypes.Wall;
import datatypes.Node;

public abstract class MazeGen {
	
	protected Maze maze;
	protected long seed;
	protected MazeDrawer drawer;
	protected int startX = 0, 
				  startY = 0;
	protected int delayDuration = 0;
	
	/**
	 * Constructs a MazeGenerator with maze width and height given in number of nodes.
	 * @param width measured in nodes
	 * @param height measured in nodes
	 */
	public MazeGen(int width, int height, MazeDrawer drawer) {
		this.maze = new Maze(width, height);
		this.drawer = drawer;
		startX = width/2;
		startY = height/2;
	}
	
	/**
	 * Generates a maze with just walls
	 */
	public void generate() {
		setAllWalls();
		setAllNodes();
	}
	
	/**
	 * Gets the seed used to create the maze
	 * @return long seed which can be passed to recreate the maze
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * Pauses execution for the delay duration specified in the delayDuration variable
	 */
	public void delay() {
		try {
			Thread.sleep(delayDuration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates all possible walls in the maze.
	 */
	public void setAllWalls() {
		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getWidth(); x++) {
				Node currentNode = new Node(x, y);
				int width = maze.getWidth();
				int height = maze.getHeight();
				for (Node neighbour : currentNode.getNeighbours(width, height)) {
					maze.addWall(currentNode, neighbour);
				}
			}
		}
	}

	/**
	 * Gives all nodes in the maze a value of 1, indicating that they have not yet been visited
	 */
	public void setAllNodes() {
		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getWidth(); x++) {
				maze.setNodeValue(x, y, 1);
			}
		}
	}
	
	/**
	 * Fetches the Maze-instance for this MazeGenerator
	 * @return maze object representing the current maze
	 */
	public Maze getMaze() {
		return this.maze;
	}
	
	/**
	 * Is called when the maze is changed and needs updating.
	 * Delays and then updates the drawn maze.
	 */
	public void mazeChanged() {
		delay();
		this.drawer.updateMaze(this.maze);
	}

	/**
	 * Fetches the wall-iterator from the current maze and returns it.
	 * @return iterator containing all the walls in the current maze
	 */
	public Iterable<Wall> getWallsIterator() {
		return maze.getWallsIterator();
	}
	
	@Override
	public String toString() {
		return maze.toString();
	}
	
	public static void main(String[] args) {
		int w = 100;
		int h = 100;
		MazeDrawer drawer = new MazeDrawer(w, h);
		MazeGen gen = new MazeGenDFS(w, h, drawer);
//		MazeGen gen = new MazeGenDFS(w, h, drawer);
		gen.generate();
		drawer.updateMaze(gen.getMaze());
	}

}




























