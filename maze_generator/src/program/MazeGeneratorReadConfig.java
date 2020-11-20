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
			ClassLoader loader = getClass().getClassLoader();
			InputStream inputStream = loader.getResourceAsStream(propFileName); 

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException(
					"Property file " + propFileName
					+ " not found in the classpath."
				);
			}
			inputStream.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	public String get(String key) {
		return this.prop.getProperty(key);
	}
	
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(this.get(key));
	}
	
	public long getLong(String key) {
		long value = 0;
		try {
			value = Long.parseLong(this.get(key));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		if (value < 0) {
			return 0;
		}
		return value;
	}
	
	public int getPositiveInteger(String key) {
		int value = 0;
		try {
			value = Integer.parseInt(this.get(key));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		if (value < 0) {
			return 0;
		}
		return value;
	}

}



























