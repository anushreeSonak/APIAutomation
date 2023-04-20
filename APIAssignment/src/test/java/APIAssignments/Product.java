package APIAssignments;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Product {
    @BeforeTest
    public void getURL() {
        baseURI = "https://automationexercise.com/api/productsList";
    }

    @Test
    public void validateProductDetails() {
        String getList = given().when().get(baseURI).then().log().all().toString();
        System.out.println(getList);
        var getStatusCode = given().when().get(baseURI).then().assertThat().log().all().statusCode(200);
        System.out.println(getStatusCode);
    }

    public static void main(String argu[]) {
        Product product = new Product();
        product.validateProductDetails();
    }
}