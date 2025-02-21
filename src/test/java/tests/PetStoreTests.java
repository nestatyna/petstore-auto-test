package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class PetStoreTests {

    private static final int PET_ID = 12345;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    @Description("Запрос питомца по Id")
    public void testGetPetById() {
        Response response = RestAssured.given()
                .when()
                .get("/pet/1")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @Description("Создание питомца")
    public void testCreatePet() {
        Map<String, Object> pet = new HashMap<>();
        pet.put("id", PET_ID);
        pet.put("name", "Fluffy");
        pet.put("status", "available");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Fluffy");
    }

    @Test
    @Description("Изменение питомца")
    public void testUpdatePet() {
        Map<String, Object> updatedPet = new HashMap<>();
        updatedPet.put("id", PET_ID);
        updatedPet.put("name", "FluffyUpdated");
        updatedPet.put("status", "sold");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updatedPet)
                .when()
                .put("/pet")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "FluffyUpdated");
        Assert.assertEquals(response.jsonPath().getString("status"), "sold");
    }

    @Test
    @Description("Удаление питомца")
    public void testDeletePet() {
        Response response = RestAssured.given()
                .when()
                .delete("/pet/" + PET_ID)
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @AfterSuite
    public void cleanup() {
        RestAssured.given()
                .when()
                .delete("/pet/" + PET_ID)
                .then()
                .extract().response();
    }
}


