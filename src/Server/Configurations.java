package Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton class for managing application configurations.
 * Reads configurations from a properties file and provides access to various
 * configuration parameters for the maze application including thread pool size,
 * maze generation algorithm, and maze searching algorithm.
 *
 * The configuration file is expected to be located at "./resources/config.properties".
 * If the file is not found, default values are used for all properties.
 *
 * @author Sapir Aharoni
 * @version 1.0
 */
public class Configurations {

    private static Configurations instance = null;
    private final Properties properties;

    /**
     * Private constructor to enforce singleton pattern.
     * Loads configuration properties from the config file or sets default values
     * if the file cannot be read.
     *
     * Default values:
     * - threadPoolSize: 5
     * - mazeGeneratingAlgorithm: "MyMazeGenerator"
     * - mazeSearchingAlgorithm: "BestFirstSearch"
     */
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

    /**
     * Returns the singleton instance of the Configurations class.
     * Creates a new instance if one doesn't exist.
     *
     * @return the singleton instance of Configurations
     */
    public static Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }

    /**
     * Gets the thread pool size configuration value.
     *
     * @return the thread pool size as an integer, defaults to 5 if not specified
     */
    public int getThreadPoolSize() {
        return Integer.parseInt(properties.getProperty("threadPoolSize", "5"));
    }

    /**
     * Gets the maze generating algorithm configuration value.
     *
     * @return the name of the maze generating algorithm, defaults to "MyMazeGenerator" if not specified
     */
    public String getMazeGeneratingAlgorithm() {
        return properties.getProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
    }

    /**
     * Gets the maze searching algorithm configuration value.
     *
     * @return the name of the maze searching algorithm, defaults to "BestFirstSearch" if not specified
     */
    public String getMazeSearchingAlgorithm() {
        return properties.getProperty("mazeSearchingAlgorithm", "BestFirstSearch");
    }

    /**
     * Updates or adds a configuration property.
     * This method allows runtime modification of configuration values.
     *
     * @param key the property key to set
     * @param value the property value to set
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}