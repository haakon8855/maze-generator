package mazeGenerator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import program.MazeGenerator;

public class ActionListenerGenerate implements ActionListener {
	
	private MazeDrawer drawer;
	private MazeGenerator generator;
	private SwingWorkerGenerate worker;

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
	public void actionPerformed(ActionEvent e) {
		String command = ((JButton) e.getSource()).getActionCommand();
		if (command.equals("start")) {
			drawer.submitDimensions();
			drawer.submitSeed();
			this.worker = new SwingWorkerGenerate(drawer, generator);
			this.worker.execute();	// Start the maze generation
		} else if (command.equals("stop")) {
			if (this.worker != null) {
				this.worker.cancel(true);
				this.worker = null;
			}
			drawer.activateGenerationBtn();
		}
	}
	
}
