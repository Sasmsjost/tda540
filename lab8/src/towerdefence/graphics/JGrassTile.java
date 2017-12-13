package towerdefence.graphics;

import towerdefence.World;
import towerdefence.WorldMap;

import java.awt.*;
import java.util.Random;

final class JGrassTile extends JTile {
    private static final Random rand = new Random();
    private final int id;

    JGrassTile(World world) {
        super(new Image[]{Texture.get(WorldMap.GRASS)[0]}, world);
        id = rand.nextInt();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);

        double xO = Math.cos(world.now() / 200d + id) * 5d;
        double yO = Math.sin(world.now() / 200d + id) * 5d;
        double rO = Math.sin(world.now() / 200d + id);

        int size = Texture.TILE_SIZE;
        g2d.setColor(new Color(0x417505));
        for (int i = 0; i < 70; i++) {
            rand.setSeed(i + id);
            rand.nextFloat();

            double x = rand.nextFloat() * size + xO;
            double y = rand.nextFloat() * size + yO;
            double r = rand.nextFloat() * Math.PI * 2 + rO;

            double x2 = x + Math.cos(r) * 10;
            double y2 = y + Math.sin(r) * 10;

            g2d.drawLine((int) x, (int) y, (int) x2, (int) y2);
        }
    }
}
