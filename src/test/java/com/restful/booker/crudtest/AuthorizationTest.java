package com.restful.booker.crudtest;

import com.restful.booker.model.AuthorizationPojo;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AuthorizationTest {
    public static String token;
    @Test
    public void test001(){
        AuthorizationPojo authorizationPojo = new AuthorizationPojo();
        authorizationPojo.setUsername("admin");
        authorizationPojo.setPassword("password123");

        ValidatableResponse response = given()
                .header("Content-Type", "application/json")
                .when()
                .body(authorizationPojo)
                .post("https://restful-booker.herokuapp.com/auth")
                .then().log().body().statusCode(200);
        token = response.extract().path("token");
    }
}
