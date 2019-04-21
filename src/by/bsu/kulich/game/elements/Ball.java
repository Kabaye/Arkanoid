package by.bsu.kulich.game.elements;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

@Getter
public class Ball extends AbstractGameElement implements Pausable {
    private static final double BALL_RADIUS = 10.0;
    private static final double BALL_SIMPLE_STEP = 0.8;

    private final double REAL_LEFT_WINDOW_BOUND;
    private final double REAL_RIGHT_WINDOW_BOUND;
    private final double REAL_TOP_WINDOW_BOUND;
    private final double REAL_BOTTOM_WINDOW_BOUND;

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
    private boolean died;

    public Ball(int x, int y, double realLeftWindowBound, double realTopWindowBound, double realRightWindowBound, double realBottomWindowBound, @NonNull GameDifficultyLevel difficultyLevel) {
        this.x = x;
        this.y = y;
        this.difficultyLevel = difficultyLevel;
        REAL_LEFT_WINDOW_BOUND = realLeftWindowBound;
        REAL_TOP_WINDOW_BOUND = realTopWindowBound;
        REAL_RIGHT_WINDOW_BOUND = realRightWindowBound;
        REAL_BOTTOM_WINDOW_BOUND = realBottomWindowBound;
        switch (this.difficultyLevel) {
            case LIGHT:
                this.setColor(Color.MAGENTA);
                this.setBallVelocity(0.3);
                break;
            case MEDIUM:
                this.setColor(Color.GREEN);
                this.setBallVelocity(0.32);
                break;
            case HARD:
                this.setColor(Color.RED);
                this.setBallVelocity(0.35);
                break;
            case VERY_HARD:
                this.setColor(Color.PINK);
                this.setBallVelocity(0.38);
                break;
            case YOU_ARE_GOD:
                this.setColor(Color.GRAY);
                this.setBallVelocity(0.41);
                break;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) left(), (int) top(), (int) radius * 2,
                (int) radius * 2);
    }

    @Override
    public void start() {
        pause = false;
        died = false;
        this.velocityX = this.ballVelocity;
        this.velocityY = this.ballVelocity;
    }

    @Override
    public void continueGame() {
        pause = false;
    }

    @Override
    public void pause() {
        pause = true;
    }

    public void die(Paddle paddle) {
        died = true;
        x = paddle.getX();
        y = paddle.getY() - 2 * radius;
    }

    public void update(Score score, Paddle paddle) {
        if (!pause && !died) {
            x += velocityX * BALL_SIMPLE_STEP;
            y += velocityY * BALL_SIMPLE_STEP;

            if (left() < REAL_LEFT_WINDOW_BOUND)
                velocityX = -velocityX;
            else if (right() > REAL_RIGHT_WINDOW_BOUND)
                velocityX = -velocityX;
            if (top() < REAL_TOP_WINDOW_BOUND) {
                velocityY = -velocityY;
            } else if (bottom() > REAL_BOTTOM_WINDOW_BOUND) {
                velocityY = -ballVelocity;
                die(paddle);
                score.die();
            }
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
