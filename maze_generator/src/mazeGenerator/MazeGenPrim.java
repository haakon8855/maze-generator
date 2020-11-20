package mazeGenerator;

import java.util.Random;

import datatypes.Node;
import datatypes.NodeCollection;
import datatypes.Wall;
import datatypes.WallCollection;

public class MazeGenPrim extends MazeGen {
	
	private Random rnd = new Random();
	
	/**
	 * Constructs a maze generator which uses the randomized Prim's algorithm
	 * @param width of the maze measured in number of nodes
	 * @param height of the maze measured in number of nodes
	 * @param drawer of type MazeDrawer, 
	 * 		  the class which handles the drawing of the maze.
	 * @param animate boolean, indicating whether the maze generation process 
	 * 		  should be animated.
	 * @param animation delay integer defining the length in milliseconds
	 * 		  for the delay each animation step. (i.e. each time the maze
	 * 		  is redrawn.)
	 * @param timer boolean, indicating whether the maze generation process
	 * 		  should be timed and printed to the console.
	 */
	public MazeGenPrim(int width, int height, MazeDrawer drawer,
					  boolean animate, int animationDelay) {
		super(width, height, drawer, animate, animationDelay);
		seed = rnd.nextLong();
		rnd.setSeed(seed);
	}
	
	/**
	 * Constructs a maze generator which uses the randomized Prim's algorithm
	 * @param width of the maze measured in number of nodes
	 * @param height of the maze measured in number of nodes
	 * @param drawer of type MazeDrawer, 
	 * 		  the class which handles the drawing of the maze.
	 * @param seed to generate a maze identical each time
	 * @param animate boolean, indicating whether the maze generation process 
	 * 		  should be animated.
	 * @param animation delay integer defining the length in milliseconds
	 * 		  for the delay each animation step. (i.e. each time the maze
	 * 		  is redrawn.)
	 * @param timer boolean, indicating whether the maze generation process
	 * 		  should be timed and printed to the console.
	 */
	public MazeGenPrim(int width, int height, MazeDrawer drawer,
					  long seed, boolean animate, int animationDelay) {
		this(width, height, drawer, animate, animationDelay);
		if (seed != 0) {
			rnd.setSeed(seed);
		}
	}
	
	/**
	 * Generates a maze using the randomized Prim's algorithm
	 */
	@Override
	public void generate() {
		super.generate();
		Node startNode = new Node(startX, startY);
		NodeCollection visited = new NodeCollection();
		visited.add(startNode);
		maze.setNodeValue(startNode, 0);
		mazeChanged();
		WallCollection walls = new WallCollection();
		for (Node neighbour : startNode.getNeighbours(maze.getWidth(), maze.getHeight())) {
			walls.add(new Wall(startNode, neighbour));
		}
		int randint;
		while (!walls.isEmpty()) {
			randint = rnd.nextInt(walls.size());
			Wall randomWall = walls.get(randint);
			Node nodeA = randomWall.getA();
			Node nodeB = randomWall.getB();
			// if only one of the nodes divided by the wall has been visited
			if (visited.contains(nodeA) && !visited.contains(nodeB)) {
				maze.removeWall(randomWall);
				visited.add(nodeB);
				maze.setNodeValue(nodeB, 0);
				mazeChanged();
				for (Node neighbour : nodeB.getNeighbours(maze.getWidth(), maze.getHeight())) {
					walls.add(new Wall(nodeB, neighbour));
				}
			} else if (!visited.contains(nodeA) && visited.contains(nodeB)) {
				maze.removeWall(randomWall);
				visited.add(nodeA);
				maze.setNodeValue(nodeB, 0);
				mazeChanged();
				for (Node neighbour : nodeA.getNeighbours(maze.getWidth(), maze.getHeight())) {
					walls.add(new Wall(nodeA, neighbour));
				}
			}
			walls.remove(randomWall);
		}
	}

}






























