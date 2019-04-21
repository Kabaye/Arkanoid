package by.bsu.kulich;

import java.awt.*;

import static by.bsu.kulich.Arcanoid.FT_STEP;
import static by.bsu.kulich.Arcanoid.WINDOW_WIDTH;

public class Paddle extends Rectangle {

    public static final double PADDLE_WIDTH = 120.0;
    public static final double PADDLE_HEIGHT = 17.0;
    public static final double PADDLE_VELOCITY = 0.5;

    double velocity = 0.0;

    public Paddle(double x, double y) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    void update() {
        this.setX(this.getX() + velocity * FT_STEP);
    }

    void stopMove() {
        velocity = 0.0;
    }

    void moveLeft() {
        if (left() > 0.0) {
            velocity = -PADDLE_VELOCITY;
        } else {
            velocity = 0.0;
        }
    }

    void moveRight() {
        if (right() < WINDOW_WIDTH) {
            velocity = PADDLE_VELOCITY;
        } else {
            velocity = 0.0;
        }
    }

    void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) (left()), (int) (top()), (int) getSizeX(), (int) getSizeY());
    }
}
