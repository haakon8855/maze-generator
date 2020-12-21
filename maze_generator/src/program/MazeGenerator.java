package program;

import mazeGenerator.MazeDrawer;
import mazeGenerator.MazeGen;
import mazeGenerator.MazeGenDFS;
import mazeGenerator.MazeGenPrim;
import mazeGenerator.SwingWorkerGenerate;

public class MazeGenerator {
	
	public static boolean DEBUG = true;
	
	private int width, height, animationDelay;
	private String mazeType;
	private long seed;
	private boolean timer, animate;
	
	private MazeDrawer drawer;
	private MazeGen gen;
	
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
	}
	
	public String getMazeType() {
		return mazeType;
	}
	
	public void setMazeType(String mazeType) {
		this.mazeType = mazeType;
	}

	/**
	 * Creates a new maze generator corresponding to the currently selected maze type
	 * @return A MazeGen instance
	 */
	private MazeGen makeNewGenerator() {
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
		return gen;
	}
	
	/**
	 * Generates the maze itself, can be called multiple times to create new.
	 * @param worker of SwingWorker type to run maze generation on separate thread.
	 */
	public void generate(SwingWorkerGenerate worker) {
		makeNewGenerator();
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
