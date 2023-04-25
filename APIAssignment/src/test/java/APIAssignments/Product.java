package APIAssignments;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Product {
    private static String url;

    {
        try {
            url = ConfigReader.getUrl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Logger logger = Logger.getLogger("Product.class");

    @Test(priority = 1)
    public void validateProductList() {
        baseURI = url;
        var getALLProductList = given().when().get(baseURI).then().log().all().toString();
        logger.log(Level.INFO, "List return by API is " + getALLProductList);
    }

    @Test(priority = 2)
    public void validateStatusCode() {
        baseURI = url;
        logger.info("getAllProductsList baseURI:" + baseURI);
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET);
        var getStatusCode = response.getStatusCode();
        logger.log(Level.INFO, "Status Code is : " + getStatusCode);
        Assert.assertEquals(getStatusCode, 200);
    }

    @Test(priority = 3)
    public void validateContent() {
        Response response = given()
                .contentType(ContentType.JSON).when().get(url);
        Assert.assertEquals(response.getStatusCode(), 200);
        var productName = response.jsonPath().get("products[5].name").toString();
        Assert.assertEquals(productName, " Summer White Top");
        logger.log(Level.INFO, "Product name=" + productName);
    }

    @Test(priority = 4)
    public void validateLength() {
        var response = given().when().get(url).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var idLength = jsonResponse.getInt("products.id.size()");
        Assert.assertEquals(idLength, 34, "number of products are not expected");
        logger.log(Level.INFO, "The length is : " + idLength + " products");
    }
}