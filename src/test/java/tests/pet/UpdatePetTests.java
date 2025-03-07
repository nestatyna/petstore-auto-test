package tests.pet;

import base.PetController;
import base.TestBase;
import dto.Pet;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import static base.CustomLogger.step;
import static base.PetController.createPet;
import static base.PetController.updatePet;
import static org.testng.Assert.assertEquals;

public class UpdatePetTests extends TestBase {

    @Test
    @Description("Изменение питомца")
    public void testUpdatePet() {
        step("Создаём питомца");
        Pet petBody = Pet.builder()
                .id(PET_ID)
                .name("Fluffy")
                .status("available")
                .build();
        Pet createdPet = createPet(petBody);

        step("Изменяем данные питомца");
        Pet updatePetBody = Pet.builder()
                .id(petBody.getId())
                .name("FluffyUpdated")
                .status("sold")
                .build();

        Pet updatedPet = updatePet(updatePetBody);

        assertEquals(updatedPet.getName(), updatePetBody.getName());
        assertEquals(updatedPet.getStatus(), updatePetBody.getStatus());

        step("Проверяем, что данные изменились");
        Pet pet = PetController.getPet(createdPet.getId());

        assertEquals(pet.getId(), updatedPet.getId());
        assertEquals(pet.getName(), updatedPet.getName());
        assertEquals(pet.getStatus(), updatedPet.getStatus());
    }
}
