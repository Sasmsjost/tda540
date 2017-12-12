package towerdefence.graphics;

import towerdefence.World;
import towerdefence.go.Tower;
import towerdefence.util.WorldPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

/**
 * Stateless effect for tower shots since they are only simulated using a random value,
 * not using ray tracing or physics.
 */
final class JBeam extends JComponent {
    private static final int SHADER_ACCURACY = 2;
    private static final int SHADER_SIZE = 80;

    private Tower tower;
    private World world;
    private BufferedImage sampleImage;
    private Color[] colors;
    private BufferedImage lensDistiontionImage = new BufferedImage(getShaderSize(), getShaderSize(), BufferedImage.TYPE_INT_ARGB);

    JBeam(Tower tower, World world, BufferedImage sampleImage) {
        this.sampleImage = sampleImage;
        this.tower = tower;
        this.world = world;

        colors = new Color[10];
        for (int i = 0; i < 10; i++) {
            colors[i] = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
        }
    }

    private static int getShaderSize() {
        return (int) (SHADER_SIZE * 2f / SHADER_ACCURACY);
    }

    /**
     * Software shader, not too optimized but runnable on reasonable hardware
     */
    private void paintLenseDistortion(Graphics2D g2d, WorldPosition to, double delta, double passed) {

        // Convert 0-1 > 0-1-0
        double ampCurve = (1 - Math.pow(delta * 2 - 1, 2));
        int maxDist = (int) (SHADER_SIZE * SHADER_SIZE * ampCurve * 1.5);

        // Make the color red around the time we hit the monster
        // And draw an oval with that color
        int size = (int) Math.min(maxDist * 0.15, SHADER_SIZE);
        if (passed > 0.5) {
            g2d.setColor(new Color(255, 255, 255, (int) (255 * ampCurve)));
        } else {
            int gb = (int) (passed * 255);
            g2d.setColor(new Color(255, gb, gb, (int) (255 * ampCurve)));
        }
        g2d.setStroke(new BasicStroke(5));
        g2d.drawOval((int) (to.getX() - size / 2), (int) (to.getY() - size / 2), size, size);

        for (int i = 0; i < getShaderSize(); i++) {
            for (int j = 0; j < getShaderSize(); j++) {
                int x = (int) (to.getX() - SHADER_SIZE + i * SHADER_ACCURACY);
                int y = (int) (to.getY() - SHADER_SIZE + j * SHADER_ACCURACY);

                float dx = to.getX() - x;
                float dy = to.getY() - y;

                // Ensure we never divide by 0
                double quote = Math.abs(y) / (Math.abs(x) + 1);
                // Change the distance based on where on the map we are to make the effect less "stiff"
                double offset = Math.atan(quote) * 500f;
                double dist = dx * dx + dy * dy + offset;

                // Only distort things within a certain distance
                if (dist > maxDist) {
                    // Make everything else transparent
                    lensDistiontionImage.setRGB(i, j, 0x00000000);
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

                // Ensure that we don't venture outside the image bounds
                int wx = (int) Math.round(x + ox);
                int wy = (int) Math.round(y + oy);
                if (wx > sampleImage.getWidth() - 1) {
                    wx = sampleImage.getWidth() - 1;
                } else if (wx < 0) {
                    wx = 0;
                }
                if (wy > sampleImage.getHeight() - 1) {
                    wy = sampleImage.getHeight() - 1;
                } else if (wy < 0) {
                    wy = 0;
                }

                // Sample colors and update the effect image
                int sampledColor = sampleImage.getRGB(wx, wy);
                lensDistiontionImage.setRGB(i, j, sampledColor);
            }
        }

        // Move the position so that the image is centered on the shader
        int x = (int) ((to.getX() - SHADER_SIZE));
        int y = (int) ((to.getY()) - SHADER_SIZE);
        g2d.drawImage(lensDistiontionImage, x, y, SHADER_SIZE * 2, SHADER_SIZE * 2, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (tower.getLastTarget() == null) {
            return;
        }
        float lifetime = tower.getShotDelay();
        Graphics2D g2d = (Graphics2D) g;

        WorldPosition from = tower.getPosition();
        WorldPosition to = tower.getLastTarget().getPosition();

        // Convert from world to texture coordinates
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
            if (sampleImage != null) {
                paintLenseDistortion(g2d, new WorldPosition(tx, ty), nDelta, delta);
            }

            for (Color c : colors) {
                opacity -= 50;
                opacity = Math.max(opacity, 0);
                drawLine(g2d, fx, fy, tx, ty, nDelta, new Color(c.getRed(), c.getGreen(), c.getBlue(), opacity));
            }
            if (sampleImage == null) {
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
        this.sampleImage = backgroundImage;
    }
}
