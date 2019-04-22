package by.bsu.kulich.game.elements.view;

import by.bsu.kulich.game.elements.entity.Ball;
import by.bsu.kulich.game.elements.entity.Block;
import by.bsu.kulich.game.elements.entity.Paddle;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

public class GameField extends Canvas {

    private final int WINDOW_WIDTH;
    private final int WINDOW_HEIGHT;

    public GameField(int width, int height) {
        WINDOW_WIDTH = width;
        WINDOW_HEIGHT = height;
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void drawScene(Ball ball, List<Block> blocks, Paddle paddle) {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;
        try {
            g = bf.getDrawGraphics();

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            if (ball.isDied())
                ball.drawIfDied(g, paddle);
            else
                ball.draw(g);
            paddle.draw(g);
            for (Block block : blocks) {
                block.draw(g);
            }

            /*
            // real size of our window

            g.setColor(Color.RED);
            g.drawRect((int)REAL_LEFT_WINDOW_BOUND,(int)REAL_TOP_WINDOW_BOUND,5,5);
            g.drawRect((int) REAL_RIGHT_WINDOW_BOUND-5, (int)REAL_BOTTOM_WINDOW_BOUND-5,5,5);*/
        } finally {
            g.dispose();
        }

        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }
}
