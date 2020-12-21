package mazeGenerator;

import java.util.List;

import javax.swing.SwingWorker;

import datatypes.Maze;
import program.MazeGenerator;

public class SwingWorkerGenerate extends SwingWorker<Integer, int[][]> {
	
	private MazeDrawer drawer;
	private MazeGenerator generator;
	
	/**
	 * Creates the SwingWorker for generating the maze using the generation algorithm
	 * given by the generator parameter.
	 * @param drawer, the MazeDrawer class drawing the maze in the UI
	 * @param generator, the main program class used for calling the appropriate generation 
	 * algorithm
	 */
	public SwingWorkerGenerate(MazeDrawer drawer, MazeGenerator generator) {
		super();
		this.drawer = drawer;
		this.generator = generator;
	}
	
	/**
	 * This method is run when this.execute() is called. It is run on the Worker-thread such 
	 * that it runs on a different thread than the UI itself.
	 */
	protected Integer doInBackground() throws Exception {
		generator.generate(this);
		return 0;
	}
	
	/**
	 * Runs whenever the publish method is called in order to update the UI when need.
	 * This will update the maze in the UI animating the progress of the generation algorithm.
	 */
	@Override
	protected void process(List<int[][]> chunks) {
		for (int[][] mazeBitmap : chunks) {
			drawer.updateMaze(mazeBitmap);
		}
	}
	
	/**
	 * Updates the maze such that animation will happen while maze is being generated.
	 * @param maze
	 */
	public void update(Maze maze) {
		publish(MazeDrawer.generateBitmap(maze));
	}

}
