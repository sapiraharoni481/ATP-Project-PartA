
package Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton class for managing application configurations.
 * Reads configurations from a properties file.
 */
public class Configurations {

    private static Configurations instance = null;
    private final Properties properties;

    private Configurations() {
        properties = new Properties();
        try (InputStream input = new FileInputStream("./resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            // Use default values if file not found
            properties.setProperty("threadPoolSize", "5");
            properties.setProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
            properties.setProperty("mazeSearchingAlgorithm", "BestFirstSearch");
        }
    }

    public static Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }

    public int getThreadPoolSize() {
        return Integer.parseInt(properties.getProperty("threadPoolSize", "5"));
    }

    public String getMazeGeneratingAlgorithm() {
        return properties.getProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
    }

    public String getMazeSearchingAlgorithm() {
        return properties.getProperty("mazeSearchingAlgorithm", "BestFirstSearch");
    }

    // Method to update a configuration property
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}