package mazeGenerator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;

import datatypes.Maze;
import program.MazeGenerator;

public class ActionListenerGenerate implements ActionListener {
	
	MazeDrawer drawer;
	MazeGenerator generator;
	
	public ActionListenerGenerate(MazeDrawer drawer, MazeGenerator generator) {
		this.drawer = drawer;
		this.generator = generator;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		SwingWorker<Integer, Maze> mazeGenWorker = new SwingWorkerGenerate(drawer, generator);
		mazeGenWorker.execute();
	}

}
