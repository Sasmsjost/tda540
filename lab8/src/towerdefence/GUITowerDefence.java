package towerdefence;

import towerdefence.graphics.Texture;
import towerdefence.levels.Level;
import towerdefence.levels.Level1;
import towerdefence.levels.Level2;

import javax.swing.*;
import java.awt.*;

public class GUITowerDefence extends JFrame {
    private static final Level[] levels = new Level[]{Level1.get(), Level2.get()};
    private int currentLevel = -1;
    private Game game;

    public static void main(String[] args) {
        Texture.load();
        new GUITowerDefence("Tower Defence").setVisible(true);
    }

    private GUITowerDefence(String title) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        nextLevel();
    }

    private boolean nextLevel() {
        assert (game == null);

        int nextLevelIndex = currentLevel + 1;
        boolean nextLevelExists = levels.length > nextLevelIndex;
        if (!nextLevelExists) {
            return false;
        }
        currentLevel = nextLevelIndex;

        game = new Game(levels[currentLevel]);
        game.attachTo(this);
        game.run();
        game.onWon(this::handleGameWon);
        game.onLost(this::handleGameLost);
        return true;
    }

    private void lostScene() {
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, getWidth(), getHeight());

        JLabel label = new JLabel();
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);

        panel.setBackground(new Color(200, 50, 50));
        label.setForeground(Color.WHITE);
        label.setText("You lost...");
        label.setBounds(0, 0, getWidth(), getHeight());

        panel.add(label);
        add(panel);

        repaint();
    }

    private void wonScene() {
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, getWidth(), getHeight());

        JLabel label = new JLabel();
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);

        panel.setBackground(new Color(50, 200, 50));
        label.setForeground(Color.WHITE);
        label.setText("You won!!!");
        label.setBounds(0, 0, getWidth(), getHeight());

        panel.add(label);
        add(panel);

        repaint();
    }

    private void handleGameWon() {
        game.dispose();
        if (nextLevel()) {
            return;
        }
        wonScene();
    }

    private void handleGameLost() {
        game.dispose();
        lostScene();
    }


}
