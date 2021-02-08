package program;

public enum MazeType {
	DFS,
	PRIM {
		public String toString() {
			return "Prim";
		}
	};
	
	public static MazeType parseString(String type) {
		type = type.toUpperCase();
		MazeType mazeType = MazeType.DFS;
		try {
			mazeType = MazeType.valueOf(type);
		} catch (IllegalArgumentException e) {
			System.err.println("Illegal maze type!");
			e.printStackTrace();
		}
		return mazeType;
	}
}