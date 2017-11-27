package towerdefence.graphics;

import towerdefence.World;
import towerdefence.WorldMap;

public class JGrassTile extends JTile {
    public JGrassTile(World world) {
        super(Texture.get(WorldMap.GRASS), world);
        float rotation = (float) (Math.round(Math.random() * 4) * Math.PI / 2f);
        setRotation(rotation);
    }
}
