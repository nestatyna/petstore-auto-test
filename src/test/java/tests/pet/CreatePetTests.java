package tests.pet;

import base.controllers.PetController;
import base.TestBase;
import dto.Category;
import dto.Pet;
import dto.Tag;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static base.CustomLogger.step;
import static base.controllers.PetController.createPet;
import static org.testng.Assert.assertEquals;

public class CreatePetTests extends TestBase {

    @Test
    @Description("Создание питомца")
    public void testCreatePet() {
        step("Создаём питомца");
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(Tag.builder()
                .id(0)
                .name("autotest")
                .build());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add("photoUrls for Fluffy");

        Pet petBody = Pet.builder()
                .id(PET_ID)
                .name("Fluffy")
                .status("available")
                .category(Category.builder()
                        .id(0)
                        .name("autotest")
                        .build())
                .tags(tagsList)
                .photoUrls(photoUrls)
                .build();

        Pet createdPet = createPet(petBody);

        assertEquals(createdPet.getId(), petBody.getId());
        assertEquals(createdPet.getName(), petBody.getName());
        assertEquals(createdPet.getStatus(), petBody.getStatus());
        assertEquals(createdPet.getCategory().getId(), petBody.getCategory().getId());
        assertEquals(createdPet.getCategory().getName(), petBody.getCategory().getName());
        assertEquals(createdPet.getTags().get(0).getId(), petBody.getTags().get(0).getId());
        assertEquals(createdPet.getTags().get(0).getName(), petBody.getTags().get(0).getName());
        assertEquals(createdPet.getPhotoUrls().get(0), petBody.getPhotoUrls().get(0));

        step("Проверяем, что питомец создан корректно");
        Pet pet = PetController.getPet(createdPet.getId());

        assertEquals(pet.getId(), createdPet.getId());
        assertEquals(pet.getName(), createdPet.getName());
        assertEquals(pet.getStatus(), createdPet.getStatus());
        assertEquals(pet.getCategory().getId(), createdPet.getCategory().getId());
        assertEquals(pet.getCategory().getName(), createdPet.getCategory().getName());
        assertEquals(pet.getTags().get(0).getId(), createdPet.getTags().get(0).getId());
        assertEquals(pet.getTags().get(0).getName(), createdPet.getTags().get(0).getName());
        assertEquals(pet.getPhotoUrls().get(0), createdPet.getPhotoUrls().get(0));
    }
}
