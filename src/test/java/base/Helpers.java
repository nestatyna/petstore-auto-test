package base;

import dto.Pet;

import java.util.Random;

import static base.BodyHelper.getPetBody;
import static base.controllers.PetController.createPet;

public class Helpers {

    public static Pet createNewPet() {
        return createPet(getPetBody());
    }

    public static Integer getRandomId() {
        return new Random().nextInt(100000);
    }
}
