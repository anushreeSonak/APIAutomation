package APIAssignments;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class ProductList {
    private static String url;

    {
        try {
            url = ConfigReader.getUrl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Logger logger = Logger.getLogger("ProductList.class");

    @Test
    public void validateStatusCode() {
        Response response = given().queryParam("Sort", "name")
                .when().post(url).then().log().body().statusCode(200).extract().response();
        String value = response.asString();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("Status code of " + url + " is " + response.getStatusCode());
        Assert.assertEquals(value.contains("This request method is not supported."), true, "Values does not contains \"This request method is not supported.\"");
        Assert.assertEquals(value.contains("\"responseCode\": 405"), true, "Values does not contains \"responseCode\": 405");
        logger.info("Values contains \"responseCode\": 405");
    }
}