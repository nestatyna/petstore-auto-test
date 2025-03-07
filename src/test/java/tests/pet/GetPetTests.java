package tests.pet;

import base.PetController;
import base.TestBase;
import dto.ErrorResponse;
import dto.Pet;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import static base.AbstractController.isSuccess;
import static base.CustomLogger.step;
import static base.PetController.createPet;
import static base.PetController.gePetRequest;
import static org.testng.Assert.assertEquals;

public class GetPetTests extends TestBase {

    @Test
    @Description("Запрос данных питомца по Id")
    public void testGetPetById() {
        step("Создаём питомца");
        Pet petBody = Pet.builder()
                .id(PET_ID)
                .name("Fluffy")
                .status("available")
                .build();
        Pet createdPet = createPet(petBody);

        step("Запрашиваем данные питомца по id");
        Pet response = PetController.getPet(createdPet.getId());

        assertEquals(response.getId(), PET_ID);
        assertEquals(response.getName(), petBody.getName());
        assertEquals(response.getStatus(), petBody.getStatus());
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


