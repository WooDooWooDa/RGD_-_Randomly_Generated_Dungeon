package cegepst;

import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class GameResources {

    private static GameResources instance;
    private HashMap<String, Image> allImages;

    public static GameResources getInstance() {
        if (instance == null) {
            instance = new GameResources();
        }
        return instance;
    }

    private GameResources() {
        allImages = new HashMap<>();
        loadImagesResources(new File("resources/images"));
    }

    private void loadImagesResources(File folder) {

    }
}
