package towerdefence.graphics;

import towerdefence.World;
import towerdefence.go.Tower;
import towerdefence.util.WorldPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public final class JBeam extends JComponent {
    private static final int SHADER_ACCURACY = 2;
    private static final float lifetime = 1000;

    private Tower tower;
    private int offset;
    private World world;
    private BufferedImage background;

    public JBeam(Tower tower, World world, BufferedImage background, int offset) {
        this.background = background;
        this.tower = tower;
        this.world = world;
        this.offset = offset;
    }

    private void paintBlurredBackground(Graphics2D g2d, WorldPosition to, float delta) {
        if (background == null) {
            return;
        }

        WorldPosition pos1 = tower.getPosition();
        WorldPosition pos2 = tower.getLastTarget().getPosition();
        int x0 = (int) ((Math.min(pos1.getX(), pos2.getX()) + 0.5) * Texture.TILE_SIZE) - getRenderPadding();
        int y0 = (int) ((Math.min(pos1.getY(), pos2.getY()) + 0.5) * Texture.TILE_SIZE) - getRenderPadding();

        int maxDist = offset * offset;

        double ampCurve = (1 - Math.pow(delta * 2 - 1, 2));

        for (int i = 0; i < getWidth() / SHADER_ACCURACY; i++) {
            for (int j = 0; j < getHeight() / SHADER_ACCURACY; j++) {
                int x = i * SHADER_ACCURACY;
                int y = j * SHADER_ACCURACY;
                double dist = (Math.pow(to.getX() - x, 2f) + Math.pow(to.getY() - y, 2f)) + 1;

                if (dist > maxDist * ampCurve) {
                    continue;
                }

                double invDist = (maxDist - dist) / maxDist;
                double amp = invDist * ampCurve;
                double n = world.now() / 100f;

                double ox = Math.cos(n * 2 + amp * 20) * amp * 10;
                double oy = Math.sin(n + amp * 5) * amp * 10;

                ox = Math.abs(ox);
                oy = Math.abs(oy);

                int wx = (int) Math.round(x + x0 + ox);
                int wy = (int) Math.round(y + y0 + oy);

                if (wx > background.getWidth()) {
                    wx = background.getWidth();
                } else if (wx < 0) {
                    wx = 0;
                }

                if (wy > background.getHeight()) {
                    wy = background.getHeight();
                } else if (wy < 0) {
                    wy = 0;
                }

                Color c = new Color(background.getRGB(wx, wy));
                g2d.setColor(c);

                g2d.fillRect(x, y, SHADER_ACCURACY, SHADER_ACCURACY);
            }
        }
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
        float delta = 1f - Math.min(now - tower.getLastShot(), lifetime) / lifetime;
        int opacity = (int) (Math.sqrt(delta) * 255);

        // Increase the shot speed with time
        float dx = x2 - x;
        float dy = y2 - y;
        float passed = (float) Math.pow(Math.min((1 - delta) * 2, 1), 4);
        x2 = x + dx * passed;
        y2 = y + dy * passed;

        boolean isHit = tower.isLastShotHit();
        if (isHit) {
            paintBlurredBackground(g2d, new WorldPosition(x2, y2), delta);
            drawLine(g2d, x, y, x2, y2, delta, new Color(255, 50, 100, opacity));
            drawLine(g2d, x, y, x2, y2, delta, new Color(50, 255, 100, opacity));
            drawLine(g2d, x, y, x2, y2, delta, new Color(100, 120, 255, opacity));
            drawProjectile(g2d, x2, y2, new Color(255, 255, 255, opacity));
        } else {
            drawLine(g2d, x, y, x, y, delta, new Color(0, 50, 60, opacity));
            drawLine(g2d, x, y, x, y, delta, new Color(50, 0, 60, opacity));
            drawLine(g2d, x, y, x, y, delta, new Color(30, 40, 0, opacity));
            drawProjectile(g2d, x, y, new Color(0, 0, 0, opacity));
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

    public int getRenderPadding() {
        return offset;
    }

    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.background = backgroundImage;
    }
}
