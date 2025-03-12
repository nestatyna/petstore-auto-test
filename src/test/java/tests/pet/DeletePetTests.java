package tests.pet;

import base.TestBase;
import dto.DeletePetResponse;
import dto.Pet;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static base.Helpers.createNewPet;
import static base.Helpers.getRandomId;
import static base.controllers.AbstractController.isSuccess;
import static base.controllers.PetController.*;
import static org.testng.AssertJUnit.assertEquals;

@Feature("Удаление питомца")
public class DeletePetTests extends TestBase {

    @Test
    @Description("Удаление питомца")
    public void checkDeletePetTest() {
        final Pet[] pet = {new Pet()};

        Allure.step("Создаём питомца", () -> {
            pet[0] = createNewPet();
        });

        Allure.step("Удаляем созданного питомца", () -> {
            DeletePetResponse response = deletePetWrapper(pet[0].getId());

            assertEquals(response.getCode().intValue(), 200);
            assertEquals(response.getType(), "unknown");
            assertEquals(response.getMessage(), pet[0].getId().toString());
        });

        Allure.step("Проверяем, что питомец удалён", () -> {
            isSuccess(gePetRequest(getRandomId()), 404);
        });
    }

    @Test
    @Description("Удаление питомца по несуществующему id")
    public void checkDeleteNotExistPetTest() {
        Allure.step("Удаляем питомца по несуществующему id", () -> {
            isSuccess(deletePetRequest(getRandomId()), 404);
        });
    }
}
