package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    //Read Properties file from Config.properties file
    public static Properties getProperties(String productId) {
        String userDirectory = System.getProperty("user.dir");
        FileInputStream file = null;
        Properties properties = new Properties();
        try {
            file = new FileInputStream(userDirectory + "/config/config.properties");
            properties.load(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}