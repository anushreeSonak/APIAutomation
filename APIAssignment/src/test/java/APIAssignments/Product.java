package APIAssignments;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static io.restassured.RestAssured.*;

public class Product {
    private static String url;
    private String productId;
    private String productName;
    private String productPrice;
    private String productBrand;

    public Product() {
        try {
            url = ConfigReader.getUrl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Product(String productId, String productName, String productPrice, String productBrand) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBrand = productBrand;
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
        List<Product> productList = jsonObject.getList("products");
        List<Product> mockData = new ArrayList<>();
        Product proLists = new Product("1", "Blue Top", "Rs. 500", "Polo");
        mockData.add(proLists);
        proLists = new Product("2", "Men Tshirt", "Rs. 400", "H&M");
        mockData.add(proLists);

        for (int index = 0; index < productList.size(); index++) {
            for (Product mockProduct : mockData) {
                if (jsonObject.getString("products[" + index + "].name").equals(mockProduct.productName)) {
                    logger.info(mockProduct.productId);
                    logger.info(mockProduct.productName);
                    logger.info(mockProduct.productBrand);
                    logger.info(mockProduct.productPrice);
                    Assert.assertEquals(mockProduct.productId, jsonObject.getString("products[" + index + "].id"));
                    Assert.assertEquals(mockProduct.productName, jsonObject.getString("products[" + index + "].name"));
                    Assert.assertEquals(mockProduct.productPrice, jsonObject.getString("products[" + index + "].price"));
                    Assert.assertEquals(mockProduct.productBrand, jsonObject.getString("products[" + index + "].brand"));
                }
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