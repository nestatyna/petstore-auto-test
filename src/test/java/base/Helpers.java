package base;

import dto.Pet;

import java.util.Random;

import static base.BodyHelper.getPetBody;
import static base.controllers.PetController.createPet;

public class Helpers {

    public static Pet createNewPet() {
        return createPet(getPetBody());
    }

    public static Long getRandomId() {
        return new Random().nextLong(100000);
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
