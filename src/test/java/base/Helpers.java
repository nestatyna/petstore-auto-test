package base;

import base.controllers.PetController;
import dto.Category;
import dto.Pet;
import dto.Tag;

import java.util.ArrayList;

import static base.TestBase.PET_ID;

public class Helpers {

    public static Pet createNewPet() {
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

        return PetController.createPet(petBody);
    }
}
