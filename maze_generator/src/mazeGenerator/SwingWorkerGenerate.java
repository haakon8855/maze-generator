package mazeGenerator;

import java.util.List;

import javax.swing.SwingWorker;

import datatypes.Maze;
import program.MazeGenerator;

public class SwingWorkerGenerate extends SwingWorker<Integer, int[][]> {
	
	private MazeDrawer drawer;
	private MazeGenerator generator;
	
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
	protected void process(List<int[][]> chunks) {
		for (int[][] mazeBitmap : chunks) {
			drawer.updateMaze(mazeBitmap, true);
		}
	}
	
	public void update(Maze maze) {
		publish(MazeDrawer.generateBitmap(maze));
	}

}
