package by.bsu.kulich.game.elements.view;

import by.bsu.kulich.game.elements.entity.Ball;
import by.bsu.kulich.game.elements.entity.Block;
import by.bsu.kulich.game.elements.entity.Paddle;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

@Getter
public class GameField extends Canvas {

    public final int WINDOW_WIDTH;
    public final int WINDOW_HEIGHT;

    public final double REAL_LEFT_WINDOW_BOUND = 0.0;
    public final double REAL_RIGHT_WINDOW_BOUND;
    public final double REAL_TOP_WINDOW_BOUND = 1.0;
    public final double REAL_BOTTOM_WINDOW_BOUND;

    GameField(int width, int height) {
        WINDOW_WIDTH = width;
        WINDOW_HEIGHT = height;
        REAL_RIGHT_WINDOW_BOUND = WINDOW_WIDTH - 15.0;
        REAL_BOTTOM_WINDOW_BOUND = WINDOW_HEIGHT - 61.0;
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    void initBufferStrategy() {
        createBufferStrategy(2);
    }

    void drawScene(Ball ball, List<Block> blocks, Paddle paddle, View view) {
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

            view.draw(g);


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
