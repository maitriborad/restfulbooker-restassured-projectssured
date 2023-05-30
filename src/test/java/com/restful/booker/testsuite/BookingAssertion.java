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
                .get("/booking/295")
                .then().statusCode(200);
    }
    //1. Verify first name
    @Test
    public void test001() {
        response.body("firstname", equalTo("James"));
    }

    //2. Verify last name
    @Test
    public void test002() {
        response.body("lastname", equalTo("Brown"));
    }
    //3. Verify totalprice is 111
    @Test
    public void test003() {
        response.body("totalprice", equalTo(111));
    }
    //4. Verify depositpaid is true
    @Test
    public void test004() {
        response.body("depositpaid", equalTo(true));
    }
    //5. verify "checkin": "2018-01-01",
    @Test
    public void test005() {
        response.body("bookingdates.checkin", equalTo("2018-01-01"));
    }
    //6. verify "checkout": "2019-01-01",
    @Test
    public void test006() {
        response.body("bookingdates.checkout", equalTo("2019-01-01"));
    }
    //7. verify additionalneeds
    @Test
    public void test007() {
        response.body("additionalneeds", equalTo("Lunch"));
    }
}
