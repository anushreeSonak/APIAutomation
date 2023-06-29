package baseClass;

import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeTest;

import static baseClass.EndPoint.postUrl;
import static baseClass.EndPoint.putUrl;
import static java.lang.System.getProperties;

public class BaseUrl {
    //This is Base url read from properties file return the base url
    public static String productBaseUrl() {
        String commonUrl = getProperties().getProperty("baseURI");
        return commonUrl;
    }

    public static String postEndUrl() {
        String getFinalUrl = productBaseUrl() + postUrl();
        return getFinalUrl;
    }

    public static String putBrandUrl() {
        String finalUrl = productBaseUrl() + putUrl();
        return finalUrl;
    }
    @BeforeTest
    public void getLoggerDisplay() {
        String userDirectory = System.getProperty("user.dir");
        PropertyConfigurator.configure(userDirectory+ "log4j2.properties");
    }
}