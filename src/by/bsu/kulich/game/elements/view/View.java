package by.bsu.kulich.game.elements.view;

import by.bsu.kulich.game.Arcanoid;
import by.bsu.kulich.game.elements.entity.Ball;
import by.bsu.kulich.game.elements.entity.Block;
import by.bsu.kulich.game.elements.entity.GameDifficultyLevel;
import by.bsu.kulich.game.elements.entity.Paddle;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class View {
    private final static String FONT = "Arial";

    public final static int WINDOW_WIDTH = 800;
    public final static int WINDOW_HEIGHT = 700;

    public final static int REAL_LEFT_WINDOW_BOUND = 0;
    public final static int REAL_RIGHT_WINDOW_BOUND = WINDOW_WIDTH - 15;
    public final static int REAL_TOP_WINDOW_BOUND = 1;
    public final static int REAL_BOTTOM_WINDOW_BOUND = WINDOW_HEIGHT - 61;

    private int score;
    private int lives;

    @Setter
    private int amountOfBlocks;

    private boolean win;
    private boolean gameOver;

    private JMenuBar menuBar;

    @Getter
    private String text = "";

    @Setter
    @Getter
    private GameDifficultyLevel difficultyLevel;

    private Font font;

    @Getter
    private Arcanoid arcanoid;

    private GameFieldCanvas gameFieldCanvas;

    public View(@NonNull GameDifficultyLevel difficultyLevel, Arcanoid arcanoid) {
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

        arcanoid.setLayout(new BorderLayout());
        JPanel canvasPanel = new JPanel(new BorderLayout());

        gameFieldCanvas = new GameFieldCanvas();
        arcanoid.setVisible(true);
        createMenu();

        canvasPanel.add(gameFieldCanvas);
        canvasPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        canvasPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        arcanoid.add(canvasPanel, BorderLayout.CENTER);
        gameFieldCanvas.initBufferStrategy();

        arcanoid.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        arcanoid.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        arcanoid.setResizable(false);
        arcanoid.setLocationRelativeTo(null);
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

    private void updateScoreboard() {
        text = "View: " + score + "  Lives: " + lives;
    }

    void draw(Graphics g) {
        font = font.deriveFont(34f);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        int titleLen = fontMetrics.stringWidth(text);
        int titleHeight = fontMetrics.getHeight();
        g.drawString(text, (arcanoid.getWidth() / 2) - (titleLen / 2),
                titleHeight + 5);
    }

    public void drawScene(Ball ball, List<Block> blocks, Paddle paddle) {
        gameFieldCanvas.drawScene(ball, blocks, paddle, this);
    }

    private void createMenu() {
        menuBar = new JMenuBar();

        JMenu menu = new JMenu("Меню");
        JMenuItem newGame = new JMenuItem("");
        JMenuItem changeDifficulty = new JMenuItem("");
        JMenuItem about = new JMenuItem("");

        menu.add(newGame);
        menu.add(changeDifficulty);
        menu.addSeparator();
        menu.add(about);

        menuBar.add(menu);

        arcanoid.setJMenuBar(menuBar);
    }

}
