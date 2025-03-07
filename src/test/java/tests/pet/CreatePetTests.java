package tests.pet;

import base.TestBase;
import dto.Pet;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import static base.CustomLogger.step;
import static base.PetController.createPet;
import static org.testng.Assert.assertEquals;

public class CreatePetTests extends TestBase {

    @Test
    @Description("Создание питомца")
    public void testCreatePet() {
        step("Создаём питомца");
        Pet pet = Pet.builder()
                .id(PET_ID)
                .name("Fluffy")
                .status("available")
                //todo добавить остальные поля
                .build();

        Pet createdPet = createPet(pet);

        step("Проверяем, что питомец создан корректно");
        assertEquals(createdPet.getId(), pet.getId());
        assertEquals(createdPet.getName(), pet.getName());
        assertEquals(createdPet.getStatus(), pet.getStatus());
    }
}
