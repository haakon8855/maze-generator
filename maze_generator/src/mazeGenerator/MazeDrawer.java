package mazeGenerator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import datatypes.Maze;
import datatypes.Node;
import datatypes.Wall;
import program.MazeGenerator;

public class MazeDrawer {
	
	private int width, height;
	private int animationDelay;
	private JFrame frame;
	private JPanel container;
	private JPanel settingsPanel;
	private MazeDrawerCanvas canvas;
	private JButton btnGenerate;
	
	private MazeGenerator generator;
	
	/**
	 * Constructs a maze drawer which draws the maze of give width and height 
	 * @param widthInBlocks given in number of nodes/blocks
	 * @param heightInBlocks given in number of nodes/blocks
	 */
	public MazeDrawer(int widthInBlocks, int heightInBlocks, int animationDelay, 
														MazeGenerator generator) {
		// Set up main JFrame
		this.frame = new JFrame("Håkon's Maze Generator");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.animationDelay = animationDelay;
		this.generator = generator;
		
		// Calculate some needed values
		int blocksize = calculateBlocksize(widthInBlocks, heightInBlocks);
		
		this.width = calculateWidth(widthInBlocks, blocksize);	// width in pixels
		this.height = calculateHeight(heightInBlocks, blocksize); // height in pixels
		
		// Put window in the center of the screen
		centerScreen();
		
		// Main flowLayout-style-container for all other UI-elements
		this.container = new JPanel(new FlowLayout());
		
		// Initialize the canvas
		initCanvas(blocksize, blocksize);
		
		// Initialize the settings panel
		initSettingsPanel();

		// Finally, add the main container JPanel to the frame
		this.frame.add(this.container);
		this.frame.pack();
		this.frame.setResizable(false);
		this.frame.setVisible(true);

		// Initialize the necessary action listeners for the UI-elements
		this.addActionListeners();
	}
	
	/**
	 * Initializes the settings panel by setting its size, and adding its components
	 */
	private void initSettingsPanel() {
		this.settingsPanel = new JPanel(new BorderLayout());
		// temprorary size
		Dimension settingsDim = new Dimension(400, height);
		this.settingsPanel.setSize(settingsDim);
		this.settingsPanel.setPreferredSize(settingsDim);
		btnGenerate = new JButton("Generate!");
		this.settingsPanel.add(btnGenerate);
		
		this.container.add(settingsPanel);
	}
	
	/**
	 * Initializes the mazeCanvas with given sizes
	 * @param blocksize, an integer, the width in pixels of each corridor
	 * @param wallWidth, an integer, the width in pixels of each wall
	 */
	private void initCanvas(int blocksize, int wallWidth) {
		this.canvas = new MazeDrawerCanvas(blocksize, wallWidth);
		this.canvas.setSize(width, height);
		this.canvas.setPreferredSize(new Dimension(width, height));
		this.container.add(this.canvas);
	}
	
	/**
	 * Centers the window on the screen such that it is in roughly the same location each time
	 * regardless of the maze size.
	 */
	private void centerScreen() {
		// Dimensions of users screen
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		// sets location to center of screen
		this.frame.setLocation((screenWidth-this.width)/2, (screenHeight-this.height)/2);
	}
	
	/**
	 * Calculate the size in pixels for each block on the screen, i.e. corridor width/height
	 * @param widthInBlocks
	 * @param heightInBlocks
	 * @return blocksize, an integer
	 */
	private int calculateBlocksize(int widthInBlocks, int heightInBlocks) {
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		int actualWidthInBlocks = widthInBlocks*2-1 + 2;
		int actualHeightInBlocks = heightInBlocks*2-1 + 2;
		
		int preferredWidth = screenWidth*2/3;
		int preferredHeight = screenHeight*4/5;
		
		int blocksizeFromWidth = preferredWidth/actualWidthInBlocks;
		int blocksizeFromHeight = preferredHeight/actualHeightInBlocks;
		int blocksize = blocksizeFromHeight;
		if (blocksizeFromWidth*actualWidthInBlocks < preferredWidth &&
				blocksizeFromWidth < blocksizeFromHeight) {
			blocksize = blocksizeFromWidth;
		}
		return blocksize;
	}

	/**
	 * Calculate the width of the maze canvas in pixels
	 * @param widthInBlocks
	 * @param blocksize
	 * @return width, an integer
	 */
	private int calculateWidth(int widthInBlocks, int blocksize) {
		int actualWidthInBlocks = widthInBlocks*2-1 + 2;
		int width = actualWidthInBlocks*blocksize;	// width in pixels
		return width;
	}
	
	/**
	 * Calculate the height of the maze canvas in pixels
	 * @param heightInBlocks
	 * @param blocksize
	 * @return height, an integer
	 */
	private int calculateHeight(int heightInBlocks, int blocksize) {
		int actualHeightInBlocks = heightInBlocks*2-1 + 2;
		int height = actualHeightInBlocks*blocksize; // height in pixels
		return height;
	}
	
