package tests.pet;

import base.TestBase;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import static base.AbstractController.isSuccess;
import static base.CustomLogger.step;
import static base.PetController.deletePet;
import static base.PetController.deletePetRequest;
import static org.testng.Assert.assertEquals;

public class DeletePetTests extends TestBase {

    @Test
    @Description("Удаление питомца")
    public void checkDeletePetTest() {
        Response response = deletePet(PET_ID);

        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @Description("Удаление питомца по несуществующему id")
    public void checkDeleteNotExistPetTest() {
        step("Удаляем питомца по несуществующему id");
        isSuccess(deletePetRequest(PET_ID), 404);
    }
}
