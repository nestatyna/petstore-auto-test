package base;

import dto.Category;
import dto.Pet;
import dto.Tag;

import java.util.ArrayList;

import static base.Helpers.getRandomId;

public class BodyHelper {

    public static Pet getPetBody() {

        return getPetBody("Fluffy", "available");
    }

    public static Pet getPetBody(String name, String status) {
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(Tag.builder()
                .id(0)
                .name("autotest")
                .build());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add("photoUrls for Fluffy");

        return Pet.builder()
                .id(getRandomId())
                .name(name)
                .status(status)
                .category(Category.builder()
                        .id(0)
                        .name("autotest")
                        .build())
                .tags(tagsList)
                .photoUrls(photoUrls)
                .build();
    }
}
