package base;

import org.testng.annotations.AfterSuite;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static base.CustomLogger.log;
import static base.controllers.PetController.deletePetWrapper;
import static base.controllers.PetController.deletePetWrapperWithoutRemove;

public class TestBase {

    public static final Set<Long> PET_IDS = new HashSet<>();

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

       /* PET_IDS.forEach(petId -> {
            log("Удаляем питомца id = " + petId);
            deletePetWrapper(petId);
        });*/
    }
}
