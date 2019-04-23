package main.java.by.bsu.kulich.game;

import lombok.Getter;
import lombok.Setter;
import main.java.by.bsu.kulich.game.elements.Pausable;
import main.java.by.bsu.kulich.game.elements.controller.GameController;
import main.java.by.bsu.kulich.game.elements.creator.GameLevelCreator;
import main.java.by.bsu.kulich.game.elements.entity.*;
import main.java.by.bsu.kulich.game.elements.observer.DefaultUpdator;
import main.java.by.bsu.kulich.game.elements.view.View;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Arkanoid extends JFrame implements Pausable {

    @Getter
    private GameDifficultyLevel gameDifficultyLevel = GameDifficultyLevel.LIGHT;

    @Getter
    private GameLevel gameLevel = GameLevel.BEGINNING;

    @Getter
    private Paddle paddle = new Paddle(View.WINDOW_WIDTH / 2.0, View.REAL_BOTTOM_WINDOW_BOUND - 20, gameDifficultyLevel);
    @Getter
    private Ball ball = new Ball(View.WINDOW_WIDTH / 2, View.REAL_BOTTOM_WINDOW_BOUND - 35, gameDifficultyLevel, paddle, this);
    private List<Block> blocks = new ArrayList<>();
    private GameLevelCreator gameLevelCreator = new GameLevelCreator(gameLevel);

    private DefaultUpdator defaultUpdator = new DefaultUpdator();

    @Getter
    private View view;
    @Getter
    private GameController gameController;

    @Getter
    @Setter
    private int amountOfBlocks;

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

    private Timer timer;

    public Arkanoid() {
        super("KABAYE INC. ARCANOID®");

        this.setUndecorated(true);

        view = new View(this);

        this.gameController = new GameController(this);

        this.addKeyListener(gameController);

        view.addListener(gameController);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        score = 0;
        amountOfBlocks = gameLevelCreator.getBlockAmount();
        setLives();

        gameLevelCreator.createNewMap(blocks);
        view.playMusic(gameLevel, gameDifficultyLevel);

        defaultUpdator.add(ball);
        defaultUpdator.add(paddle);
        defaultUpdator.add(view);
        run();
    }

    public void setGameDifficultyLevel(GameDifficultyLevel level) {
        this.gameDifficultyLevel = level;
        restart();
    }

    public void setGameLevel(GameLevel level) {
        this.gameLevel = level;
        view.playMusic(gameLevel, gameDifficultyLevel);
        restart();
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
                ball.setVelocityX(ball.getBallVelocity() * (bX - pX) / (pW / 2.0));
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
                view.update();
            }
        }
    }

    public void die() {
        ball.die();
        lives--;
        if (lives == 0) {
            loosed = true;
        } else {
            view.update();
        }
    }

    private void update() {
        /*ball.update();
        paddle.update();
        view.update();*/

        defaultUpdator.notifyObservers();

        testCollision(paddle, ball);

        for (int i = blocks.size() - 1; i >= 0; i--) {
            testCollision(blocks.get(i), ball);
            if (blocks.get(i).isDestroyed())
                blocks.remove(blocks.get(i));
        }

    }

    private void run() {
        running = true;

        view.start();
        view.start();
        view.start();
        view.start();

        Arkanoid arkanoid = this;
        timer = new Timer("timer1");

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    if (won) {
                        view.won();
                    } else if (loosed) {
                        view.loosed();
                    } else {
                        update();
                        view.drawScene(ball, blocks, paddle);
                    }
                } else {
                    dispatchEvent(new WindowEvent(arkanoid, WindowEvent.WINDOW_CLOSING));
                }

            }
        };

        TimerTask starting = new TimerTask() {
            @Override
            public void run() {
                view.showAllHotKeysDialog();
                timer.schedule(task, 3500, 4);
            }
        };

        TimerTask starting1 = new TimerTask() {
            @Override
            public void run() {
                view.drawMainImage();
                timer.schedule(starting, 1500);
            }
        };
        timer.schedule(starting1, 2000);
    }

    public void restart() {
        won = false;
        ball.setGameDifficultyLevel(this.getGameDifficultyLevel());
        paddle.setGameDifficultyLevel(this.getGameDifficultyLevel());
        gameLevelCreator.setGameLevel(this.getGameLevel());
        ball.die();
        setLives();
        this.continueGame();
        loosed = false;
        score = 0;
        view.update();
        amountOfBlocks = gameLevelCreator.getBlockAmount();
        gameLevelCreator.createNewMap(blocks);

        gameController.setNextLevel(false);
    }

    private void setLives() {
        switch (gameDifficultyLevel) {
            case LIGHT:
                lives = 7;
                break;
            case MEDIUM:
                lives = 5;
                break;
            case HARD:
                lives = 3;
                break;
            case VERY_HARD:
                lives = 2;
                break;
            case YOU_ARE_GOD:
                lives = 1;
                break;
        }
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
