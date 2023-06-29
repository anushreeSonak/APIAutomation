package APIAssignments;

import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeTest;

import static baseClass.BaseUrl.postEndUrl;

public class BaseClass {

    public String productId;
    public String productName;
    public String productPrice;
    public String productBrand;
    public static String url;


    public BaseClass(){

    }

    public void getProductUrl() {
        url= postEndUrl();
    }

    public BaseClass(String productId, String productName, String productPrice, String productBrand) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBrand = productBrand;
    }

    @BeforeTest
    public void getLoggerDisplay() {
        String userDirectory = System.getProperty("user.dir");
        PropertyConfigurator.configure(userDirectory+ "log4j2.properties");
    }
}