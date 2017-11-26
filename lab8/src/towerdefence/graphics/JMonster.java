package towerdefence.graphics;

import com.sun.istack.internal.NotNull;
import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.go.Monster;

import java.awt.*;

public  class JMonster extends JTile {
    private Monster monster;

    public JMonster(@NotNull Monster monster, World world) {
        super(Texture.get(WorldMap.MONSTER), world);
        this.monster= monster;
        animationSpeed = 100;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double offset = Math.sin(world.now() / 200d) * 5d;

        g2d.translate(0, offset);
        super.paintComponent(g);

        g2d.translate(0, -offset);
        int x = 20;
        int y = getHeight() - 20;
        int width = getWidth() - x*2;
        int height = 5;

        float health = (float)monster.getHealth() / monster.getMaxHealth();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(x,y,width, height);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(x,y,(int)(width*health), height);
    }
}
