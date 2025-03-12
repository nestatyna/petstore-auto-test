package tests.pet;

import base.TestBase;
import dto.Pet;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static base.BodyHelper.getPetBody;
import static base.Helpers.getRandomId;
import static base.controllers.PetController.*;
import static org.testng.Assert.assertEquals;

@Feature("Изменение данных питомца")
public class UpdatePetTests extends TestBase {

    @Test
    @Description("Изменение питомца")
    public void checkUpdatePetTest() {
        Pet petBody = getPetBody();
        final Pet[] createdPet = {new Pet()};
        final Pet[] updatedPet = {new Pet()};

        Allure.step("Создаём питомца", () -> {
            createdPet[0] = createPet(petBody);
        });

        Allure.step("Изменяем данные питомца", () -> {
            Pet updatePetBody = getPetBody("FluffyUpdated", "sold");
            updatePetBody.setId(createdPet[0].getId());
            updatedPet[0] = updatePet(updatePetBody);

            assertEquals(updatedPet[0].getName(), updatePetBody.getName());
            assertEquals(updatedPet[0].getStatus(), updatePetBody.getStatus());
        });

        Allure.step("Проверяем, что данные изменились", () -> {
            Pet pet = getPetWrapper(createdPet[0].getId());

            assertEquals(pet.getId(), updatedPet[0].getId());
            assertEquals(pet.getName(), updatedPet[0].getName());
            assertEquals(pet.getStatus(), updatedPet[0].getStatus());
            assertEquals(pet.getCategory().getId(), updatedPet[0].getCategory().getId());
            assertEquals(pet.getCategory().getName(), updatedPet[0].getCategory().getName());
            assertEquals(pet.getTags().get(0).getId(), updatedPet[0].getTags().get(0).getId());
            assertEquals(pet.getTags().get(0).getName(), updatedPet[0].getTags().get(0).getName());
            assertEquals(pet.getPhotoUrls().get(0), updatedPet[0].getPhotoUrls().get(0));
        });
    }

    @Test
    @Description("Изменение питомца с несуществующим id")
    public void checkUpdatePetWithNotExistIdTest() {
        Pet petBody = getPetBody();
        final Pet[] createdPet = {new Pet()};

        Pet updatePetBody = getPetBody("FluffyUpdated", "sold");
        updatePetBody.setId(getRandomId());
        final Pet[] updatedPet = {new Pet()};

        Allure.step("Создаём питомца", () -> {
            createdPet[0] = createPet(petBody);
        });

        Allure.step("Изменяем данные питомца", () -> {
            updatedPet[0] = updatePet(updatePetBody);

            assertEquals(updatedPet[0].getName(), updatePetBody.getName());
            assertEquals(updatedPet[0].getStatus(), updatePetBody.getStatus());
        });

        Allure.step("Проверяем, что данные не изменились", () -> {
            Pet pet = getPetWrapper(createdPet[0].getId());

            assertEquals(pet.getId(), createdPet[0].getId());
            assertEquals(pet.getName(), createdPet[0].getName());
            assertEquals(pet.getStatus(), createdPet[0].getStatus());
        });

        Allure.step("Проверяем, что создан питомец с новым id", () -> {
            getPetWrapper(updatedPet[0].getId());
        });
    }
}
