package by.bsu.kulich.game.elements;

import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

public class Paddle extends Rectangle {

    private static final double PADDLE_WIDTH = 110.0;
    private static final double PADDLE_HEIGHT = 17.0;
    private static final double PADDLE_SIMPLE_STEP = 0.8;
    private final double REAL_LEFT_WINDOW_BOUND;
    private final double REAL_RIGHT_WINDOW_BOUND;


    @Setter
    private double velocityValue = 0.6;
    private double velocity = 0.0;

    @Setter
    private GameDifficultyLevel gameDifficultyLevel;

    public Paddle(double x, double y, double realLeftWindowBound, double realRightWindowBound, @NonNull GameDifficultyLevel difficultyLevel) {
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
        REAL_LEFT_WINDOW_BOUND = realLeftWindowBound;
        REAL_RIGHT_WINDOW_BOUND = realRightWindowBound;
        gameDifficultyLevel = difficultyLevel;
    }

    public void update() {
        if (left() < REAL_LEFT_WINDOW_BOUND && velocity < 0.0)
            velocity = 0.0;
        if (right() > REAL_RIGHT_WINDOW_BOUND && velocity > 0.0)
            velocity = 0.0;
        this.setX(this.getX() + velocity * PADDLE_SIMPLE_STEP);
    }

    public void stopMove() {
        velocity = 0.0;
    }

    public void moveLeft() {
        velocity = -velocityValue;
    }

    public void moveRight() {
        velocity = velocityValue;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) (left()), (int) (top()), (int) getSizeX(), (int) getSizeY());
    }
}
