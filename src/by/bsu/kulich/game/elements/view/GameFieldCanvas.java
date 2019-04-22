package by.bsu.kulich.game.elements.view;

import by.bsu.kulich.game.elements.entity.Ball;
import by.bsu.kulich.game.elements.entity.Block;
import by.bsu.kulich.game.elements.entity.Paddle;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

import static by.bsu.kulich.game.elements.view.View.WINDOW_HEIGHT;
import static by.bsu.kulich.game.elements.view.View.WINDOW_WIDTH;

@Getter
class GameFieldCanvas extends Canvas {
    private final static String FONT = "Arial";

    private Font font;

    GameFieldCanvas() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        font = new Font(FONT, Font.PLAIN, 12);
        //this.setVisible(true);
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
}
