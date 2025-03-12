package tests.pet;

import base.TestBase;
import dto.Category;
import dto.ErrorResponse;
import dto.Pet;
import dto.Tag;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static base.BodyHelper.getPetBody;
import static base.Helpers.getRandomId;
import static base.controllers.PetController.*;
import static org.testng.Assert.assertEquals;

@Feature("Создание питомца")
public class CreatePetTests extends TestBase {

    @Test
    @Description("Создание питомца")
    public void checkCreatePetTest() {

        final Pet[] createdPet = {new Pet()};

        Allure.step("Создаём питомца", () -> {
            Pet petBody = getPetBody();
            createdPet[0] = createPet(petBody);

            assertEquals(createdPet[0].getId(), petBody.getId());
            assertEquals(createdPet[0].getName(), petBody.getName());
            assertEquals(createdPet[0].getStatus(), petBody.getStatus());
            assertEquals(createdPet[0].getCategory().getId(), petBody.getCategory().getId());
            assertEquals(createdPet[0].getCategory().getName(), petBody.getCategory().getName());
            assertEquals(createdPet[0].getTags().get(0).getId(), petBody.getTags().get(0).getId());
            assertEquals(createdPet[0].getTags().get(0).getName(), petBody.getTags().get(0).getName());
            assertEquals(createdPet[0].getPhotoUrls().get(0), petBody.getPhotoUrls().get(0));
        });


        Allure.step("Проверяем, что питомец создан корректно", () -> {
            Pet pet = getPetWrapper(createdPet[0].getId());

            assertEquals(pet.getId(), createdPet[0].getId());
            assertEquals(pet.getName(), createdPet[0].getName());
            assertEquals(pet.getStatus(), createdPet[0].getStatus());
            assertEquals(pet.getCategory().getId(), createdPet[0].getCategory().getId());
            assertEquals(pet.getCategory().getName(), createdPet[0].getCategory().getName());
            assertEquals(pet.getTags().get(0).getId(), createdPet[0].getTags().get(0).getId());
            assertEquals(pet.getTags().get(0).getName(), createdPet[0].getTags().get(0).getName());
            assertEquals(pet.getPhotoUrls().get(0), createdPet[0].getPhotoUrls().get(0));
        });
    }

    @Test
    @Description("Создание питомца без отправки body")
    public void checkCreatePetWithoutBodyTest() {
        Allure.step("Создаём питомца без body", () -> {
            Response response = isSuccess(createPetWithoutBodyRequest(""), 405);

            ErrorResponse error = response.as(ErrorResponse.class);

            assertEquals(error.getCode(), 405);
            assertEquals(error.getType(), "unknown");
            assertEquals(error.getMessage(), "no data");
        });

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
        final Pet[] createdPet = {new Pet()};

        Allure.step("Создаём питомца", () -> {
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

            createdPet[0] = createPet(petBody);

            assertEquals(createdPet[0].getId(), petBody.getId());
            assertEquals(createdPet[0].getName(), petBody.getName());
            assertEquals(createdPet[0].getStatus(), petBody.getStatus());
            assertEquals(createdPet[0].getCategory().getId(), petBody.getCategory().getId());
            assertEquals(createdPet[0].getCategory().getName(), petBody.getCategory().getName());
            if (tagsList != null) {
                assertEquals(createdPet[0].getTags().get(0).getId(), petBody.getTags().get(0).getId());
                assertEquals(createdPet[0].getTags().get(0).getName(), petBody.getTags().get(0).getName());
            } else {
                assertEquals(createdPet[0].getTags().toString(), "[]");
            }
            if (photoUrl != null) {
                assertEquals(createdPet[0].getPhotoUrls().get(0), petBody.getPhotoUrls().get(0));
            } else {
                assertEquals(createdPet[0].getPhotoUrls().toString(), "[]");
            }
        });


        Allure.step("Проверяем, что питомец создан корректно", () -> {
            Pet pet = getPetWrapper(createdPet[0].getId());

            assertEquals(pet.getId(), createdPet[0].getId());
            assertEquals(pet.getName(), createdPet[0].getName());
            assertEquals(pet.getStatus(), createdPet[0].getStatus());
            assertEquals(pet.getCategory().getId(), createdPet[0].getCategory().getId());
            assertEquals(pet.getCategory().getName(), createdPet[0].getCategory().getName());
            if (tagsList != null) {
                assertEquals(pet.getTags().get(0).getId(), createdPet[0].getTags().get(0).getId());
                assertEquals(pet.getTags().get(0).getName(), createdPet[0].getTags().get(0).getName());
            } else {
                assertEquals(pet.getTags().toString(), "[]");
            }
            if (photoUrl != null) {
                assertEquals(pet.getPhotoUrls().get(0), createdPet[0].getPhotoUrls().get(0));
            } else {
                assertEquals(pet.getPhotoUrls().toString(), "[]");
            }
        });
    }

    @Test
    @Description("Повторное создание питомца")
    public void checkCreateDuplicateTest() {
        final Pet[] createdPet = {new Pet()};
        Pet petBody = getPetBody();

        Allure.step("Создаём питомца", () -> {
            createdPet[0] = createPet(petBody);
        });


        Allure.step("Отправляем запрос на повторное создание", () -> {
            Pet pet = createPet(petBody);

            assertEquals(pet.getId(), createdPet[0].getId());
            assertEquals(pet.getName(), createdPet[0].getName());
            assertEquals(pet.getStatus(), createdPet[0].getStatus());
            assertEquals(pet.getCategory().getId(), createdPet[0].getCategory().getId());
            assertEquals(pet.getCategory().getName(), createdPet[0].getCategory().getName());
            assertEquals(pet.getTags().get(0).getId(), createdPet[0].getTags().get(0).getId());
            assertEquals(pet.getTags().get(0).getName(), createdPet[0].getTags().get(0).getName());
            assertEquals(pet.getPhotoUrls().get(0), createdPet[0].getPhotoUrls().get(0));
        });
    }
}
