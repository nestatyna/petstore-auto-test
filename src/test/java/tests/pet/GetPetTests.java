package tests.pet;

import base.controllers.PetController;
import base.TestBase;
import dto.ErrorResponse;
import dto.Pet;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import static base.Helpers.createNewPet;
import static base.controllers.AbstractController.isSuccess;
import static base.CustomLogger.step;
import static base.controllers.PetController.createPet;
import static base.controllers.PetController.gePetRequest;
import static java.lang.Thread.sleep;
import static org.testng.Assert.assertEquals;

public class GetPetTests extends TestBase {

    @Test
    @Description("Запрос данных питомца по Id")
    public void testGetPetById() {
        step("Создаём питомца");
        Pet createdPet = createNewPet();

        step("Запрашиваем данные питомца по id");
        Pet response = PetController.getPet(createdPet.getId());

        assertEquals(response.getId(), PET_ID);
        assertEquals(response.getName(), createdPet.getName());
        assertEquals(response.getStatus(), createdPet.getStatus());
    }

    @Test
    @Description("Запрос данных питомца по несуществующему id")
    public void testGetPetByNotExistId() {
        step("Запрашиваем данные питомца по несуществующему id");
        Response response = isSuccess(gePetRequest(getRandomId()), 404);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        assertEquals(errorResponse.getCode(), 1);
        assertEquals(errorResponse.getType(), "error");
        assertEquals(errorResponse.getMessage(), "Pet not found");
    }
}


