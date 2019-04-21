package by.bsu.kulich.game.elements;

import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

public class Paddle extends Rectangle {

    private static final double PADDLE_WIDTH = 110.0;
    private static final double PADDLE_HEIGHT = 17.0;
    private static final double PADDLE_SIMPLE_STEP = 0.8;
    private final double WINDOW_WIDTH;

    @Setter
    private double velocityValue = 0.6;
    private double velocity = 0.0;

    @Setter
    private GameDifficultyLevel gameDifficultyLevel;

    public Paddle(double x, double y, double windowWidth, @NonNull GameDifficultyLevel difficultyLevel) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        switch (difficultyLevel) {
            case LIGHT:
                this.setSizeX(PADDLE_WIDTH);
                break;
            case MEDIUM:
                this.setSizeX(PADDLE_WIDTH - 15.0);
                break;
            case HARD:
                this.setSizeX(PADDLE_WIDTH - 30.0);
                this.setVelocityValue(0.55);
                break;
            case VERY_HARD:
                this.setSizeX(PADDLE_WIDTH - 45.0);
                this.setVelocityValue(0.50);
                break;
            case YOU_ARE_GOD:
                this.setSizeX(PADDLE_WIDTH - 60.0);
                this.setVelocityValue(0.42);
                break;
        }
        WINDOW_WIDTH = windowWidth;
        gameDifficultyLevel = difficultyLevel;
    }

    public void update() {
        this.setX(this.getX() + velocity * PADDLE_SIMPLE_STEP);
    }

    public void stopMove() {
        velocity = 0.0;
    }

    public void moveLeft() {
        if (left() > 0.0) {
            velocity = -velocityValue;
        } else {
            velocity = 0.0;
        }
    }

    public void moveRight() {
        if (right() < WINDOW_WIDTH) {
            velocity = velocityValue;
        } else {
            velocity = 0.0;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) (left()), (int) (top()), (int) getSizeX(), (int) getSizeY());
    }
}
