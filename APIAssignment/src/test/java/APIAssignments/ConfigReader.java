package APIAssignments;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public static Properties getProperties() throws IOException {
        FileInputStream file = new FileInputStream("C:\\Users\\anushrees\\Desktop\\APIAutomation\\APIAutomation\\APIAssignment\\config.properties");
        Properties properties = new Properties();
        properties.load(file);
        return properties;
    }

    public static String getUrl() throws IOException {
        return getProperties().getProperty("baseURL");
    }
}