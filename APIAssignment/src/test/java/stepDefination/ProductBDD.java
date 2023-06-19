package stepDefination;

import APIAssignments.BaseClass;
import APIAssignments.ConfigReader;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductBDD {
    private static final  Logger logger = Logger.getLogger("ProductBDD.class");
    private static Response response;
    private static JsonPath jsonObject;

    @Before
    public void setUp() throws IOException {
        BaseClass product = new BaseClass();
        product.getLoggerDisplay();
    }

    @Given("enter url and and get product list")
    public void enterUrl() throws IOException {
        response = RestAssured.get(ConfigReader.getPropertyValue("baseURL")).then().extract().response();
        logger.info(response);
    }

    @When("hit GET request get product list")
    public void getProductList() throws IOException {
        response = RestAssured.get(ConfigReader.getPropertyValue("baseURL")).then().extract().response();
        ResponseBody getList = response.getBody();
        logger.info("Response Body is: " + getList.asString());
        Assert.assertNotNull(getList);
    }

    @Then("validate status code for GET product list")
    public void validateStatusCode() throws IOException {
        logger.info("Status code is " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("validate content for GET product list")
    public void validateContentType() {
        jsonObject = new JsonPath(response.asString());
        var size = jsonObject.getInt("products.size()");
        List<BaseClass> productData = new ArrayList<>();
        var productId = ConfigReader.getPropertyValue("productId");
        var productName = ConfigReader.getPropertyValue("productName");
        var productPrice = ConfigReader.getPropertyValue("productPrice");
        var productBand = ConfigReader.getPropertyValue("productBrand");
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

    @And("validate length for GET product list")
    public void validateLength() {
        jsonObject = new JsonPath(response.asString());
        var idLength = jsonObject.getInt("products.id.size()");
        logger.info("The length is : " + idLength + " products");
        Assert.assertEquals(idLength, 34, "number of products are Not expected");
    }
}