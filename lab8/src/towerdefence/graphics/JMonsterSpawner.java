package towerdefence.graphics;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.go.Monster;

import java.awt.*;

final class JMonsterSpawner extends JTile {
    private Monster monster;

    JMonsterSpawner(Monster monster, World world) {
        super(Texture.get(WorldMap.MONSTERSPAWNER), world);
        this.monster = monster;
        animationSpeed = 100;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        double offset = Math.sin(world.now() / 200d) * 5d;
        if (monster.isDead()) {
            offset = 0;
        }

        g2d.translate(0, offset);

        float health = (float) monster.getHealth() / monster.getMaxHealth();
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, health));
        super.paintComponent(g);

        g2d.translate(0, -offset);
    }
}
