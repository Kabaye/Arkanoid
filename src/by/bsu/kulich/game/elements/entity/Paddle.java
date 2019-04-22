package by.bsu.kulich.game.elements.entity;

import by.bsu.kulich.game.elements.Pausable;
import lombok.NonNull;
import lombok.Setter;

public class Paddle extends Rectangle implements Pausable {

    private static final double PADDLE_WIDTH = 110.0;
    private static final double PADDLE_HEIGHT = 15.0;
    private static final double PADDLE_SIMPLE_STEP = 1.0;
    private final int REAL_LEFT_WINDOW_BOUND;
    private final int REAL_RIGHT_WINDOW_BOUND;

    private double velocityValue = 0.45;
    private double velocity = 0.0;

    @Setter
    private GameDifficultyLevel gameDifficultyLevel;

    private int direction;
    private boolean pause;

    public Paddle(double x, double y, int realLeftWindowBound, int realRightWindowBound, @NonNull GameDifficultyLevel difficultyLevel) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.gameDifficultyLevel = difficultyLevel;
        switch (this.gameDifficultyLevel) {
            case LIGHT:
                this.setSizeX(PADDLE_WIDTH);
                break;
            case MEDIUM:
                this.setSizeX(PADDLE_WIDTH - 15.0);
                break;
            case HARD:
                this.setSizeX(PADDLE_WIDTH - 30.0);
                this.velocityValue = 0.7;
                break;
            case VERY_HARD:
                this.setSizeX(PADDLE_WIDTH - 45.0);
                this.velocityValue = 0.6;
                break;
            case YOU_ARE_GOD:
                this.setSizeX(PADDLE_WIDTH - 60.0);
                this.velocityValue = 0.5;
                break;
        }
        REAL_LEFT_WINDOW_BOUND = realLeftWindowBound;
        REAL_RIGHT_WINDOW_BOUND = realRightWindowBound;

        start();
    }

    public void update() {
        if (!pause) {
            if (left() < REAL_LEFT_WINDOW_BOUND && velocity < 0.0)
                velocity = 0.0;
            if (right() > REAL_RIGHT_WINDOW_BOUND && velocity > 0.0)
                velocity = 0.0;
            this.setX(this.getX() + velocity * PADDLE_SIMPLE_STEP);
        }
    }


    public void stopMove() {
        this.velocity = 0.0;
    }

    public void moveLeft() {
        this.velocity = -this.velocityValue;
    }

    public void moveRight() {
        this.velocity = this.velocityValue;
    }

    @Override
    public void start() {
        velocity = 0.0;
        pause = false;
    }

    @Override
    public void continueGame() {
        pause = false;
    }

    @Override
    public void pause() {
        pause = true;
    }
}
