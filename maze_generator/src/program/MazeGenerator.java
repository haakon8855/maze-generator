package program;

import mazeGenerator.MazeDrawer;
import mazeGenerator.MazeGen;
import mazeGenerator.MazeGenDFS;
import mazeGenerator.MazeGenPrim;
import mazeGenerator.SwingWorkerGenerate;

public class MazeGenerator {
	
	public static boolean DEBUG = false;
	
	int width, height, animationDelay;
	String mazeType;
	long seed;
	boolean timer, animate;
	
	MazeDrawer drawer;
	MazeGen gen;
	
	public void init(String config) {
		MazeGeneratorReadConfig cfg = new MazeGeneratorReadConfig(config);

		this.width = cfg.getPositiveInteger("width");
		this.height = cfg.getPositiveInteger("height");
		if (width < 5 || height < 5) {
			width = 5;
			height = 5;
		}
		if (height/width >= 3) {
			height = 2*width;
		}

		this.mazeType = cfg.get("type").toLowerCase();

		this.animate = cfg.getBoolean("animate");
		this.animationDelay = cfg.getPositiveInteger("animationDelay");

		this.timer = cfg.getBoolean("timer");
		
		this.seed = cfg.getLong("seed");

		this.drawer = new MazeDrawer(width, height, animationDelay, this);
		this.gen = null;
		switch (mazeType) {
			case "dfs":
				gen = new MazeGenDFS(width, height, drawer, seed,
									 animate, animationDelay);
				break;
			case "prim":
				gen = new MazeGenPrim(width, height, drawer, seed,
									  animate, animationDelay);
				break;
			default:
				System.out.println("Invalid maze type, check README.md for guide");
				break;
		}
	}
	
	public void generate(SwingWorkerGenerate worker) {
		if (gen != null) {
			long startTime = 0;
			long endTime = 0;
			long elapsed;
			if (timer) {
				startTime = System.currentTimeMillis();
			}
			gen.generate(worker);
			if (timer) {
				endTime = System.currentTimeMillis();
				elapsed = endTime - startTime;
				System.out.println("Time: " + elapsed + "ms");
			}
			drawer.updateMaze(gen.getMaze());
		}
	}

	public static void main(String[] args) {
		MazeGenerator generator = new MazeGenerator();
		generator.init("config.conf");
	}

}
