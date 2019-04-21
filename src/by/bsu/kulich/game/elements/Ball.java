package by.bsu.kulich.game.elements;

import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

public class Ball extends AbstractGameElement {
    private static final double BALL_RADIUS = 10.0;
    private static final double BALL_SIMPLE_STEP = 0.8;
    private final double WINDOW_WIDTH;
    private final double WINDOW_HEIGHT;

    private double x, y;
    private double radius = BALL_RADIUS;
    @Setter
    private double ballVelocity = 0.3;
    private double velocityX;
    private double velocityY;
    private GameDifficultyLevel difficultyLevel;
    @Setter
    private Color color;

    public Ball(int x, int y, double windowWidth, double windowHeight, @NonNull GameDifficultyLevel difficultyLevel) {
        this.x = x;
        this.y = y;
        this.difficultyLevel = difficultyLevel;
        WINDOW_HEIGHT = windowHeight;
        WINDOW_WIDTH = windowWidth;
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

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) left(), (int) top(), (int) radius * 2,
                (int) radius * 2);
    }

    public void update(/*ScoreBoard scoreBoard, */Paddle paddle) {
        x += velocityX * BALL_SIMPLE_STEP;
        y += velocityY * BALL_SIMPLE_STEP;

        if (left() < 0)
            velocityX = ballVelocity;
        else if (right() > WINDOW_WIDTH)
            velocityX = -ballVelocity;
        if (top() < 0) {
            velocityY = ballVelocity;
        } else if (bottom() > WINDOW_HEIGHT) {
            velocityY = -ballVelocity;
            x = paddle.getX();
            y = paddle.getY() - 2 * radius;
            //scoreBoard.die();
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
