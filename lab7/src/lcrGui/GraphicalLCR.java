package lcrGui;

import lcrgame.Dice;
import lcrgame.Game;
import lcrgame.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by alex on 2015-11-20.
 */
public class GraphicalLCR extends JFrame {
    private static final Color ACTIVE_COLOR = new Color(228,160,90);
    private static final Color INACTIVE_COLOR = null;

    private JPanel rootPanel;
    private JButton rollButton;
    private JButton quitButton;
    private JPanel playerPanel;
    private JLabel resultLabel;
    private JPanel cmdPanel;
    private JPanel resultPanel;

    private Game lcrGame;
    private JButton restartButton;
    private HashMap<Player, PlayerUI> playerToUi = new HashMap<>();

    private class PlayerUI {
        public JLabel badgeLabel;
        public JPanel panel;

        PlayerUI(JPanel panel, JLabel badgeLabel) {
            this.panel = panel;
            this.badgeLabel = badgeLabel;
        }
    }

    public static void main(String[] args) {
        GraphicalLCR game = new GraphicalLCR();
    }

    public GraphicalLCR() {
        super("LCR game");

        restartGame();

        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        rollButton.addActionListener(e -> step());
        quitButton.addActionListener(e -> System.exit(0));

        pack();
        setVisible(true);
    }

    private void createPlayerUI() {
        int panelMargin = 20;
        int textMargin = 10;

        // Sice we allow the game to be restarted and
        // since the UI is connected to players, we
        // recreate the UI every new game
        playerToUi.clear();
        playerPanel.removeAll();

        for(Player player : lcrGame.getPlayers()) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(panelMargin, panelMargin, panelMargin, panelMargin));
            playerPanel.add(panel);

            JLabel nameLabel = new JLabel();
            nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0,textMargin,0));
            nameLabel.setText(player.getName());
            panel.add(nameLabel);

            JLabel badgeLabel = new JLabel();
            panel.add(badgeLabel);

            PlayerUI ui = new PlayerUI(panel, badgeLabel);
            playerToUi.put(player, ui);
        }

        render();
        // Has to be called after render since render overwrites the result label
        resultLabel.setText("Press roll to start game!");

        pack();
    }

    public void step() {
        resetGameIfWon();
        lcrGame.step();
        render();
    }

    private void resetGameIfWon() {
        if(lcrGame.getWinner() != null) {
            restartGame();
        }
    }

    private void restartGame(){
        lcrGame = buildLCRGame();
        createPlayerUI();
    }

    private static Game buildLCRGame() {
        int startingBadges = 3;

        Dice[] dices = new Dice[3];
        for (int i = 0; i < dices.length; i++) {
            dices[i] = new Dice();
        }

        Player[] players = new Player[]{
                new Player("Bengt-Arne", startingBadges),
                new Player("Per-Olof", startingBadges),
                new Player("Sibylla", startingBadges)
        };

        return new Game(players, dices);
    }

    private void render() {
        renderLastThrow();
        renderPlayers();
        renderWinner();
    }

    private void renderLastThrow() {
        String result = "";
        for(String res : lcrGame.getResult()) {
            result += res + " ";
        }
        resultLabel.setText(result);
    }

    private void renderPlayers() {
        for(Player player : lcrGame.getPlayers()) {
            PlayerUI ui = playerToUi.get(player);
            String badgeCount = String.format("Badges: %s", player.getBadgeCount());
            ui.badgeLabel.setText(badgeCount);

            if(lcrGame.getCurrentPlayer() == player) {
                ui.panel.setBackground(ACTIVE_COLOR);
            } else {
                ui.panel.setBackground(INACTIVE_COLOR);
            }
        }
    }

    private void renderWinner() {
        Player winner = lcrGame.getWinner();
        if(winner != null){
            resultLabel.setText(winner.getName() + " won! Press roll to restart game");
            pack();
        }
    }
}
