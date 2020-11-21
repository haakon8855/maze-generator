package program;

import mazeGenerator.MazeDrawer;
import mazeGenerator.MazeGen;
import mazeGenerator.MazeGenDFS;
import mazeGenerator.MazeGenPrim;

public class MazeGenerator {
	
	public static boolean DEBUG = true;
	
	public static void run(String config) {
		MazeGeneratorReadConfig cfg = new MazeGeneratorReadConfig(config);

		int width = cfg.getPositiveInteger("width");
		int height = cfg.getPositiveInteger("height");
		if (width < 5 || height < 5) {
			width = 5;
			height = 5;
		}
		if (height/width >= 3) {
			height = 2*width;
		}

		String mazeType = cfg.get("type").toLowerCase();

		boolean animate = cfg.getBoolean("animate");
		int animationDelay = cfg.getPositiveInteger("animationDelay");

		boolean timer = cfg.getBoolean("timer");
		
		long seed = cfg.getLong("seed");

		MazeDrawer drawer = new MazeDrawer(width, height);
		MazeGen gen = null;
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
		if (gen != null) {
			long startTime = 0;
			long endTime = 0;
			long elapsed;
			if (timer) {
				startTime = System.currentTimeMillis();
			}
			gen.generate();
			if (timer) {
				endTime = System.currentTimeMillis();
				elapsed = endTime - startTime;
				System.out.println("Time: " + elapsed + "ms");
			}
			drawer.updateMaze(gen.getMaze());
		}
	}

	public static void main(String[] args) {
		MazeGenerator.run("config.conf");
	}

}