	/**
	 * Adds the appropriate action listeners for each UI element
	 */
	public void addActionListeners() {
		btnGenerate.addActionListener(new ActionListenerGenerate(this, generator));
	}

	/**
	 * Pauses execution for the delay duration 
	 * specified in the delayDuration variable
	 */
	public void delay() {
		try {
			Thread.sleep(animationDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generates a bitmap of pixels which represents the maze's corridors and walls.
	 * The numbers in the bitmap represent different characteristics of the maze.
	 * <ul>
	 *  <li>0: a corridor in the maze</li>
	 *  <li>1: a wall in the maze</li>
	 * </ul>
	 * @param maze instance with walls and nodes
	 * @return bitmap of type int[][]
	 */
	public static int[][] generateBitmap(Maze maze) {
		int width = maze.getWidth()*2 - 1;
		int height = maze.getHeight()*2 - 1;
		int[][] bitmap = new int[width][height];
		bitmap = addNodes(bitmap, maze.getNodesIterator());
		bitmap = addWalls(bitmap, maze.getWallsIterator());
		bitmap = addCornerWalls(bitmap);
		bitmap = addBorderWalls(bitmap);
		return bitmap;
	}

	/**
	 * Adds the nodes or 'rooms' in the maze with their given values.
	 * If a node is not present in the given iterable, it is given the value of 0
	 * @param bitmap of type int[][]
	 * @param nodes iterable with the nodes from the maze with specified values
	 * @return bitmap of type int[][] with the nodes as specified int the maze's node-collection
	 */
	public static int[][] addNodes(int[][] bitmap, Iterable<Node> nodes) {
		for (Node node : nodes) {
			int x = node.getX()*2;
			int y = node.getY()*2;
			bitmap[x][y] = node.getValue();
		}
		return bitmap;
	}
	
	/**
	 * Adds the inner walls to the maze. These walls are specified in the maze-instance's 
	 * walls-collection.
	 * @param bitmap of type int[][]
	 * @param walls iterable with the walls from the maze
	 * @return bitmap of type int[][] with the walls as specified in the maze's wall-collection
	 */
	private static int[][] addWalls(int[][] bitmap, Iterable<Wall> walls) {
		for (Wall wall : walls) {
			int x1 = wall.getA().getX()*2;
			int y1 = wall.getA().getY()*2;
			int x2 = wall.getB().getX()*2;
			int y2 = wall.getB().getY()*2;
			int wallX = (x1 + x2) / 2;
			int wallY = (y1 + y2) / 2;
			bitmap[wallX][wallY] = 1;
		}
		return bitmap;
	}
	
	/**
	 * Adds the corner walls in the maze
	 * @param bitmap of type int[][]
	 * @return bitmap of type int[][] with corner walls (1s) at every node with only odd coords.
	 */
	private static int[][] addCornerWalls(int[][] bitmap) {
		for (int y = 0; y < bitmap[0].length; y++) {
			for (int x = 0; x < bitmap.length; x++) {
				if (x % 2 != 0 && y % 2 != 0) {
					bitmap[x][y] = 1;
				}
			}
		}
		return bitmap;
	}
	
	/**
	 * Adds the outer walls to the maze
	 * @param bitmap of type int[][]
	 * @return bitmap of type int[][] with border walls (1s) at around the edges 
	 */
	private static int[][] addBorderWalls(int[][] bitmap) {
		int w = bitmap.length+2;
		int h = bitmap[0].length+2;
		int[][] outbitmap = new int[w][h];
		for (int y = 0; y < outbitmap[0].length; y++) {
			for (int x = 0; x < outbitmap.length; x++) {
				if ((y == 0 || y == outbitmap[0].length-1) || 
						(x == 0 || x == outbitmap.length-1)) {
					outbitmap[x][y] = 1;
				} else {
					outbitmap[x][y] = bitmap[x-1][y-1];
				}
			}
		}
		return outbitmap;
	}
	
	/**
	 * Gives a string representation of the maze.
	 * @param bitmap of type int[][] which contains the maze's walls, borders and nodes.
	 * @return string representation of the given bitmap, aka. maze.
	 */
	public static String bitmapToString(int[][] bitmap) {
		String outString = "";
		for (int y = 0; y < bitmap[0].length; y++) {
			for (int x = 0; x < bitmap.length; x++) {
				outString += bitmap[x][y] + " ";
			}
			outString += "\n";
		}
		return outString;
	}
	
	/**
	 * Updates the canvas with the given maze
	 * @param maze, instance of Maze.
	 */
	public void updateMaze(Maze maze) {
		int[][] bitmap = generateBitmap(maze);
		updateMaze(bitmap);
	}
	
	/**
	 * Updates the canvas with the given maze
	 * @param maze bitmap of type int[][].
	 */
	public void updateMaze(int[][] bitmap) {
		canvas.setBitmap(bitmap);
		canvas.repaint();
	}
	
}


















