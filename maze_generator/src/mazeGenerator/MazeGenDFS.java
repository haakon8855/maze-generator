package mazeGenerator;

import java.util.Random;
import java.util.Stack;

import datatypes.Node;
import datatypes.NodeCollection;

public class MazeGenDFS extends MazeGen {
	
	private Random rnd = new Random();
	
	/**
	 * Constructs a maze generator which uses the randomized DFS algorithm
	 * @param width of the maze measured in number of nodes
	 * @param height of the maze measured in number of nodes
	 */
	public MazeGenDFS(int width, int height, MazeDrawer drawer) {
		super(width, height, drawer);
	}
	
	/**
	 * Constructs a maze generator which uses the randomized DFS algorithm
	 * @param width of the maze measured in number of nodes
	 * @param height of the maze measured in number of nodes
	 * @param seed to generate a maze identical each time
	 */
	public MazeGenDFS(int width, int height, long seed, MazeDrawer drawer) {
		this(width, height, drawer);
		rnd.setSeed(seed);
	}

	/**
	 * Generates a maze with randomized-DFS
	 */
	@Override
	public void generate() {
		super.generate();
		int startX = maze.getWidth()/2;
		int startY = maze.getHeight()/2;
		Node startNode = new Node(startX, startY);
		NodeCollection visited = new NodeCollection();
		visited.add(startNode);
		maze.setNodeValue(startNode, 0);
		mazeChanged();
		Stack<Node> stack = new Stack<Node>();
		stack.push(startNode);
		Node currentNode, chosenNode;
		while (!stack.isEmpty()) {
			currentNode = stack.pop();
			NodeCollection neighbours = currentNode.getNeighbours(maze.getWidth(), maze.getHeight());
			NodeCollection unvisitedNeighbours = new NodeCollection();
			for (Node neighbour : neighbours) {
				if (!visited.contains(neighbour)) {
					unvisitedNeighbours.add(neighbour);
				}
			}
			if (unvisitedNeighbours.size() > 0) {
				stack.push(currentNode);
				int randint = rnd.nextInt(unvisitedNeighbours.size());
				chosenNode = unvisitedNeighbours.get(randint);
				maze.removeWall(currentNode, chosenNode);
				visited.add(chosenNode);
				maze.setNodeValue(chosenNode, 0);
				stack.push(chosenNode);
				mazeChanged();
			}
		}
	}

}




























