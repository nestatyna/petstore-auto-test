package tests.store;

import base.TestBase;
import dto.StoreInventory;
import io.qameta.allure.Description;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static base.CustomLogger.step;
import static base.controllers.StoreController.getStoreInventory;
import static org.testng.Assert.assertNotNull;
@Ignore
public class StoreTests extends TestBase {

    @Test
    @Description("Запрос инвентаризации по статусам")
    public void checkGetPetByIdTest() {
        step("Запрашиваем инвентаризацию по статусам");
        StoreInventory response = getStoreInventory();

        //Т.к. у нас нет возможности узнать точное колличество животных по статусам, проверяем что они не null.
        assertNotNull(response.getAvailable());
        assertNotNull(response.getNotAvailable());
        assertNotNull(response.getSold());
        assertNotNull(response.getPending());
        assertNotNull(response.getString());
    }
}
