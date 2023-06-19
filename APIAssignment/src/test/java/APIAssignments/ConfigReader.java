package APIAssignments;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties propertiesObject;
    private static Logger logger = Logger.getLogger(ConfigReader.class);

    public static String getPropertyValue(String propertyName) {
        String configFilePath = System.getProperty("user.dir");
        Properties configProperties = ConfigReader.readPropertiesFile(configFilePath + "/config.properties");
        return configProperties.getProperty(propertyName);
    }

    public static Properties readPropertiesFile(String fileName) {
        try {
            FileInputStream fileStream = new FileInputStream(fileName);
            propertiesObject = new Properties();
            propertiesObject.load(fileStream);
        } catch (FileNotFoundException e) {
            logger.info("File not found exception occurred");
        } catch (IOException e) {
            logger.info("IO exception occurred");
        }
        return propertiesObject;
    }
}