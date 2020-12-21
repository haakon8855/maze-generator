package mazeGenerator;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MazeDrawerCanvas extends JPanel { 

	public static final Color wallColor = Color.BLACK;
	private static final long serialVersionUID = 1L;
	private int[][] bitmap;
	private int blocksize = 5;
	private int wallWidth = blocksize;
	
	/**
	 * Constructs an instance of MazeDrawerCanvas which draws the maze
	 */
	public MazeDrawerCanvas() {
		setBackground(Color.LIGHT_GRAY);
	}

	/**
	 * Constructs an instance of MazeDrawerCanvas which draws the maze
	 * @param blocksize int indicating the width and height of a room or a hallway.
	 * @param wallWidth int indicating the width of the walls.
	 */
	public MazeDrawerCanvas(int blocksize, int wallWidth) {
		this();
		this.blocksize = blocksize;
		this.wallWidth = wallWidth;
	}
	
	/**
	 * Fetches the blocksize and returns it.
	 * @return blocksize int indicating the width and height of a room or a hallway.
	 */
	public int getBlocksize() {
		return blocksize;
	}
	
	/**
	 * Fetches the wallwidth and returns it.
	 * @return wallwidth int indicating the width of the walls.
	 */
	public int getWallWidth() {
		return wallWidth;
	}
	
	/**
	 * Sets the bitmap which is to be drawn by the maze.
	 * @param bitmap of type int[][] representing the maze.
	 */
	public void setBitmap(int[][] bitmap) {
		this.bitmap = bitmap;
	}
	
	/**
	 * Paints the maze by calling paintMaze. This method runs every time repaint is called.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintMaze(g);
	}
	
	/**
	 * Paints the maze according to the bitmap that is set
	 * @param Graphics g given by the paintComponent method which calls this method.
	 */
	public void paintMaze(Graphics g) {
		if (this.bitmap != null) {
			g.setColor(Color.black);
			for (int y = 0; y < bitmap[0].length; y++) {
				for (int x = 0; x < bitmap.length; x++) {
					if (bitmap[x][y] == 1) {
						int xPix = x * blocksize;
						int yPix = y * blocksize;
						g.fillRect(xPix, yPix, blocksize, blocksize);
					}
				}
			}
		}
	}

}


























