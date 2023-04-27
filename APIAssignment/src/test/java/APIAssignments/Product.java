package APIAssignments;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class Product {

    final static String url = "https://automationexercise.com/api/productsList";

    public static void main(String args[]) {
        getResponseBody();
        getResponseStatus();
    }

    public static void getResponseBody() {
        // Send a GET request to the API endpoint and log the response body to the console
        given()
                .when()
                .get(url)
                .then()
                .log()
                .all();

        // Send another GET request with query parameters and log the response body to the console
        given()
                .queryParam("ID", "8")
                .queryParam("PRODUCT", "GRAPHIC DESIGN MEN T SHIRT - BLUE")
                .queryParam("BRAND", "Mast & Harbour")
                .when()
                .get(url)
                .then()
                .log()
                .body();
    }

    public static void getResponseStatus() {
        // Send a GET request with query parameters to the API endpoint and get the response
        Response response = given()
                .queryParam("ID", "43")
                .queryParam("PRODUCT", "GRAPHIC DESIGN MEN T SHIRT - BLUE")
                .queryParam("BRAND", "Mast & Harbour")
                .when()
                .get(url);

        // Get the status code of the response and print it to the console
        int statusCode = response.getStatusCode();
        System.out.println("The response status is " + statusCode);

        // Assert that the status code is 200
        Assert.assertEquals("Status code is not 200", 200, statusCode);

        // Get the list of products from the response using JSONPath and assert that it is not null
        ArrayList<String> products = response.jsonPath().get("products");
        Assert.assertNotNull("Products list is null", products);

        // Iterate through the list of products and assert that each product has a non-null ID, product name, and brand
        for (String product : products) {
            String id = response.jsonPath().get("id");
            Assert.assertNotNull("ID is null", id);

            String productName = response.jsonPath().get("product");
            Assert.assertNotNull("Product name is null", productName);

            String brand = response.jsonPath().get("brand");
            Assert.assertNotNull("Brand is null", brand);
        }
    }
}
