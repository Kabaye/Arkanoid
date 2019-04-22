package by.bsu.kulich.game;

import by.bsu.kulich.game.elements.controller.PaddleController;
import by.bsu.kulich.game.elements.entity.*;
import by.bsu.kulich.game.elements.view.View;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static by.bsu.kulich.game.elements.entity.Block.BLOCK_HEIGHT;
import static by.bsu.kulich.game.elements.entity.Block.BLOCK_WIDTH;

public class Arcanoid extends JFrame {

    @Getter
    private final static int WINDOW_WIDTH = 800;

    @Getter
    private final static int WINDOW_HEIGHT = 700;

    private final double REAL_LEFT_WINDOW_BOUND = 7.0;
    private final double REAL_RIGHT_WINDOW_BOUND = WINDOW_WIDTH - 8.0;
    private final double REAL_TOP_WINDOW_BOUND = 32.0;
    private final double REAL_BOTTOM_WINDOW_BOUND = WINDOW_HEIGHT - 30.0;

    @Getter
    private Paddle paddle = new Paddle(WINDOW_WIDTH / 2.0, REAL_BOTTOM_WINDOW_BOUND - 25.0, REAL_LEFT_WINDOW_BOUND, REAL_RIGHT_WINDOW_BOUND, GameDifficultyLevel.MEDIUM);
    @Getter
    private Ball ball = new Ball(WINDOW_WIDTH / 2, (int) REAL_BOTTOM_WINDOW_BOUND - 45,
            REAL_LEFT_WINDOW_BOUND, REAL_TOP_WINDOW_BOUND, REAL_RIGHT_WINDOW_BOUND, REAL_BOTTOM_WINDOW_BOUND, GameDifficultyLevel.MEDIUM);
    private List<Block> blocks = new ArrayList<>();
    private View view;
    private PaddleController controller;

    @Getter
    @Setter
    private int amountOfBlocks = 50;

    @Setter
    @Getter
    private boolean running;

    private Arcanoid() {
        super("KABAYE INC. ARCANOID®");

        view = new View(GameDifficultyLevel.MEDIUM, this);

        this.controller = new PaddleController(this);

        this.addKeyListener(controller);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeBricks();
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
            } else {
                ball.die();
                view.die();
            }
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
                view.increaseScore();
            }

            /**
             * collision of ball and block (bottom side of block)
             */
            else if ((Math.abs(bX - blX) <= blW / 2.0) && ((bY - blY) >= blH / 2.0)) {
                ball.setVelocityY(-ball.getVelocityY());
                block.setDestroyed(true);
                view.increaseScore();
            }

            /**
             * collision of ball and block (right side of block)
             */
            else if (((bX - blX) >= blW / 2.0) && (Math.abs(bY - blY) <= blH / 2.0) && (bY < blY + blH / 2.0) && (bY > blY - blH / 2.0)) {
                ball.setVelocityX(-ball.getVelocityX());
                block.setDestroyed(true);
                view.increaseScore();
            }

            /**
             * collision of ball and block (top side of block)
             */
            else if ((Math.abs(bX - blX) <= blW / 2.0) && ((bY - blY) <= -blH / 2.0)) {
                ball.setVelocityY(-ball.getVelocityY());
                block.setDestroyed(true);
                view.increaseScore();
            }
        }
    }

    private void update() {
        ball.update(view, paddle);
        paddle.update();

        testCollision(paddle, ball);

        for (int i = blocks.size() - 1; i >= 0; i--) {
            testCollision(blocks.get(i), ball);
            if (blocks.get(i).isDestroyed())
                blocks.remove(blocks.get(i));
        }

    }

    private void run() {
        running = true;
        while (running) {

            update();
                view.drawScene(ball, blocks, paddle);

        }

        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

    }
}
