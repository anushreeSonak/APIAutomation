package APIAssignments;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static APIAssignments.BaseClass.url;
import static io.restassured.RestAssured.given;

public class APIAssignmentOne {
    private static Logger logger = Logger.getLogger("ProductList.class");

    @Test(priority = 1)
    public void setUp() {
        BaseClass mainclass = new BaseClass();
        mainclass.getLoggerDisplay();
    }

    @Test(priority = 2)
    public void validateStatusCode() throws IOException {
        url = ConfigReader.getPropertyValue("productId");
        Response response = RestAssured.get(ConfigReader.getPropertyValue("productId")).then().extract().response();
        logger.info("Status code is " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void validateProductList() {
        var getList = given().when().get(url).then().log().all().toString();
        logger.info("Product List is " + getList);
    }

    @Test(priority = 4)
    public void validateContent() {
        Response response = RestAssured.get(url).then().extract().response();
        JsonPath jsonObject = new JsonPath(response.asString());
        var size = jsonObject.getInt("products.size()");
        List<Product> productData = new ArrayList<>();
        Product baseClassObject = new Product("3", "Sleeveless Dress", "Rs. 1000", "Madame");
        productData.add(baseClassObject);
        baseClassObject = new Product("2", "Men Tshirt", "Rs. 400", "H&M");
        productData.add(baseClassObject);
        for (int index = 0; index < size; index++) {
            String id = jsonObject.getString("products[" + index + "].id");
            String name = jsonObject.getString("products[" + index + "].name");
            String price = jsonObject.getString("products[" + index + "].price");
            String brand = jsonObject.getString("products[" + index + "].brand");
            productData.forEach(product -> {
                if (name.equals(product.productName)) {
                    logger.info(product.productId);
                    logger.info(product.productName);
                    logger.info(product.productBrand);
                    logger.info(product.productPrice);
                    Assert.assertEquals(product.productId, id);
                    Assert.assertEquals(product.productName, name);
                    Assert.assertEquals(product.productPrice, price);
                    Assert.assertEquals(product.productBrand, brand);
                }
            });
        }
    }

    @Test(priority = 5)
    public void validateLength() {
        var response = given().when().get(url).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var idLength = jsonResponse.getInt("products.id.size()");
        logger.info("The length is : " + idLength + " products");
        Assert.assertEquals(idLength, 34, "number of products are Not expected");
    }
}
