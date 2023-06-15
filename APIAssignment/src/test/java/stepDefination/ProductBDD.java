package stepDefination;

import APIAssignments.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class ProductBDD {
    private static String url;
    private String productId;
    private String productName;
    private String productPrice;
    private String productBrand;

    private static Logger logger = Logger.getLogger("ProductBDD.class");

    public ProductBDD() {
        try {
            url = ConfigReader.getUrl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ProductBDD(String productId, String productName, String productPrice, String productBrand) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBrand = productBrand;
    }


    @Given("valid page number is available")
    public void validatePageNumber() {
        PropertyConfigurator.configure("log4j2.properties");
    }

    @When("send request to get ProductList")
    public void getList() {
        var getList = given().when().get(url).then().log().all().toString();
        logger.info("Product List is " + getList);
    }

    @Then("validate status code")
    public void validateStatusCode() throws IOException {
        url = ConfigReader.getUrl();
        Response response = RestAssured.get(ConfigReader.getUrl()).then().extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("Status code is " + response.getStatusCode());
    }

    @Then("validate content")
    public void validateContent() {
        Response response = RestAssured.get(url).then().extract().response();
        JsonPath jsonObject = new JsonPath(response.asString());
        List<Object> productList = jsonObject.getList("products");
        List<ProductBDD> mockData = new ArrayList<>();
        ProductBDD productLists = new ProductBDD("1", "Blue Top", "Rs. 500", "Polo");
        mockData.add(productLists);
        productLists = new ProductBDD("2", "Men Tshirt", "Rs. 400", "H&M");
        for (int index = 0; index < productList.size(); index++) {
            mockData.add(productLists);
            String id = jsonObject.getString("products[" + index + "].id");
            String name = jsonObject.getString("products[" + index + "].name");
            String price = jsonObject.getString("products[" + index + "].price");
            String brand = jsonObject.getString("products[" + index + "].brand");
            mockData.forEach(mockProduct -> {
                if (name.equals(mockProduct.productName)) {
                    logger.info(mockProduct.productId);
                    logger.info(mockProduct.productName);
                    logger.info(mockProduct.productBrand);
                    logger.info(mockProduct.productPrice);
                    Assert.assertEquals(mockProduct.productId, id);
                    Assert.assertEquals(mockProduct.productName, name);
                    Assert.assertEquals(mockProduct.productPrice, price);
                    Assert.assertEquals(mockProduct.productBrand, brand);
                }
            });
        }
    }

    @And("validate length")
    public void validateLength() {
        var response = given().when().get(url).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var idLength = jsonResponse.getInt("products.id.size()");
        logger.info("The length is : " + idLength + " products");
        Assert.assertEquals(idLength, 34, "number of products are Not expected");
    }
}