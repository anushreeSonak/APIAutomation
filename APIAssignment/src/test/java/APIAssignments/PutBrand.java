package APIAssignments;

import io.restassured.path.json.JsonPath;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class PutBrand {
    private String url;

    public PutBrand() {
        try {
            url = ConfigReader.getUrl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Logger logger = Logger.getLogger("PutBrandList.class");

    @BeforeTest
    public void getLoggerDisplay() {
        PropertyConfigurator.configure("log4j2.properties");
    }

    @Test(priority = 1)
    public void getResponseCode() {
        var response = given().when().put(url).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var responseCode = jsonResponse.get("responseCode");
        logger.info("responseCode: " + responseCode);
        Assert.assertEquals(responseCode, 405);
    }

    @Test(priority = 2)
    public void getResponseMessage() {
        var response = given().when().put(url).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var responseMessage = jsonResponse.get("message");
        logger.info("Response Message is: " + responseMessage);
        Assert.assertEquals(responseMessage, "This request method is not supported.");
    }
}