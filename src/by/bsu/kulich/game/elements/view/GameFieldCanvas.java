package by.bsu.kulich.game.elements.view;

import by.bsu.kulich.game.elements.entity.Ball;
import by.bsu.kulich.game.elements.entity.Block;
import by.bsu.kulich.game.elements.entity.GameDifficultyLevel;
import by.bsu.kulich.game.elements.entity.Paddle;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

import static by.bsu.kulich.game.elements.view.View.WINDOW_HEIGHT;
import static by.bsu.kulich.game.elements.view.View.WINDOW_WIDTH;

@Getter
class GameFieldCanvas extends Canvas {
    private final static String FONT = "Arial";
    private final String LEVEL_COMPLETED_IMAGE_PATH = "src/by/bsu/kulich/game/resources/won.jpg";
    private final ImageIcon LEVEL_COMPLETED_IMAGE = new ImageIcon(LEVEL_COMPLETED_IMAGE_PATH);

    private final String LEVEL_COMPLETED_GOD_LEVEL_IMAGE_PATH = "src/by/bsu/kulich/game/resources/level1.jpg";
    private final ImageIcon LEVEL_COMPLETED_GOD_LEVEL_IMAGE = new ImageIcon(LEVEL_COMPLETED_GOD_LEVEL_IMAGE_PATH);

    private final String LEVEL_LOOSED_IMAGE_PATH = "src/by/bsu/kulich/game/resources/loose.jpg";
    private final ImageIcon LEVEL_LOOSED_IMAGE = new ImageIcon(LEVEL_LOOSED_IMAGE_PATH);

    private Font font;

    GameFieldCanvas() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        font = new Font(FONT, Font.PLAIN, 12);
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
        g.setColor(b.getColor());
        g.fillRect((int) b.left(), (int) b.top(), (int) b.getSizeX(), (int) b.getSizeY());
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

    void drawScene(Ball ball, List<Block> blocks, Paddle paddle, View view) {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;
        try {
            g = bf.getDrawGraphics();

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            if (ball.isDied())
                drawBallIfDied(g, ball, paddle);
            else
                drawBall(g, ball);

            drawPaddle(g, paddle);
            for (Block block : blocks) {
                drawBlock(g, block);
            }

            drawScore(g, view);

            // real size of our window
           /* g.setColor(Color.RED);
            g.drawRect((int)REAL_LEFT_WINDOW_BOUND,(int)REAL_TOP_WINDOW_BOUND,5,5);
            g.drawRect((int) REAL_RIGHT_WINDOW_BOUND-5, (int)REAL_BOTTOM_WINDOW_BOUND-5,5,5);*/
        } finally {
            g.dispose();
        }

        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    void drawWonScene(GameDifficultyLevel level, String text) {
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

            if (level == GameDifficultyLevel.YOU_ARE_GOD) {
                g.drawImage(LEVEL_COMPLETED_GOD_LEVEL_IMAGE.getImage(), 0, 0, null);


                g.drawString(text.substring(0, firstPart), (WINDOW_WIDTH / 2) - (titleLen / 2),
                        titleHeight + 5);
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
}
