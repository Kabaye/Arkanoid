package by.bsu.kulich.game.elements;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

public class Score {
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

    @Setter
    @Getter
    private GameDifficultyLevel difficultyLevel;

    private Font font;

    public Score(@NonNull GameDifficultyLevel difficultyLevel, int amountOfBlocks) {
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
        this.amountOfBlocks = amountOfBlocks;
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
        text = "Score: " + score + "  Lives: " + lives;
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
}
