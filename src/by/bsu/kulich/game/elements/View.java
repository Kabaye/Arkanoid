package by.bsu.kulich.game.elements;

import by.bsu.kulich.game.Arcanoid;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class View extends JPanel {
    private final static String FONT = "Arial";
    private final static int WINDOW_WIDTH = 800;

    private int score;
    private int lives;

    @Setter
    private int amountOfBlocks;

    private boolean win;
    private boolean gameOver;

    @Getter
    private String text = "";

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem about;
    private JMenuItem startNew;
    private JMenuItem difficultyAndLevel;

    @Setter
    @Getter
    private GameDifficultyLevel difficultyLevel;

    private Font font;

    private Arcanoid arcanoid;

    public View(@NonNull GameDifficultyLevel difficultyLevel, Arcanoid arcanoid) {
        super();
        this.difficultyLevel = difficultyLevel;
        switch (this.difficultyLevel) {
            case LIGHT:
                lives = 8;
                break;
            case MEDIUM:
                lives = 6;
                break;
            case HARD:
                lives = 4;
                break;
            case VERY_HARD:
                lives = 2;
                break;
            case YOU_ARE_GOD:
                lives = 1;
                break;
        }
        score = 0;
        win = false;
        gameOver = false;
        font = new Font(FONT, Font.PLAIN, 12);
        this.arcanoid = arcanoid;
        this.amountOfBlocks = arcanoid.getAmountOfBlocks();
        this.setSize(arcanoid.getWidth(), arcanoid.getHeight());
        //this.setDoubleBuffered(true);
        //arcanoid.add(this);
    }

    private boolean checkWin() {
        return score == amountOfBlocks;
    }

    public void increaseScore() {
        score++;
        if (checkWin()) {
            win = true;
            text = "You win! You can try this level on higher difficulty, or go on next one.";
        } else {
            updateScoreboard();
        }
    }

    public void die() {
        lives--;
        if (lives <= 0) {
            gameOver = true;
            text = "You lose! You can try this level on lower difficulty!";
        } else {
            updateScoreboard();
        }
    }

    public void updateScoreboard() {
        text = "View: " + score + "  Lives: " + lives;
    }

    public void draw(Graphics g) {
        font = font.deriveFont(34f);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        int titleLen = fontMetrics.stringWidth(text);
        int titleHeight = fontMetrics.getHeight();
        g.drawString(text, (WINDOW_WIDTH / 2) - (titleLen / 2),
                titleHeight + 5);
    }

    public void menu() {

        menuBar = new JMenuBar();

        menu = new JMenu("Меню");

        startNew = new JMenuItem("Начать заново");
        about = new JMenuItem("О программе...");
        difficultyAndLevel = new JMenuItem("Выбрать сложность...");

        menu.add(startNew);
        menu.addSeparator();

        menu.add(difficultyAndLevel);
        menu.addSeparator();

        menu.add(about);

        menuBar.add(menu);

        arcanoid.setJMenuBar(menuBar);
    }
}
