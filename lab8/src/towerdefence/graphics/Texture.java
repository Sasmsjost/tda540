package towerdefence.graphics;

import towerdefence.World;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Texture {
    public  static int TILE_SIZE = 80;

    private static Map<Integer, Image[]> textures = new HashMap<>();

    public static void load() {
        textures.put(World.GRASS, Texture.loadTexture("grass"));
        textures.put(World.ROAD, Texture.loadTexture("road"));
        textures.put(World.TOWER, Texture.loadTexture("tower"));
        textures.put(World.MONSTER, Texture.loadTexture("monster"));
        textures.put(World.GOAL, Texture.loadTexture("goal"));
    }

    public static Image[] get(int id) {
        return textures.get(id);
    }

    private static Image[] loadTexture(String fileName) {
        try {
            URI uri = Texture.class.getResource("../icons").toURI();
            File[] files = new File(uri).listFiles((dir, name) -> name.contains(fileName));
            if(files != null) {
                ArrayList<Image> images = new ArrayList<>();
                for (File file : files) {
                    ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                    Image image = icon.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH);
                    images.add(image);
                }
                return images.toArray(new Image[]{});
            }
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("Unable to load texture " + fileName);
        return new Image[0];
    }
}
