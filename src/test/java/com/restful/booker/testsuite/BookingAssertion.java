package com.restful.booker.testsuite;

import com.restful.booker.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BookingAssertion extends TestBase {
    static ValidatableResponse response;
    public BookingAssertion() {
        response = given()
                .when()
                .get()
                .then().log().body().statusCode(200);
    }
    @Test
    public void test001(){
        response.body("size()",equalTo(2724));
    }
}
