package program;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class MazeGeneratorReadConfig {

	private String propFileName;
	private final Properties prop = new Properties();

	public MazeGeneratorReadConfig(String propFileName) {
		this.propFileName = propFileName;
		read();
	}

	public void update() {
		read();
	}

	public void read() {
		try {
			this.prop.clear(); 
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName); 

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("Property file " + propFileName + " not found in the classpath.");
			}
			inputStream.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	public String get(String key) {
		return this.prop.getProperty(key);
	}

	public static void main(String[] args) {
		MazeGeneratorReadConfig cfg = new MazeGeneratorReadConfig("config.ini");
		System.out.println(cfg.get("height"));
	}

}



























