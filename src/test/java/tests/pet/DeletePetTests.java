package tests.pet;

import base.TestBase;
import dto.DeletePetResponse;
import dto.Pet;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static base.CustomLogger.step;
import static base.Helpers.createNewPet;
import static base.Helpers.getRandomId;
import static base.controllers.AbstractController.isSuccess;
import static base.controllers.PetController.*;
import static org.testng.AssertJUnit.assertEquals;
@Ignore
@Feature("Удаление питомца")
public class DeletePetTests extends TestBase {

    @Test
    @Description("Удаление питомца")
    public void checkDeletePetTest() {
        step("Создаём питомца");
        Pet pet = createNewPet();

        step("Удаляем созданного питомца");
        DeletePetResponse response = deletePetWrapper(pet.getId());

        assertEquals(response.getCode().intValue(), 200);
        assertEquals(response.getType(), "unknown");
        assertEquals(response.getMessage(), pet.getId().toString());

        step("Проверяем, что питомец удалён");
        isSuccess(gePetRequest(getRandomId()), 404);
    }

    @Test
    @Description("Удаление питомца по несуществующему id")
    public void checkDeleteNotExistPetTest() {
        step("Удаляем питомца по несуществующему id");
        isSuccess(deletePetRequest(getRandomId()), 404);
    }
}
