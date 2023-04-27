package APIAssignments;
public class ConfigReader
{
    public static Properties getProperties() throws IOException {
        FileInputStream file = new FileInputStream("");
        Properties properties = new Properties();
        properties.load(file);
        return properties;
    }


    public static String getUrl() throws IOException {
        return getProperties().getProperty("getUrl");
    }
}
 
}
