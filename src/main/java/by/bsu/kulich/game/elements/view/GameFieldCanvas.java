package by.bsu.kulich.game.elements.view;

import lombok.Getter;
import by.bsu.kulich.game.Arkanoid;
import by.bsu.kulich.game.elements.entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

import static by.bsu.kulich.game.elements.loader.ResourceLoader.getImage;
import static by.bsu.kulich.game.elements.view.View.*;

@Getter
class GameFieldCanvas extends Canvas {
    private final static String FONT = "Arial";

    private final String LEVEL_COMPLETED_IMAGE_PATH = "images/won.jpg";
    private final ImageIcon LEVEL_COMPLETED_IMAGE = new ImageIcon(getImage(LEVEL_COMPLETED_IMAGE_PATH));

    private final String LEVEL_COMPLETED_GOD_LEVEL_IMAGE_PATH = "images/level1.jpg";
    private final ImageIcon LEVEL_COMPLETED_GOD_LEVEL_IMAGE = new ImageIcon(getImage(LEVEL_COMPLETED_GOD_LEVEL_IMAGE_PATH));

    private final String LEVEL_LOOSED_IMAGE_PATH = "images/loose.jpg";
    private final ImageIcon LEVEL_LOOSED_IMAGE = new ImageIcon(getImage(LEVEL_LOOSED_IMAGE_PATH));

    private final String LEVEL_COMPLETED_GOD_IMAGE_PATH = "images/you_are_god.jpg";
    private final ImageIcon LEVEL_COMPLETED_GOD_IMAGE = new ImageIcon(getImage(LEVEL_COMPLETED_GOD_IMAGE_PATH));

    private final String MAIN_IMAGE_PATH = "images/arkanoid-logo.jpg";
    private final ImageIcon MAIN_IMAGE = new ImageIcon(getImage(MAIN_IMAGE_PATH));

    private final String LEVEL1_BACKGROUND_PATH = "images/fon1.jpg";
    private final ImageIcon LEVEL1_BACKGROUND = new ImageIcon(getImage(LEVEL1_BACKGROUND_PATH));

    private final String LEVEL2_BACKGROUND_PATH = "images/fon2.jpg";
    private final ImageIcon LEVEL2_BACKGROUND = new ImageIcon(getImage(LEVEL2_BACKGROUND_PATH));

    private final String LEVEL3_BACKGROUND_PATH = "images/fon3.jpg";
    private final ImageIcon LEVEL3_BACKGROUND = new ImageIcon(getImage(LEVEL3_BACKGROUND_PATH));

    private Font font;

    private Arkanoid arkanoid;

    GameFieldCanvas(Arkanoid arkanoid) {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        font = new Font(FONT, Font.PLAIN, 12);
        this.arkanoid = arkanoid;
    }

    void initBufferStrategy() {
        createBufferStrategy(2);
    }

    private void drawBall(Graphics g, Ball b) {
        g.setColor(b.getColor());
        g.fillOval((int) b.left(), (int) b.top(), (int) b.getRadius() * 2,
                (int) b.getRadius() * 2);
    }

    private void drawBallIfDied(Graphics g, Ball b, Paddle p) {
        g.setColor(b.getColor());
        g.fillOval((int) (p.getX() - b.getRadius()), (int) (p.getY() - p.getSizeY() / 2.0 - b.getRadius() * 2), (int) b.getRadius() * 2,
                (int) b.getRadius() * 2);
    }

    private void drawPaddle(Graphics g, Paddle p) {
        g.setColor(Color.BLUE);
        g.fillRect((int) (p.left()), (int) (p.top()), (int) p.getSizeX(), (int) p.getSizeY());
    }

    private void drawBlock(Graphics g, Block b) {
        g.setColor(Color.BLACK);
        g.drawRect((int) b.left(), (int) b.top(), (int) b.getSizeX(), (int) b.getSizeY());
        g.setColor(b.getColor());
        g.fillRect((int) b.left() + 1, (int) b.top() + 1, (int) b.getSizeX() - 2, (int) b.getSizeY() - 2);
    }

    private void drawScore(Graphics g, View view) {
        font = font.deriveFont(34f);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        int titleLen = fontMetrics.stringWidth(view.getText());
        int titleHeight = fontMetrics.getHeight();
        g.drawString(view.getText(), (WINDOW_WIDTH / 2) - (titleLen / 2),
                titleHeight + 10);
    }

