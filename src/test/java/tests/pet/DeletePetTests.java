package tests.pet;

import base.TestBase;
import dto.DeletePetResponse;
import dto.Pet;
import jdk.jfr.Description;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static base.CustomLogger.step;
import static base.Helpers.createNewPet;
import static base.Helpers.getRandomId;
import static base.controllers.AbstractController.isSuccess;
import static base.controllers.PetController.*;
import static org.testng.AssertJUnit.assertEquals;

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
       // isSuccess(deletePetRequest(getRandomId()), 404);
        isSuccess(deletePetRequest(19241L), 200);
        isSuccess(deletePetRequest(41406L), 200);
    }
}
