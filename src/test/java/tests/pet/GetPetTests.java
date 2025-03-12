package tests.pet;

import base.TestBase;
import dto.ErrorResponse;
import dto.Pet;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static base.Helpers.createNewPet;
import static base.Helpers.getRandomId;
import static base.controllers.AbstractController.isSuccess;
import static base.controllers.PetController.gePetRequest;
import static base.controllers.PetController.getPetWrapper;
import static org.testng.Assert.assertEquals;

@Feature("Получение данных питомца")
public class GetPetTests extends TestBase {

    @Test
    @Description("Запрос данных питомца по Id")
    public void checkGetPetByIdTest() {
        final Pet[] createdPet = {new Pet()};

        Allure.step("Создаём питомца", () -> {
            createdPet[0] = createNewPet();
        });

        Allure.step("Запрашиваем данные питомца по id", () -> {
            Pet response = getPetWrapper(createdPet[0].getId());

            assertEquals(response.getId(), createdPet[0].getId());
            assertEquals(response.getName(), createdPet[0].getName());
            assertEquals(response.getStatus(), createdPet[0].getStatus());
            assertEquals(response.getCategory().getId(), createdPet[0].getCategory().getId());
            assertEquals(response.getCategory().getName(), createdPet[0].getCategory().getName());
            assertEquals(response.getTags().get(0).getId(), createdPet[0].getTags().get(0).getId());
            assertEquals(response.getTags().get(0).getName(), createdPet[0].getTags().get(0).getName());
            assertEquals(response.getPhotoUrls().get(0), createdPet[0].getPhotoUrls().get(0));
        });
    }

    @Test
    @Description("Запрос данных питомца по несуществующему id")
    public void checkGetPetByNotExistIdTest() {
        Allure.step("Запрашиваем данные питомца по несуществующему id", () -> {
            Response response = isSuccess(gePetRequest(getRandomId()), 404);
            ErrorResponse errorResponse = response.as(ErrorResponse.class);

            assertEquals(errorResponse.getCode(), 1);
            assertEquals(errorResponse.getType(), "error");
            assertEquals(errorResponse.getMessage(), "Pet not found");
        });
    }
}


