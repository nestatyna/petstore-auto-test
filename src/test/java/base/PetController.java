package base;

import dto.Pet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static base.CustomLogger.log;
import static java.lang.String.format;

public class PetController extends AbstractController {

    public static Pet getPet(Integer id) {
        Response response = isSuccess(gePetRequest(id), 200);
        return response.as(Pet.class);
    }

    public static Response gePetRequest(Integer id) {
        log(format("<<<<<< gePetRequest(), id = %s\n", id));
        return RestAssured.given()
                .log().method()
                .log().uri()
                .log().headers()
                .when()
                .get("/pet/" + id)
                .then()
                .extract().response();
    }

    public static Pet createPet(Pet pet) {
        Response response = isSuccess(createPetRequest(pet), 200);
        return response.as(Pet.class);
    }

    public static Response createPetRequest(Pet pet) {
        log(format("<<<<<< createPetRequest(), pet id = %s\n", pet.getId()));
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(pet)
                .log().method()
                .log().uri()
                .log().headers()
                .log().body()
                .when()
                .post("/pet")
                .then()
                .extract().response();
    }

    public static Response deletePet(Integer id) {
        return isSuccess(deletePetRequest(id), 200);
    }

    public static Response deletePetRequest(Integer id) {
        log(format("<<<<<< deletePetRequest(), pet id = %s\n", id));
        return RestAssured.given()
                .log().method()
                .log().uri()
                .log().headers()
                .when()
                .delete("/pet/" + id)
                .then()
                .extract().response();
    }

    public static Pet updatePet(Pet pet) {
        Response response = isSuccess(updatePetRequest(pet), 200);
        return response.as(Pet.class);
    }

    public static Response updatePetRequest(Pet pet) {
        log(format("<<<<<< updatePetRequest(), pet id = %s\n", pet.getId()));
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(pet)
                .log().method()
                .log().uri()
                .log().headers()
                .log().body()
                .when()
                .put("/pet")
                .then()
                .extract().response();
    }
}
