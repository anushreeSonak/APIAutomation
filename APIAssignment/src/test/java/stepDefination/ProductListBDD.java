package stepDefination;

import APIAssignments.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;
import org.testng.Assert;

import static APIAssignments.BaseClass.url;
import static io.restassured.RestAssured.given;

public class ProductListBDD {
    private static final Logger logger = Logger.getLogger("ProductListBDD.class");
    private static String response;


    @Given("enter url and and get product list")
    public void enterUrl()  {
        response = String.valueOf(RestAssured.get(ConfigReader.getPropertyValue("baseURL")).then().extract().response());
        logger.info(response);
    }

    @When("validate status code for post product list")
    public void validateStatusCode() {
        var response = given().when().post(url).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var responseCode = jsonResponse.get("responseCode");
        logger.info("Response Code is: " + responseCode);
    }

    @Then("validate message for post product list")
    public void validateResponseMessage() {
        var response = given().when().post(url).then().extract().asString();
        JsonPath jsonResponse = new JsonPath(response);
        var responseMessage = jsonResponse.get("message");
        logger.info("Response Message is: " + responseMessage);
        Assert.assertEquals(responseMessage, "This request method is not supported.");
        }
    }