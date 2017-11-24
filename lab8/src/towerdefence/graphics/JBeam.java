package towerdefence.graphics;

import towerdefence.WorldPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class JBeam extends JComponent {

    private long start = 0;
    private boolean isHit = false;
    private WorldPosition from;
    private WorldPosition to;
    private float lifetime = 1000;

    public JBeam() {
        super();
    }

    public void updateState(WorldPosition from, WorldPosition to, long start, boolean isHit) {
        this.from = from;
        this.to = to;
        this.start = start;
        this.isHit = isHit;
    }

    public void setFrom(WorldPosition from) {
        this.from = from;
    }

    public void setTo(WorldPosition to) {
        this.to = to;

    }

    @Override
    protected void paintComponent(Graphics g) {
        if (from == null || to == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;

        float x = from.getX();
        float y = from.getY();
        float x2 = to.getX();
        float y2 = to.getY();

        long now = System.currentTimeMillis();

        float delta = 1 - Math.min(now - start, lifetime) / lifetime;
        int opacity = (int) (delta * 255);

        if(isHit) {
            delta/=4;
            drawLine(g2d, x, y, x2, y2, delta, new Color(255, 50, 100, opacity));
            drawLine(g2d, x, y, x2, y2, delta, new Color(50, 255, 100, opacity));
            drawLine(g2d, x, y, x2, y2, delta, new Color(100, 120, 255, opacity));
        } else {
            drawLine(g2d, x, y, (float)(Math.random()*x2), (float)(Math.random()*y2), 1f, new Color(50, 50, 50, opacity));
        }
    }

    private void drawLine(Graphics2D g2d, float x, float y, float x2, float y2, float delta, Color color) {
        g2d.setColor(color);

        // 0->1 >> 0.5->1.5
        delta = (delta + 0.5f);

        float cx = (float) (Math.random()*delta) * getWidth();
        float cy = (float) (Math.random()*delta) * getHeight();

        Path2D.Float path = new Path2D.Float();
        path.moveTo(x, y);
        path.curveTo(x, y, cx, cy, x2, y2);
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(path);
    }
}
