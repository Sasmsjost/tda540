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
    private static final int SHADER_SIZE = 80;
    private static final float lifetime = 1000;

    private Tower tower;
    private World world;
    private BufferedImage background;
    private Color[] colors;

    public JBeam(Tower tower, World world, BufferedImage background) {
        this.background = background;
        this.tower = tower;
        this.world = world;

        colors = new Color[10];
        for (int i = 0; i < 10; i++) {
            colors[i] = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
        }
    }

    /**
     * Software shader, not too optimized but runnable on runnable hardware
     */
    private void paintBlurredBackground(Graphics2D g2d, WorldPosition to, double delta) {

        // Convert 0-1 > 0-1-0
        double ampCurve = (1 - Math.pow(delta * 2 - 1, 2));
        int maxDist = (int) (SHADER_SIZE * SHADER_SIZE * ampCurve);

        int size = (int) Math.min(maxDist * 0.05, SHADER_SIZE);
        g2d.setColor(new Color(255, 255, 255, (int) (255 * ampCurve)));
        g2d.setStroke(new BasicStroke(8));
        g2d.drawOval((int) (to.getX() - size / 2), (int) (to.getY() - size / 2), size, size);

        for (int i = 0; i < SHADER_SIZE * 2 / SHADER_ACCURACY; i++) {
            for (int j = 0; j < SHADER_SIZE * 2 / SHADER_ACCURACY; j++) {
                int x = (int) (to.getX() - SHADER_SIZE + i * SHADER_ACCURACY);
                int y = (int) (to.getY() - SHADER_SIZE + j * SHADER_ACCURACY);

                float dx = to.getX() - x;
                float dy = to.getY() - y;
                double dist = dx * dx + dy * dy + Math.sin(dx) * Math.cos(dy) * 500 * delta;

                // Only distort things within a certain distance
                if (dist > maxDist) {
                    continue;
                }

                // Invert distance when calculating the amplitude
                double invDist = (maxDist - dist) / maxDist;
                invDist = Math.pow(invDist, 2);
                double amp = invDist * ampCurve;
                double n = world.now() / 100f;

                // The distortion function, just something random which looks reasonable
                double ox = Math.cos(n * 2 + amp * 20) * amp * 20 + Math.sin(n + amp) * amp * 20;
                double oy = Math.sin(n + amp * 5) * amp * 20 + Math.cos(n + amp) * amp * 20;

                // Does only work for positive values
                ox = Math.abs(ox);
                oy = Math.abs(oy);

                // Ensure that we don't venture outside the image bounds
                int wx = (int) Math.round(x + ox);
                int wy = (int) Math.round(y + oy);
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

                // Fill the current pixel with the distorted color
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

        float x = (from.getX() + 0.5f) * Texture.TILE_SIZE;
        float y = (from.getY() + 0.5f) * Texture.TILE_SIZE;
        float x2 = (to.getX() + 0.5f) * Texture.TILE_SIZE;
        float y2 = (to.getY() + 0.5f) * Texture.TILE_SIZE;

        // Calculate delta value from shot started with respect to lifetime
        long now = world.now();
        double delta = 1f - Math.min(now - tower.getLastShot(), lifetime) / lifetime;

        // Increase the shot speed with time
        float passed = (float) Math.pow(Math.min((1 - delta) * 2, 1), 4);
        float dx = x2 - x;
        float dy = y2 - y;

        float dl = (float) Math.sqrt(dx * dx + dy * dy);

        // Calculate from and to positions
        float fx = x + dx / dl * 30f;
        float fy = y + dy / dl * 30f;
        float tx = fx + (dx - dx / dl * 30f) * passed;
        float ty = fy + (dy - dy / dl * 30f) * passed;

        // Delta going from 0 to 0
        float nDelta = (float) ((1 - Math.pow(delta * 2 - 1, 2)) * 0.2);
        int opacity = (int) (Math.sqrt(nDelta) * 255);

        boolean isHit = tower.isLastShotHit();
        if (isHit) {
            if (background != null) {
                paintBlurredBackground(g2d, new WorldPosition(tx, ty), nDelta);
            }

            for (Color c : colors) {
                opacity -= 50;
                opacity = Math.max(opacity, 0);
                drawLine(g2d, fx, fy, tx, ty, nDelta, new Color(c.getRed(), c.getGreen(), c.getBlue(), opacity));
            }
            if (background == null) {
                drawProjectile(g2d, tx, ty, delta, new Color(255, 255, 255, opacity));
            }
        } else {
            nDelta *= 0.1;
            drawLine(g2d, fx, fy, fx, fy, nDelta, new Color(0, 50, 60, opacity));
            drawLine(g2d, fx, fy, fx, fy, nDelta, new Color(50, 0, 60, opacity));
            drawLine(g2d, fx, fy, fx, fy, nDelta, new Color(30, 40, 0, opacity));
        }
    }

    private void drawProjectile(Graphics2D g2d, float x, float y, double delta, Color color) {
        int size = 20;
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(5));
        g2d.fillOval((int) x - size / 2, (int) y - size / 2, size, size);
    }

    private void drawLine(Graphics2D g2d, float x, float y, float x2, float y2, double delta, Color color) {
        g2d.setColor(color);

        float cx = (float) ((Math.random() - 0.5) * delta) * getWidth();
        float cy = (float) ((Math.random() - 0.5) * delta) * getHeight();

        Path2D.Float path = new Path2D.Float();
        path.moveTo(x, y);
        path.curveTo(x, y, x + cx, y + cy, x2, y2);
        g2d.setStroke(new BasicStroke(8));
        g2d.draw(path);
    }

    public void setSampleImage(BufferedImage backgroundImage) {
        this.background = backgroundImage;
    }
}
