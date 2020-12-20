package mazeGenerator;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import datatypes.Maze;
import program.MazeGenerator;

public class SwingWorkerGenerate extends SwingWorker<Integer, Maze> {
	
	private MazeDrawer drawer;
	private MazeGenerator generator;
	private List<Maze> mazeStates = new ArrayList<Maze>();
	
	public SwingWorkerGenerate(MazeDrawer drawer, MazeGenerator generator) {
		super();
		this.drawer = drawer;
		this.generator = generator;
	}
	
	protected Integer doInBackground() throws Exception {
		generator.generate(this);
		return 0;
	}
	
	@Override
	protected void process(List<Maze> chunks) {
		mazeStates.addAll(chunks);
		Maze maze = chunks.get(chunks.size()-1);
		drawer.updateMaze(maze);
//		for (Maze maze : chunks) {
//			drawer.updateMaze(maze);
//		}
	}
	
	public void update(Maze maze) {
		publish(maze);
	}

}
