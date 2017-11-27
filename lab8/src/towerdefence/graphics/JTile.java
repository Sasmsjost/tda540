package towerdefence.graphics;

import towerdefence.World;
import towerdefence.util.TilePosition;
import towerdefence.util.WorldPosition;

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
    protected World world;

    public JTile(Image[] texture, World world) {
        super();
        this.texture = texture;
        this.world = world;
        setTilePosition(0,0);
        allTiles.add(this);
    }

    public void animate() {
        if (world.now() - lastAnimated > animationSpeed) {
            currentFrame = (currentFrame + 1) % texture.length;
            lastAnimated = world.now();
        }
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setTilePosition(WorldPosition pos) {
        setTilePosition(pos.getX(), pos.getY());
    }

    public void setTilePosition(TilePosition pos) {
        setTilePosition(pos.getX(), pos.getY());
    }


    private void setTilePosition(float x, float y) {
        int size = Texture.TILE_SIZE;
        int ix = Math.round(x*size);
        int iy = Math.round(y*size);
        setBounds(ix,iy,size, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(rotation, getWidth() / 2, getHeight() / 2);
        g2d.drawImage(texture[currentFrame], 0, 0, null);
    }
}
