package tests.pet;

import base.TestBase;
import dto.Pet;
import io.qameta.allure.Description;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static base.BodyHelper.getPetBody;
import static base.CustomLogger.step;
import static base.Helpers.getRandomId;
import static base.controllers.PetController.*;
import static org.testng.Assert.assertEquals;
@Ignore
public class UpdatePetTests extends TestBase {

    @Test
    @Description("Изменение питомца")
    public void checkUpdatePetTest() {
        step("Создаём питомца");
        Pet petBody = getPetBody();
        Pet createdPet = createPet(petBody);

        step("Изменяем данные питомца");
        Pet updatePetBody = getPetBody("FluffyUpdated", "sold");
        updatePetBody.setId(createdPet.getId());
        Pet updatedPet = updatePet(updatePetBody);

        assertEquals(updatedPet.getName(), updatePetBody.getName());
        assertEquals(updatedPet.getStatus(), updatePetBody.getStatus());

        step("Проверяем, что данные изменились");
        Pet pet = getPetWrapper(createdPet.getId());

        assertEquals(pet.getId(), updatedPet.getId());
        assertEquals(pet.getName(), updatedPet.getName());
        assertEquals(pet.getStatus(), updatedPet.getStatus());
        assertEquals(pet.getCategory().getId(), updatedPet.getCategory().getId());
        assertEquals(pet.getCategory().getName(), updatedPet.getCategory().getName());
        assertEquals(pet.getTags().get(0).getId(), updatedPet.getTags().get(0).getId());
        assertEquals(pet.getTags().get(0).getName(), updatedPet.getTags().get(0).getName());
        assertEquals(pet.getPhotoUrls().get(0), updatedPet.getPhotoUrls().get(0));
    }

    @Test
    @Description("Изменение питомца с несуществующим id")
    public void checkUpdatePetWithNotExistIdTest() {
        step("Создаём питомца");
        Pet petBody = getPetBody();
        Pet createdPet = createPet(petBody);

        step("Изменяем данные питомца");
        Pet updatePetBody =  getPetBody("FluffyUpdated", "sold");
        updatePetBody.setId(getRandomId());

        Pet updatedPet = updatePet(updatePetBody);

        assertEquals(updatedPet.getName(), updatePetBody.getName());
        assertEquals(updatedPet.getStatus(), updatePetBody.getStatus());

        step("Проверяем, что данные не изменились");
        Pet pet = getPet(createdPet.getId());

        assertEquals(pet.getId(), createdPet.getId());
        assertEquals(pet.getName(), createdPet.getName());
        assertEquals(pet.getStatus(), createdPet.getStatus());

        step("Проверяем, что питомец не создан");
        isSuccess(gePetRequest(updatedPet.getId()), 404);
    }
}