    void drawScene(Ball ball, List<Block> blocks, Paddle paddle, View view, GameLevel level) {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;
        try {
            g = bf.getDrawGraphics();

//            g.setColor(Color.BLACK);
//            g.fillRect(0, 0, getWidth(), getHeight());

            switch (level) {
                case BEGINNING:
                    g.drawImage(LEVEL1_BACKGROUND.getImage(), 0, 0, null);
                    break;
                case MEDIUM:
                    g.drawImage(LEVEL2_BACKGROUND.getImage(), 0, 0, null);
                    break;
                case FINAL:
                    g.drawImage(LEVEL3_BACKGROUND.getImage(), 0, 0, null);
                    break;
            }

            if (ball.isDied())
                drawBallIfDied(g, ball, paddle);
            else
                drawBall(g, ball);

            drawPaddle(g, paddle);
            for (int i = 0; i < blocks.size(); i++) {
                drawBlock(g, blocks.get(i));
            }

            drawScore(g, view);

            // real size of our window
           /*g.setColor(Color.RED);
            g.drawRect((int)REAL_LEFT_WINDOW_BOUND,(int)REAL_TOP_WINDOW_BOUND,5,5);
            g.drawRect((int) REAL_RIGHT_WINDOW_BOUND-5, (int)REAL_BOTTOM_WINDOW_BOUND-5,5,5);*/
        } finally {
            g.dispose();
        }

        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    private boolean[] easterEgg = {false, false, false};

    @Getter
    private boolean easterEggFlag;

    void drawLoosedScene(String text) {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;
        try {
            g = bf.getDrawGraphics();

            g.drawImage(LEVEL_LOOSED_IMAGE.getImage(), 0, 0, null);

            g = bf.getDrawGraphics();
            font = font.deriveFont(25f);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            g.setColor(Color.RED);
            g.setFont(font);

            int titleLen = fontMetrics.stringWidth(text);
            int titleHeight = fontMetrics.getHeight();
            g.drawString(text, (WINDOW_WIDTH / 2) - (titleLen / 2),
                    titleHeight + 5);

        } finally {
            g.dispose();
        }

        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    void drawWonScene(GameDifficultyLevel level, GameLevel gameLevel, String text) {
        if (level == GameDifficultyLevel.YOU_ARE_GOD) {
            if (gameLevel == GameLevel.BEGINNING && !arkanoid.getGameController().isNextLevel()) {
                easterEgg[0] = true;
            } else if (gameLevel == GameLevel.MEDIUM && !arkanoid.getGameController().isNextLevel()) {
                easterEgg[1] = true;
            } else if (gameLevel == GameLevel.FINAL && !arkanoid.getGameController().isNextLevel()) {
                easterEgg[2] = true;
            }
        }
        easterEggFlag = true;
        for (int i = 0; i < 3; i++)
            if (!easterEgg[i])
                easterEggFlag = false;

        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;
        try {
            g = bf.getDrawGraphics();
            font = font.deriveFont(24f);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            g.setColor(Color.BLUE);
            g.setFont(font);
            int firstPart = text.indexOf('\n');

            int titleLen = fontMetrics.stringWidth(text.substring(0, firstPart));
            int titleLen1 = fontMetrics.stringWidth(text.substring(firstPart));
            int titleHeight = fontMetrics.getHeight();

            if (level == GameDifficultyLevel.YOU_ARE_GOD && gameLevel == GameLevel.FINAL && easterEggFlag && !arkanoid.getGameController().isNextLevel()) {
                g.drawImage(LEVEL_COMPLETED_GOD_IMAGE.getImage(), 0, 0, null);
            } else if (level == GameDifficultyLevel.YOU_ARE_GOD && gameLevel == GameLevel.FINAL) {
                g.drawImage(LEVEL_COMPLETED_GOD_LEVEL_IMAGE.getImage(), 0, 0, null);
                g.drawString("Вы прошли последний уровень! 10Q за игру!!!", WINDOW_WIDTH / 2 - 300, 40);
            } else if (level == GameDifficultyLevel.YOU_ARE_GOD) {
                g.drawImage(LEVEL_COMPLETED_GOD_LEVEL_IMAGE.getImage(), 0, 0, null);
                g.drawString(text.substring(0, firstPart), (WINDOW_WIDTH / 2) - (titleLen / 2),
                        titleHeight + 5);
            } else if (gameLevel == GameLevel.FINAL) {
                g.drawImage(LEVEL_COMPLETED_IMAGE.getImage(), 0, 0, null);
                g.drawString(text.substring(firstPart), (WINDOW_WIDTH / 2) - (titleLen1 / 2),
                        2 * titleHeight + 5);
            } else {
                g.drawImage(LEVEL_COMPLETED_IMAGE.getImage(), 0, 0, null);
                g.drawString(text.substring(0, firstPart), (WINDOW_WIDTH / 2) - (titleLen / 2),
                        titleHeight + 5);
                g.drawString(text.substring(firstPart), (WINDOW_WIDTH / 2) - (titleLen1 / 2),
                        2 * titleHeight + 5);
            }
        } finally {
            g.dispose();
        }

        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public void drawMainImage() {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;
        try {
            g = bf.getDrawGraphics();

            g.drawImage(MAIN_IMAGE.getImage(), 0, 0, null);

        } finally {
            g.dispose();
        }

        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public void start() {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g;
        try {
            g = bf.getDrawGraphics();

            g.setColor(Color.BLACK);

            g.fillRect(0, 0, REAL_RIGHT_WINDOW_BOUND, REAL_BOTTOM_WINDOW_BOUND);

            g.dispose();
        } catch (Exception e) {

        }
        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void repaint() {

    }
}
