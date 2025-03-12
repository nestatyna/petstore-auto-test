package base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static base.CustomLogger.log;
import static base.controllers.PetController.deletePetWrapper;
import static base.controllers.PetController.deletePetWrapperWithoutRemove;

public class TestBase {

    public static final Set<Long> PET_IDS = new HashSet<>();

    @BeforeClass
    public void setup() {
        RestAssured.filters(new AllureRestAssured());
    }

    @AfterSuite
    public void cleanup() {
        log("\nAFTER SUITE: Удаляем созданных питомцев, id = " + PET_IDS);

        Iterator<Long> iterator = PET_IDS.iterator();
        while (iterator.hasNext()) {
            Long petId = iterator.next();
            log("Удаляем питомца id = " + petId);
            deletePetWrapper(petId, false);
            iterator.remove(); // Теперь удаление выполняется ТОЛЬКО здесь
        }
    }
}
