package stepDefination;

import APIAssignments.BaseClass;
import APIAssignments.Product;
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

import java.util.ArrayList;
import java.util.List;

import static baseClass.BaseUrl.postEndUrl;
import static baseClass.BaseUrl.putBrandUrl;
import static io.restassured.RestAssured.given;

public class HttpMethodProduct {
    private static Logger logger = Logger.getLogger(HttpMethodProduct.class);
    private static Response response;
    private static JsonPath jsonObject;
    private String url;
    private String brandUrl;

    @Before
    public void setUp() {
        BaseClass baseclass  = new BaseClass();
        baseclass.getProductUrl();
        baseclass.getLoggerDisplay();
    }

    @Given("valid url to fetch products")
    public void fetchUrl() {
        url = postEndUrl();
        brandUrl = putBrandUrl();
        logger.info("fetching products url");
    }

    @When("hit GET request get product list")
    public void getProductList() {
        response = RestAssured.get(url).then().extract().response();
        ResponseBody getList = response.getBody();
        logger.info("Response Body is: " + getList.asString());
        Assert.assertNotNull(getList);
    }

    @Then("validate status code for GET product list")
    public void validateStatusCode() {
        logger.info("Status code is " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("validate content for GET product list")
    public void validateContentType() {
        jsonObject = new JsonPath(response.asString());
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

    @When("add product to productList")
    public void addProduct() {
        var response = given().when().post(url).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var responseCode = jsonResponse.get("responseCode");
        logger.info("Response Code is: " + responseCode);
    }

    @Then("the product should be POST in product list")
    public void validateProductList() {
        var response = given().when().post(url).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var responseMessage = jsonResponse.get("message");
        logger.info("Response Message is: " + responseMessage);
        Assert.assertEquals(responseMessage, "This request method is not supported.");
    }

    @When("put a brand from brand list")
    public void putBrandList() {
        var response = given().when().put(brandUrl).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var responseCode = jsonResponse.get("responseCode");
        logger.info("responseCode: " + responseCode);
    }

    @Then("the brand should be PUT in brand list")
    public void validatePutBrandList() {
        var response = given().when().put(brandUrl).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var responseMessage = jsonResponse.get("message");
        logger.info("Response Message is: " + responseMessage);
        Assert.assertEquals(responseMessage, "This request method is not supported.");
    }
}
