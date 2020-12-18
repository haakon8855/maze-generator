package mazeGenerator;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import datatypes.Maze;
import datatypes.Node;
import datatypes.Wall;

public class MazeDrawer {
	
	private int width, height;
	private JFrame frame;
	private JPanel container;
	private JPanel settingsPanel;
	private MazeDrawerCanvas canvas;
	
	/**
	 * Constructs a maze drawer which draws the maze of give width and height 
	 * @param widthInBlocks given in number of nodes/blocks
	 * @param heightInBlocks given in number of nodes/blocks
	 */
	public MazeDrawer(int widthInBlocks, int heightInBlocks) {
		this.frame = new JFrame("Håkon's Maze Generator");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		this.width = actualWidthInBlocks*blocksize;	// width in pixels
		this.height = actualHeightInBlocks*blocksize; // height in pixels
		
		// sets location to center of screen
		this.frame.setLocation((screenWidth-this.width)/2, (screenHeight-this.height)/2);
		this.container = new JPanel(new FlowLayout());
		
		this.canvas = new MazeDrawerCanvas(blocksize, blocksize);
		this.canvas.setSize(width, height);
		this.canvas.setPreferredSize(new Dimension(width, height));
		this.container.add(this.canvas);
		
		this.settingsPanel = new JPanel();
		// temprorary size
		Dimension settingsDim = new Dimension(400, height);
		this.settingsPanel.setSize(settingsDim);
		this.settingsPanel.setPreferredSize(settingsDim);
		this.container.add(settingsPanel);

		
		this.frame.add(this.container);
		this.frame.pack();
		this.frame.setResizable(false);
		this.frame.setVisible(true);
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
	public int[][] generateBitmap(Maze maze) {
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
	public int[][] addNodes(int[][] bitmap, Iterable<Node> nodes) {
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
	private int[][] addWalls(int[][] bitmap, Iterable<Wall> walls) {
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
	private int[][] addCornerWalls(int[][] bitmap) {
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
	private int[][] addBorderWalls(int[][] bitmap) {
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
	public String bitmapToString(int[][] bitmap) {
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
		canvas.setBitmap(bitmap);
//		Toolkit.getDefaultToolkit().sync();
		canvas.repaint();
	}
	
}


















