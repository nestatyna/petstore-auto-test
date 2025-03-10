package base;

import io.restassured.response.Response;
import org.testng.annotations.AfterTest;

import java.util.Random;

import static base.CustomLogger.log;
import static base.controllers.PetController.deletePetRequest;

public class TestBase {

    protected static final Integer PET_ID = 78819;

    @AfterTest
    public void cleanup() {
        log("\nAFTER TEST: Удаляем созданного питомца, id = " + PET_ID);
        Response response = deletePetRequest(PET_ID);
        log("RESPONSE STATUS: " + response.getStatusCode());
    }
}
