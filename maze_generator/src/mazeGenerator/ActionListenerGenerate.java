package mazeGenerator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;

import program.MazeGenerator;

public class ActionListenerGenerate implements ActionListener {
	
	private MazeDrawer drawer;
	private MazeGenerator generator;

	/**
	 * Creates an action listener for the generate-button and starts the 
	 * generation algorithm when this button is clicked
	 * @param drawer, the MazeDrawer class drawing the maze on the canvas
	 * @param generator, the main program class to call from the worker thread
	 */
	public ActionListenerGenerate(MazeDrawer drawer, MazeGenerator generator) {
		this.drawer = drawer;
		this.generator = generator;
	}

	/**
	 * Creates a SwingWorker to generate the maze in the worker thread and keep UI on the 
	 * EDT. This keeps the UI from freezing when the maze is being generated.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		SwingWorker<Integer, int[][]> mazeGenWorker = new SwingWorkerGenerate(drawer, generator);
		mazeGenWorker.execute();	// Start the maze generation
	}
	
}
