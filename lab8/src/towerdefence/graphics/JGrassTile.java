package towerdefence.graphics;

import towerdefence.World;
import towerdefence.WorldMap;

final class JGrassTile extends JTile {
    JGrassTile(World world) {
        super(Texture.get(WorldMap.GRASS), world);
    }
}
