package by.bsu.kulich;

import lombok.Data;

@Data
public class Rectangle extends AbstractGameElement {

    private double x, y;
    private double sizeX, sizeY;

    public Rectangle(double x, double y, double sizeX, double sizeY) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    double left() {
        return x - sizeX / 2.0;
    }

    @Override
    double right() {
        return x + sizeX / 2.0;
    }

    @Override
    double top() {
        return y - sizeY / 2.0;
    }

    @Override
    double bottom() {
        return y + sizeY / 2.0;
    }
}
