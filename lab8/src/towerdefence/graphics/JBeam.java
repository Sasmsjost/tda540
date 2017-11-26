package towerdefence.graphics;

import com.sun.istack.internal.NotNull;
import towerdefence.WorldPosition;
import towerdefence.go.Tower;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class JBeam extends JComponent {
    private Tower tower;
    private int offset;
    private float lifetime = 1000;

    public JBeam(@NotNull Tower tower, int offset) {
        this.tower = tower;
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
        float x = (from.getX()-minX) * Texture.TILE_SIZE + offset;
        float x2 = (to.getX()-minX) * Texture.TILE_SIZE + offset;
        float y = (from.getY()-minY) * Texture.TILE_SIZE + offset;
        float y2 = (to.getY()-minY) * Texture.TILE_SIZE + offset;

        long now = System.currentTimeMillis();
        float delta = 1 - Math.min(now - tower.getLastShot(), lifetime) / lifetime;
        int opacity = (int) (delta * 255);

        if (tower.isLastShotHit()) {
            delta /= 4;
            drawLine(g2d, x, y, x2, y2, delta, new Color(255, 50, 100, opacity));
            drawLine(g2d, x, y, x2, y2, delta, new Color(50, 255, 100, opacity));
            drawLine(g2d, x, y, x2, y2, delta, new Color(100, 120, 255, opacity));
        } else {
            drawLine(g2d, x, y, (float) (Math.random() * x2), (float) (Math.random() * y2), 1f, new Color(50, 50, 50, opacity));
        }
    }

    private void drawLine(Graphics2D g2d, float x, float y, float x2, float y2, float delta, Color color) {
        g2d.setColor(color);

        // 0->1 >> 0.5->1.5
        delta = (delta + 0.5f);

        float cx = (float) (Math.random() * delta) * getWidth();
        float cy = (float) (Math.random() * delta) * getHeight();

        Path2D.Float path = new Path2D.Float();
        path.moveTo(x, y);
        path.curveTo(x, y, cx, cy, x2, y2);
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(path);
    }

    public int getOffset() {
        return offset;
    }
}
