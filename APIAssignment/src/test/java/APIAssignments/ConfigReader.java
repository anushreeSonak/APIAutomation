package APIAssignments;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public static Properties getProperties() throws IOException {
        String userDirectory = System.getProperty("user.dir");
        FileInputStream file = new FileInputStream(userDirectory + "/config.properties");
        Properties properties = new Properties();
        properties.load(file);
        return properties;
    }

    public static String getUrl() throws IOException {
        return getProperties().getProperty("baseURL");

    }
    public static String getprice() throws IOException {
        return getProperties().getProperty("price");
    }

    public static String getid() throws IOException {
        return getProperties().getProperty("id");
    }

    public static String getbrand() throws IOException {
        return getProperties().getProperty("brand");
    }

    public static String getname() throws IOException {
        return getProperties().getProperty("name");
    }

}
