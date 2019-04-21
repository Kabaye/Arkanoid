package by.bsu.kulich.game;

import by.bsu.kulich.game.elements.*;
import by.bsu.kulich.game.keyboard.PaddleController;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import static by.bsu.kulich.game.elements.Block.BLOCK_HEIGHT;
import static by.bsu.kulich.game.elements.Block.BLOCK_WIDTH;

public class Arcanoid extends JFrame {
    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 700;

    private final double REAL_LEFT_WINDOW_BOUND = 7.0;
    private final double REAL_RIGHT_WINDOW_BOUND = WINDOW_WIDTH - 8.0;
    private final double REAL_TOP_WINDOW_BOUND = 32.0;
    private final double REAL_BOTTOM_WINDOW_BOUND = WINDOW_HEIGHT - 8.0;

    private Paddle paddle = new Paddle(WINDOW_WIDTH / 2.0, WINDOW_HEIGHT - 25.0, REAL_LEFT_WINDOW_BOUND, REAL_RIGHT_WINDOW_BOUND, GameDifficultyLevel.MEDIUM);
    private Ball ball = new Ball(WINDOW_WIDTH / 2, WINDOW_HEIGHT - 45,
            REAL_LEFT_WINDOW_BOUND, REAL_TOP_WINDOW_BOUND, REAL_RIGHT_WINDOW_BOUND, REAL_BOTTOM_WINDOW_BOUND, GameDifficultyLevel.MEDIUM);
    private List<Block> blocks = new ArrayList<>();
    private PaddleController controller;

    @Setter
    @Getter
    private boolean running;

    private Arcanoid() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(false);
        this.setResizable(false);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        this.createBufferStrategy(2);

        this.controller = new PaddleController(paddle, this);

        this.addKeyListener(controller);

        initializeBricks();

        ball.setVelocityX(ball.getBallVelocity());
        ball.setVelocityY(ball.getBallVelocity());

    }

    public static void main(String[] args) {
        new Arcanoid().run();
    }

    private void initializeBricks() {
        blocks.clear();

        for (int iX = 0; iX < 10; ++iX) {
            for (int iY = 0; iY < 5; ++iY) {
                blocks.add(new Block((iX + 1) * (BLOCK_WIDTH + 3) + 22,
                        (iY + 2) * (BLOCK_HEIGHT + 3) + 20, GameLevel.BEGINNING));
            }
        }
    }

    private void drawScene(Ball ball, List<Block> blocks/*, ScoreBoard scoreboard*/) {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;
        try {
            g = bf.getDrawGraphics();

            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());

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

            //scoreboard.draw(g)
        } finally {
            g.dispose();
        }

        bf.show();
        Toolkit.getDefaultToolkit().sync();

    }

    private boolean isIntersecting(AbstractGameElement gameElement1, AbstractGameElement gameElement2) {
        return gameElement1.right() >= gameElement2.left() && gameElement1.left() <= gameElement2.right()
                && gameElement1.bottom() >= gameElement2.top() && gameElement1.top() <= gameElement2.bottom();
    }

    private void testCollision(Paddle paddle, Ball ball) {
        if (isIntersecting(paddle, ball)) {
            double bX = ball.getX();
            double bY = ball.getY();

            double pX = paddle.getX();
            double pY = paddle.getY();
            double pH = paddle.getSizeY();
            double pW = paddle.getSizeX();

            if ((Math.abs(bX - pX) <= pW / 2.0) && ((bY - pY) <= -pH / 2.0)) {
                ball.setVelocityY(-ball.getVelocityY());
                if (ball.getX() > paddle.getX())
                    ball.setVelocityX(ball.getBallVelocity() * (ball.getX() - paddle.getX()) / (paddle.getSizeX() / 2.0));
                else if (ball.getX() < paddle.getX())
                    ball.setVelocityX(ball.getBallVelocity() * (ball.getX() - paddle.getX()) / (paddle.getSizeX() / 2.0));
            } else
                System.out.println("LOOOOOSEEEEE");
        }
    }

    private void testCollision(Block block, Ball ball) {
        if (isIntersecting(block, ball)) {

            double bX = ball.getX();
            double bY = ball.getY();

            double blX = block.getX();
            double blY = block.getY();
            double blW = block.getSizeX();
            double blH = block.getSizeY();

            /**
             * collision of ball and block (left side of block)
             */
            if (((bX - blX) <= -blW / 2.0) && (Math.abs(bY - blY) <= blH / 2.0) && (bY < blY + blH / 2.0) && (bY > blY - blH / 2.0)) {
                ball.setVelocityX(-ball.getVelocityX());
                block.setDestroyed(true);
            }

            /**
             * collision of ball and block (bottom side of block)
             */
            else if ((Math.abs(bX - blX) <= blW / 2.0) && ((bY - blY) >= blH / 2.0)) {
                ball.setVelocityY(-ball.getVelocityY());
                block.setDestroyed(true);
            }

            /**
             * collision of ball and block (right side of block)
             */
            else if (((bX - blX) >= blW / 2.0) && (Math.abs(bY - blY) <= blH / 2.0) && (bY < blY + blH / 2.0) && (bY > blY - blH / 2.0)) {
                ball.setVelocityX(-ball.getVelocityX());
                block.setDestroyed(true);
            }

            /**
             * collision of ball and block (top side of block)
             */
            else if ((Math.abs(bX - blX) <= blW / 2.0) && ((bY - blY) <= -blH / 2.0)) {
                ball.setVelocityY(-ball.getVelocityY());
                block.setDestroyed(true);
            }
        }
    }

    private void update() {
        ball.update(/*scoreboard, */paddle);
        paddle.update();

        testCollision(paddle, ball);

        for (int i = blocks.size() - 1; i >= 0; i--) {
            testCollision(blocks.get(i), ball);
            if (blocks.get(i).isDestroyed())
                blocks.remove(blocks.get(i));
        }

    }

    private void run() {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = bf.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.dispose();
        bf.show();

        running = true;

        while (running) {
            update();
            drawScene(ball, blocks);
            paddle.update();
        }

        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

    }
}
