package program;

import mazeGenerator.MazeDrawer;
import mazeGenerator.MazeGen;
import mazeGenerator.MazeGenDFS;
import mazeGenerator.MazeGenPrim;

public class MazeGenerator {

	public static void main(String[] args) {
		MazeGeneratorReadConfig cfg = new MazeGeneratorReadConfig("config.ini");

		int width = Integer.parseInt(cfg.get("width"));
		int height = Integer.parseInt(cfg.get("height"));
		String mazeType = cfg.get("type").toLowerCase();
		MazeDrawer drawer = new MazeDrawer(width, height);
		MazeGen gen = null;
		switch (mazeType) {
			case "dfs":
				gen = new MazeGenDFS(width, height, drawer);
				break;
			case "prim":
				gen = new MazeGenPrim(width, height, drawer);
				break;
			default:
				System.out.println("Invalid maze type, check config file for guide");
				break;
		}
		if (gen != null) {
			gen.generate();
			drawer.updateMaze(gen.getMaze());
		}
	}

}
