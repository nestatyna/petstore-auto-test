package base.controllers;

import dto.StoreInventory;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static base.CustomLogger.log;

public class StoreController extends AbstractController {

    public static StoreInventory getStoreInventory() {
        Response response = isSuccess(getStoreInventoryRequest(), 200);
        return response.as(StoreInventory.class);
    }

    public static Response getStoreInventoryRequest() {
        log("<<<<<< getStoreInventoryRequest()\n");
        return RestAssured.given()
                .log().method()
                .log().uri()
                .log().headers()
                .when()
                .get("/store/inventory")
                .then()
                .extract().response();
    }
}
