package by.bsu.kulich.game;

import by.bsu.kulich.game.elements.Pausable;
import by.bsu.kulich.game.elements.controller.GameController;
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
import static by.bsu.kulich.game.elements.view.View.REAL_BOTTOM_WINDOW_BOUND;
import static by.bsu.kulich.game.elements.view.View.WINDOW_WIDTH;

public class Arcanoid extends JFrame implements Pausable {

    @Getter
    private GameDifficultyLevel gameDifficultyLevel = GameDifficultyLevel.MEDIUM;

    @Getter
    private GameLevel gameLevel = GameLevel.BEGINNING;

    @Getter
    private Paddle paddle = new Paddle(WINDOW_WIDTH / 2.0, REAL_BOTTOM_WINDOW_BOUND - 20, gameDifficultyLevel);
    @Getter
    private Ball ball = new Ball(WINDOW_WIDTH / 2, REAL_BOTTOM_WINDOW_BOUND - 40, gameDifficultyLevel);
    private List<Block> blocks = new ArrayList<>();
    @Getter
    private View view;
    private GameController gameController;

    @Getter
    @Setter
    private int amountOfBlocks = 80;

    @Setter
    @Getter
    private boolean running;

    @Setter
    @Getter
    private boolean loosed;

    @Setter
    @Getter
    private boolean won;

    @Getter
    private int score;
    @Getter
    private int lives;

    private Arcanoid() {
        super("KABAYE INC. ARCANOID®");

        view = new View(this);

        this.gameController = new GameController(this);

        this.addKeyListener(gameController);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        score = 0;
        lives = 2;

        initializeBricks();
    }

    public static void main(String[] args) {
        new Arcanoid().run();
    }

    public void setGameDifficultyLevel(GameDifficultyLevel level) {
        this.gameDifficultyLevel = level;
        restart();
    }

    public void setGameLevel(GameLevel level) {
        this.gameLevel = level;
        restart();
    }

    private void initializeBricks() {
        blocks.clear();

        for (int iX = 0; iX < 16; ++iX) {
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
                die();
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
                score++;
            }

            /**
             * collision of ball and block (bottom side of block)
             */
            else if ((Math.abs(bX - blX) <= blW / 2.0) && ((bY - blY) >= blH / 2.0)) {
                ball.setVelocityY(-ball.getVelocityY());
                block.setDestroyed(true);
                score++;
            }

            /**
             * collision of ball and block (right side of block)
             */
            else if (((bX - blX) >= blW / 2.0) && (Math.abs(bY - blY) <= blH / 2.0) && (bY < blY + blH / 2.0) && (bY > blY - blH / 2.0)) {
                ball.setVelocityX(-ball.getVelocityX());
                block.setDestroyed(true);
                score++;
            }

            /**
             * collision of ball and block (top side of block)
             */
            else if ((Math.abs(bX - blX) <= blW / 2.0) && ((bY - blY) <= -blH / 2.0)) {
                ball.setVelocityY(-ball.getVelocityY());
                block.setDestroyed(true);
                score++;
            }
            if (score == amountOfBlocks)
                won = true;
            else {
                view.updateScore();
            }
        }
    }

    public void die() {
        ball.die();
        lives--;
        if (lives == 0) {
            loosed = true;
        } else {
            view.updateScore();
        }
    }

    private void update() {
        ball.update(this, paddle);
        paddle.update();
        view.updateScore();

        testCollision(paddle, ball);

        for (int i = blocks.size() - 1; i >= 0; i--) {
            testCollision(blocks.get(i), ball);
            if (blocks.get(i).isDestroyed())
                blocks.remove(blocks.get(i));
        }

    }

    private void run() {
        running = true;
        view.showAllHotKeysDialog();
        while (running) {
            if (won) {
                view.won();
            } else if (loosed) {
                view.loosed();
            } else {
                update();
                view.drawScene(ball, blocks, paddle);
            }
        }
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public void restart() {
        ball.setGameDifficultyLevel(this.getGameDifficultyLevel());
        paddle.setGameDifficultyLevel(this.getGameDifficultyLevel());
        ball.die();
        lives = 2;
        won = false;
        loosed = false;
        score = 0;
        view.updateScore();
        initializeBricks();
    }

    @Override
    public void start() {
        ball.start();
        paddle.start();
    }

    @Override
    public void continueGame() {
        ball.continueGame();
        paddle.continueGame();
    }

    @Override
    public void pause() {
        ball.pause();
        paddle.pause();
    }
}
