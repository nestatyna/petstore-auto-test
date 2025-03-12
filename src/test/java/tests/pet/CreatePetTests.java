package tests.pet;

import base.TestBase;
import dto.Category;
import dto.ErrorResponse;
import dto.Pet;
import dto.Tag;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static base.BodyHelper.getPetBody;
import static base.CustomLogger.step;
import static base.Helpers.getRandomId;
import static base.controllers.PetController.*;
import static org.testng.Assert.assertEquals;

public class CreatePetTests extends TestBase {

    @Test
    @Description("Создание питомца")
    public void checkCreatePetTest() {
        step("Создаём питомца");
        Pet petBody = getPetBody();
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
        Pet pet = getPet(createdPet.getId());

        assertEquals(pet.getId(), createdPet.getId());
        assertEquals(pet.getName(), createdPet.getName());
        assertEquals(pet.getStatus(), createdPet.getStatus());
        assertEquals(pet.getCategory().getId(), createdPet.getCategory().getId());
        assertEquals(pet.getCategory().getName(), createdPet.getCategory().getName());
        assertEquals(pet.getTags().get(0).getId(), createdPet.getTags().get(0).getId());
        assertEquals(pet.getTags().get(0).getName(), createdPet.getTags().get(0).getName());
        assertEquals(pet.getPhotoUrls().get(0), createdPet.getPhotoUrls().get(0));
    }

    @Test
    @Description("Создание питомца без отправки body")
    public void checkCreatePetWithoutBodyTest() {
        step("Создаём питомца");
        Response response = isSuccess(createPetWithoutBodyRequest(""), 405);

        ErrorResponse error = response.as(ErrorResponse.class);

        assertEquals(error.getCode(), 405);
        assertEquals(error.getType(), "unknown");
        assertEquals(error.getMessage(), "no data");
    }

    @DataProvider(name = "petDataProvider")
    public Object[][] petDataProvider() {
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(Tag.builder()
                .id(0)
                .name("autotest")
                .build());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add("photoUrls for Fluffy");

        return new Object[][]{
                {null, "available", tagsList, photoUrls},
                {"Fluffy", null, tagsList, photoUrls},
                {"Fluffy", "available", null, photoUrls},
                {"Fluffy", "available", tagsList, null}
        };
    }

    @Test(dataProvider = "petDataProvider")
    @Description("Создание питомца с разными входными данными")
    public void checkCreatePetWithNullNameTest(String name, String status, ArrayList<Tag> tagsList, ArrayList<String> photoUrl) {
        step("Создаём питомца");
        Pet petBody = Pet.builder()
                .id(getRandomId())
                .name(name)
                .status(status)
                .category(Category.builder()
                        .id(0)
                        .name("autotest")
                        .build())
                .tags(tagsList)
                .photoUrls(photoUrl)
                .build();

        Pet createdPet = createPet(petBody);

        assertEquals(createdPet.getId(), petBody.getId());
        assertEquals(createdPet.getName(), petBody.getName());
        assertEquals(createdPet.getStatus(), petBody.getStatus());
        assertEquals(createdPet.getCategory().getId(), petBody.getCategory().getId());
        assertEquals(createdPet.getCategory().getName(), petBody.getCategory().getName());
        if (tagsList != null) {
            assertEquals(createdPet.getTags().get(0).getId(), petBody.getTags().get(0).getId());
            assertEquals(createdPet.getTags().get(0).getName(), petBody.getTags().get(0).getName());
        } else {
            assertEquals(createdPet.getTags().toString(), "[]");
        }
        if (photoUrl != null) {
            assertEquals(createdPet.getPhotoUrls().get(0), petBody.getPhotoUrls().get(0));
        } else {
            assertEquals(createdPet.getPhotoUrls().toString(), "[]");
        }

        step("Проверяем, что питомец создан корректно");
        Pet pet = getPetWrapper(createdPet.getId());

        assertEquals(pet.getId(), createdPet.getId());
        assertEquals(pet.getName(), createdPet.getName());
        assertEquals(pet.getStatus(), createdPet.getStatus());
        assertEquals(pet.getCategory().getId(), createdPet.getCategory().getId());
        assertEquals(pet.getCategory().getName(), createdPet.getCategory().getName());
        if (tagsList != null) {
            assertEquals(pet.getTags().get(0).getId(), createdPet.getTags().get(0).getId());
            assertEquals(pet.getTags().get(0).getName(), createdPet.getTags().get(0).getName());
        } else {
            assertEquals(pet.getTags().toString(), "[]");
        }
        if (photoUrl != null) {
            assertEquals(pet.getPhotoUrls().get(0), createdPet.getPhotoUrls().get(0));
        } else {
            assertEquals(pet.getPhotoUrls().toString(), "[]");
        }
    }

    @Test
    @Description("Повторное создание питомца")
    public void checkCreateDuplicateTest() {
        step("Создаём питомца");
        Pet petBody = getPetBody();
        Pet createdPet = createPet(petBody);

        step("Отправляем запрос на повторное создание");
        Pet pet = createPet(petBody);

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
