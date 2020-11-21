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

	/**
	 * Fetches a single value from the config and returns it as a string.
	 * @param key, indicating which value to fetch from the config.
	 * @return a string holding the value at 'key' in the config.
	 */
	public String get(String key) {
		return this.prop.getProperty(key);
	}
	
	/**
	 * Fetches the value from the config file, and treating it as a boolean.
	 * @param key, indicating which variable in the config file to fetch.
	 * @return boolean value of key
	 */
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(this.get(key));
	}
	
	/**
	 * Fetches the value from the config file, and treating it as a long.
	 * If the integer value is negative, 0 is returned.
	 * @param key, indicating which variable in the config file to fetch.
	 * @return A positive integer of type long.
	 */
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
	
	/**
	 * Fetches the key from the config file, and treating it as an integer.
	 * If the integer value is negative, 0 is returned.
	 * @param key, indicating which variable in the config file to fetch.
	 * @return A positive integer.
	 */
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



























