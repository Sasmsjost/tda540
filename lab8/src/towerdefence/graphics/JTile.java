package towerdefence.graphics;

import com.sun.istack.internal.NotNull;
import towerdefence.WorldPosition;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public  class JTile extends JComponent {
    public static List<JTile> allTiles = new LinkedList<>();

    private float rotation = 0;
    private Image[] texture;

    private int currentFrame = 0;
    private long lastAnimated = 0;
    protected int animationSpeed = 100;

    public JTile(@NotNull Image[] texture) {
        super();
        this.texture = texture;
        setTilePosition(0,0);
        allTiles.add(this);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void animate() {
        if(System.currentTimeMillis() - lastAnimated > animationSpeed) {
            currentFrame = (currentFrame + 1) % texture.length;
            lastAnimated = System.currentTimeMillis();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(rotation, getWidth() / 2, getHeight() / 2);
        g2d.drawImage(texture[currentFrame], 0, 0, null);
    }

    public void setTilePosition(WorldPosition pos) {
        setTilePosition(pos.getX(), pos.getY());
    }

    public void setTilePosition(float x, float y) {
        int size = Texture.TILE_SIZE;
        int ix = Math.round(x*size);
        int iy = Math.round(y*size);
        setBounds(ix,iy,size, size);
    }


}
