package tests.pet;

import base.TestBase;
import dto.ErrorResponse;
import dto.Pet;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static base.CustomLogger.step;
import static base.Helpers.createNewPet;
import static base.Helpers.getRandomId;
import static base.controllers.AbstractController.isSuccess;
import static base.controllers.PetController.gePetRequest;
import static base.controllers.PetController.getPetWrapper;
import static org.testng.Assert.assertEquals;
@Ignore
public class GetPetTests extends TestBase {

    @Test
    @Description("Запрос данных питомца по Id")
    public void checkGetPetByIdTest() {
        step("Создаём питомца");
        Pet createdPet = createNewPet();

        step("Запрашиваем данные питомца по id");
        Pet response = getPetWrapper(createdPet.getId());

        assertEquals(response.getId(), createdPet.getId());
        assertEquals(response.getName(), createdPet.getName());
        assertEquals(response.getStatus(), createdPet.getStatus());
        assertEquals(response.getCategory().getId(), createdPet.getCategory().getId());
        assertEquals(response.getCategory().getName(), createdPet.getCategory().getName());
        assertEquals(response.getTags().get(0).getId(), createdPet.getTags().get(0).getId());
        assertEquals(response.getTags().get(0).getName(), createdPet.getTags().get(0).getName());
        assertEquals(response.getPhotoUrls().get(0), createdPet.getPhotoUrls().get(0));
    }

    @Test
    @Description("Запрос данных питомца по несуществующему id")
    public void checkGetPetByNotExistIdTest() {
        step("Запрашиваем данные питомца по несуществующему id");
        Response response = isSuccess(gePetRequest(getRandomId()), 404);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        assertEquals(errorResponse.getCode(), 1);
        assertEquals(errorResponse.getType(), "error");
        assertEquals(errorResponse.getMessage(), "Pet not found");
    }
}


