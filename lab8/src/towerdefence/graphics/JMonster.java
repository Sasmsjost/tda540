package towerdefence.graphics;

import java.awt.*;

public  class JMonster extends JTile {

    float health = 0;

    public JMonster(Image[] texture) {
        super(texture);
        animationSpeed = 100;
    }

    public void setHelthFraction(float health) {
        this.health = health;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double offset = Math.sin(System.currentTimeMillis() / 200d)*5d;

        g2d.translate(0, offset);
        super.paintComponent(g);

        g2d.translate(0, -offset);
        int x = 20;
        int y = getHeight() - 20;
        int width = getWidth() - x*2;
        int height = 5;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(x,y,width, height);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(x,y,(int)(width*health), height);
    }
}
