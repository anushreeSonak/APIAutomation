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
        var getStatusLine = response.getStatusLine();
        Assert.assertEquals(getStatusLine, "HTTP/1.1 200 OK");
        logger.log(Level.INFO, "Status Code is : " + getStatusLine);

        logger.log(Level.INFO, "Status Code is : " + getStatusCode);
        Assert.assertEquals(getStatusCode, 200);
    }

    @Test(priority = 3)
    public void validateContent() {
        Response response = given().contentType(ContentType.JSON).when().get(url);
        JsonPath jsonObject = new JsonPath(response.asString());
        int size = jsonObject.getInt("products.size()");
        for (int index = 0; index < size; index++) {
            String id = jsonObject.getString("products[" + index + "].id");
            String name = jsonObject.getString("products[" + index + "].name");
            String price = jsonObject.getString("products[" + index + "].price");
            String brand = jsonObject.getString("products[" + index + "].brand");
            if (name.contains("Blue Top")) {
                Assert.assertEquals(id, "1");
                Assert.assertEquals(name, "Blue Top");
                Assert.assertEquals(price, "Rs. 500");
                Assert.assertEquals(brand, "Polo");
                logger.info(" Product of Id is :" + id);
                logger.info(" Product of Name is :" + name);
                logger.info(" Product of Price is :" + price);
                logger.info(" Product of brand is :" + brand);
            }
        }
    }

    @Test(priority = 4)
    public void validateLength() {
        var response = given().when().get(url).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var idLength = jsonResponse.getInt("products.id.size()");
        Assert.assertEquals(idLength, 34, "number of products are Not expected");
        logger.log(Level.INFO, "The length is : " + idLength + " products");
    }
}