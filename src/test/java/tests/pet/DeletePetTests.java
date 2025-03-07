package tests.pet;

import base.Helpers;
import base.TestBase;
import dto.Pet;
import jdk.jfr.Description;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static base.CustomLogger.step;
import static base.controllers.AbstractController.isSuccess;
import static base.controllers.PetController.*;

public class DeletePetTests extends TestBase {

    @Override
    @AfterTest
    public void cleanup() {}

    @Test
    @Description("Удаление питомца")
    public void checkDeletePetTest() {
        step("Создаём питомцв");
        Pet pet = Helpers.createNewPet();

        step("Удаялем созданного питомца");
        deletePet(pet.getId());

        step("Проверяем, что питомец удалён");
        isSuccess(gePetRequest(getRandomId()), 404);
    }

    @Test
    @Description("Удаление питомца по несуществующему id")
    public void checkDeleteNotExistPetTest() {
        step("Удаляем питомца по несуществующему id");
        isSuccess(deletePetRequest(PET_ID), 404);
    }
}
