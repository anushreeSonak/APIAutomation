package APIAssignments;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Logger;

import static io.restassured.RestAssured.*;

//import static io.restassured.RestAssured.baseURI;
//import static io.restassured.RestAssured.given;

public class Product {
    private static String url;
    private String productId;
    private String productName;
    private String productPrice;
    private String productBrand;


    public Product() {
        {
            try {
                url = ConfigReader.getUrl();
                productId = ConfigReader.getid();
                productName = ConfigReader.getname();
                productPrice = ConfigReader.getprice();
                productBrand = ConfigReader.getbrand();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Logger logger = Logger.getLogger("Product.class");

    @BeforeTest
    public void getLoggerDisplay() {
        PropertyConfigurator.configure("log4j2.properties");
    }

    @Test(priority = 1)
    public void validateStatusCode() {
        {
            baseURI = url;
            Response response = RestAssured.get(url).then().extract().response();
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Status code is " + response.getStatusCode());
        }
    }

    @Test(priority = 2)
    public void validateProductList() {
        var getList = given().when().get(url).then().log().all().toString();
        logger.info("Product List is " + getList);
    }

    @Test(priority = 3)
    public void validateContent() {
        Response response = RestAssured.get(url).then().extract().response();
        JsonPath jsonObject = new JsonPath(response.asString());
        int size = jsonObject.getInt("products.size()");
        for (int index = 0; index < size; index++) {
            String id = jsonObject.getString("products[" + index + "].id");
            String name = jsonObject.getString("products[" + index + "].name");
            String price = jsonObject.getString("products[" + index + "].price");
            String brand = jsonObject.getString("products[" + index + "].brand");
            if (name.equals(productName)) {
                logger.info(productId);
                logger.info(productName);
                logger.info(productBrand);
                logger.info(productPrice);
                Assert.assertEquals(id, productId);
                Assert.assertEquals(name, productName);
                Assert.assertEquals(price, productPrice);
                Assert.assertEquals(brand, productBrand);
                break;
            }
        }
    }

    @Test(priority = 4)
    public void validateLength() {
        var response = given().when().get(url).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var idLength = jsonResponse.getInt("products.id.size()");
        logger.info("The length is : " + idLength + " products");
        Assert.assertEquals(idLength, 34, "number of products are Not expected");
    }
}