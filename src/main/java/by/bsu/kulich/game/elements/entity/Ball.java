package main.java.by.bsu.kulich.game.elements.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import main.java.by.bsu.kulich.game.Arkanoid;
import main.java.by.bsu.kulich.game.elements.Pausable;
import main.java.by.bsu.kulich.game.elements.view.View;

import java.awt.*;

@Getter
public class Ball extends AbstractGameElement implements Pausable {
    public static final double BALL_RADIUS = 7.0;
    private static final double BALL_SIMPLE_STEP = 1.0;

    private double x, y;
    private double radius = BALL_RADIUS;
    @Setter
    private double ballVelocity;
    @Setter
    private double velocityX;
    @Setter
    private double velocityY;
    private GameDifficultyLevel difficultyLevel;
    @Setter
    private Color color;

    private boolean pause;
    @Getter
    private boolean died = true;

    public Ball(int x, int y, @NonNull GameDifficultyLevel difficultyLevel) {
        this.x = x;
        this.y = y;
        this.difficultyLevel = difficultyLevel;
        this.setGameDifficultyLevel(difficultyLevel);
    }

    @Override
    public void start() {
        pause = false;
        died = false;
        this.velocityX = 0;
        this.velocityY = -this.ballVelocity;
    }

    @Override
    public void continueGame() {
        pause = false;
    }

    @Override
    public void pause() {
        pause = true;
    }

    public void die() {
        died = true;
    }

    public void update(Arkanoid arkanoid, Paddle paddle) {
        if (!pause && !died) {
            x += velocityX * BALL_SIMPLE_STEP;
            y += velocityY * BALL_SIMPLE_STEP;

            if (left() < View.REAL_LEFT_WINDOW_BOUND)
                velocityX = -velocityX;
            else if (right() > View.REAL_RIGHT_WINDOW_BOUND)
                velocityX = -velocityX;
            if (top() < View.REAL_TOP_WINDOW_BOUND) {
                velocityY = -velocityY;
            } else if (bottom() > View.REAL_BOTTOM_WINDOW_BOUND) {
                velocityY = -ballVelocity;
                arkanoid.die();
            }
        } else if (died && !pause) {
            x = paddle.getX();
            y = paddle.getY() - 2 * radius;
        }
    }

    public void setGameDifficultyLevel(GameDifficultyLevel level) {
        this.difficultyLevel = level;
        switch (this.difficultyLevel) {
            case LIGHT:
                this.setColor(Color.WHITE);
                this.setBallVelocity(1.5);
                break;
            case MEDIUM:
                this.setColor(Color.GREEN);
                this.setBallVelocity(1.7);
                break;
            case HARD:
                this.setColor(Color.RED);
                this.setBallVelocity(1.9);
                break;
            case VERY_HARD:
                this.setColor(Color.PINK);
                this.setBallVelocity(2.09);
                break;
            case YOU_ARE_GOD:
                this.setColor(Color.GRAY);
                this.setBallVelocity(2.2);
                break;
        }
    }

    @Override
    public double left() {
        return x - radius;
    }

    @Override
    public double right() {
        return x + radius;
    }

    @Override
    public double top() {
        return y - radius;
    }

    @Override
    public double bottom() {
        return y + radius;
    }

}
