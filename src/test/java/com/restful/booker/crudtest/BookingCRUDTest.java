package com.restful.booker.crudtest;

import com.restful.booker.model.AuthorizationPojo;
import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class BookingCRUDTest extends TestBase {
    static String firstname="Maitri"+ TestUtils.getRandomValue();
    static String lastname="Borad"+TestUtils.getRandomValue();
    static int totalprice=200;
    static boolean depositpaid=true;
    static String additionalneeds="Breakfast";
    static String id;
    public static String token;
    public static String username="admin";
    public static String password="password123";
    @Test
    public void test001(){
        AuthorizationPojo authorizationPojo = new AuthorizationPojo();
        authorizationPojo.setUsername(username);
        authorizationPojo.setPassword(password);

        ValidatableResponse response = given()
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .when()
                .body(authorizationPojo)
                .post("/auth")
                .then().log().body().statusCode(200);
        token = response.extract().path("token");
    }
    @Test
    public void test002() {
        BookingPojo.BookingDates date = new BookingPojo.BookingDates();
        date.setCheckIn("2023-07-01");
        date.setCheckout("2023-07-06");

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(date);
        bookingPojo.setAdditionalneeds(additionalneeds);

        ValidatableResponse response = given()
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .header("Cookie", "token="+token)
                .auth().preemptive().basic("username", "password")
                .when()
                .body(bookingPojo)
                .post("/booking")
                .then().log().body().statusCode(200);
        id=response.extract().path("bookingid");
    }
    @Test
    public void test003(){
        String bId=given()
                .header("Connection", "keep-alive")
                .pathParams("id",id)
                .when()
                .get("/booking/{id}")
                .then().statusCode(200)
                .extract()
                .path("bookingid");
        Assert.assertEquals(bId,id);
    }
    @Test
    public void test004(){
        BookingPojo.BookingDates date = new BookingPojo.BookingDates();
        date.setCheckIn("2023-07-03");
        date.setCheckout("2023-07-09");

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname("John");
        bookingPojo.setLastname("woods");
        bookingPojo.setTotalprice(205);
        bookingPojo.setDepositpaid(true);
        bookingPojo.setBookingdates(date);
        bookingPojo.setAdditionalneeds("Lunch");

        Response response= given()
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .header("Cookie", "token="+token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .pathParams("id", id)
                .when()
                .body(bookingPojo)
                .put("/booking/{id}");
        response.then().log().body().statusCode(200);
    }
    @Test
    public void test005(){
        given()
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .header("Cookie", "token="+token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .pathParam("id", id)
                .when()
                .delete("/booking/{id}")
                .then()
                .statusCode(201);

        given()
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .header("Cookie", "token="+token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .pathParam("id", id)
                .when()
                .get("/booking/{id}")
                .then().statusCode(404);
    }
}
