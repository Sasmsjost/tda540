package towerdefence.graphics;

import towerdefence.World;
import towerdefence.go.Tower;
import towerdefence.util.WorldPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public final class JBeam extends JComponent {
    private Tower tower;
    private int offset;
    private World world;
    private static final float lifetime = 1000;

    public JBeam(Tower tower, World world, int offset) {
        this.tower = tower;
        this.world = world;
        this.offset = offset;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (tower.getLastTarget() == null) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g;

        WorldPosition from = tower.getPosition();
        WorldPosition to = tower.getLastTarget().getPosition();

        // Transform from global to local coordinates
        float minX = (Math.min(to.getX(), from.getX()));
        float minY = (Math.min(to.getY(), from.getY()));
        float x = (from.getX() - minX) * Texture.TILE_SIZE + offset;
        float x2 = (to.getX() - minX) * Texture.TILE_SIZE + offset;
        float y = (from.getY() - minY) * Texture.TILE_SIZE + offset;
        float y2 = (to.getY() - minY) * Texture.TILE_SIZE + offset;

        // Calculate delta value from shot started with respect to lifetime
        long now = world.now();
        float delta = 1 - Math.min(now - tower.getLastShot(), lifetime) / lifetime;
        int opacity = (int) (delta * 255);

        // Increase the shot speed with time
        float dx = x2 - x;
        float dy = y2 - y;
        float passed = (float) Math.pow(Math.min((1 - delta) * 2, 1), 4);
        x2 = x + dx * passed;
        y2 = y + dy * passed;

        boolean isHit = tower.isLastShotHit();
        if (isHit) {
            drawLine(g2d, x, y, x2, y2, delta, new Color(255, 50, 100, opacity));
            drawLine(g2d, x, y, x2, y2, delta, new Color(50, 255, 100, opacity));
            drawLine(g2d, x, y, x2, y2, delta, new Color(100, 120, 255, opacity));
            drawProjectile(g2d, x2, y2, new Color(255, 255, 255, opacity));
        } else {
            float r = (float) Math.random();
            r = 0.6f + r * 0.4f;
            x2 = x2 * r;
            y2 = y2 * r;
            drawLine(g2d, x, y, x2, y2, 0.1f, new Color(50, 50, 50, opacity));
        }
    }

    private void drawProjectile(Graphics2D g2d, float x, float y, Color color) {
        int size = 20;
        g2d.setColor(color);
        g2d.fillOval((int) x - size / 2, (int) y - size / 2, size, size);
    }

    private void drawLine(Graphics2D g2d, float x, float y, float x2, float y2, float delta, Color color) {
        g2d.setColor(color);

        float cx = (float) ((Math.random() - 0.5) * delta) * getWidth();
        float cy = (float) ((Math.random() - 0.5) * delta) * getHeight();

        Path2D.Float path = new Path2D.Float();
        path.moveTo(x, y);
        path.curveTo(x, y, x + cx, y + cy, x2, y2);
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(path);
    }

    public int getOffset() {
        return offset;
    }
}
