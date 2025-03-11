package base.controllers;

import dto.DeletePetResponse;
import dto.Pet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Objects;

import static base.CustomLogger.log;
import static base.Helpers.sleep;
import static base.TestBase.PET_IDS;
import static java.lang.String.format;

public class PetController extends AbstractController {

    public static Pet getPet(Long id) {
        Response response = isSuccess(gePetRequest(id), 200);
        return response.as(Pet.class);
    }

    public static Pet getPetWrapper(Long id) {
        for (int i = 0; i < 3; i++) {
            Response response = gePetRequest(id);

            if (response.getStatusCode() == 200) {
                log("SUCCESS: " + response.getStatusCode() + " OK");
                log("body: \n" + (response.asString().isEmpty() ? "empty" : response.asString()) + "\n");
                return response.as(Pet.class);
            }
            log("ERROR: " + response.getStatusCode() + " OK");
            log("body: \n" + (response.asString().isEmpty() ? "empty" : response.asString()) + "\n");
            sleep(1000);
        }
        throw new RuntimeException("Ошибка получения данных о питомце id = " + id);
    }

    public static Response gePetRequest(Long id) {
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
        Pet response = isSuccess(createPetRequest(pet), 200).as(Pet.class);
        PET_IDS.add(response.getId());
        return response;
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

    public static Response createPetWithoutBodyRequest(String body) {
        log(format("<<<<<< createPetWithoutBodyRequest()\n"));
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().method()
                .log().uri()
                .log().headers()
                .log().body()
                .when()
                .post("/pet")
                .then()
                .extract().response();
    }

    public static DeletePetResponse deletePet(Long id) {
        Response response = isSuccess(deletePetRequest(id), 200);
        PET_IDS.remove(id);
        return response.as(DeletePetResponse.class);
    }

    public static DeletePetResponse deletePetWrapper(Long id, Boolean remove) {
        for (int i = 0; i < 3; i++) {
            Response response = deletePetRequest(id);

            if (response.getStatusCode() == 200) {
                if (remove) {
                    PET_IDS.remove(id);
                }
                log("SUCCESS: " + response.getStatusCode() + " OK");
                log("body: \n" + (response.asString().isEmpty() ? "empty" : response.asString()) + "\n");
                return response.as(DeletePetResponse.class);
            }
            log("ERROR: " + response.getStatusCode() + " OK");
            log("body: \n" + (response.asString().isEmpty() ? "empty" : response.asString()) + "\n");
            sleep(2000);
        }
        log("ERROR: Не удалось удалить питомца ID = " + id + " после 3 попыток.");
        return null;
    }

    public static DeletePetResponse deletePetWrapper(Long id) {
        return deletePetWrapper(id, true);
    }

    public static DeletePetResponse deletePetWrapperWithoutRemove(Long id) {
        for (int i = 0; i < 3; i++) {
            Response response = deletePetRequest(id);

            if (response.getStatusCode() == 200) {
                log("SUCCESS: " + response.getStatusCode() + " OK");
                log("body: \n" + (response.asString().isEmpty() ? "empty" : response.asString()) + "\n");
                return response.as(DeletePetResponse.class);
            }
            log("ERROR: " + response.getStatusCode() + " OK");
            log("body: \n" + (response.asString().isEmpty() ? "empty" : response.asString()) + "\n");
            sleep(1000);
        }
        log("ERROR: Не удалось удалить питомца ID = " + id + " после 3 попыток.");
        return null;
    }

    public static Response deletePetRequest(Long id) {
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
        PET_IDS.add(pet.getId());

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
