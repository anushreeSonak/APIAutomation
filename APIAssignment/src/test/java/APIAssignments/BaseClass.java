package APIAssignments;

import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeTest;

public class BaseClass {

    public static String url;
    public String productId;
    public String productName;
    public String productPrice;
    public String productBrand;

    public BaseClass() {
        url = ConfigReader.getPropertyValue("baseURL");
    }

    public BaseClass(String productId, String productName, String productPrice, String productBrand) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBrand = productBrand;
    }

    @BeforeTest
    public void getLoggerDisplay() {
        PropertyConfigurator.configure("log4j2.properties");
    }
}