package by.bsu.kulich.game.elements.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Rectangle extends AbstractGameElement {

    private double x, y;
    private double sizeX, sizeY;

    @Override
    public double left() {
        return x - sizeX / 2.0;
    }

    @Override
    public double right() {
        return x + sizeX / 2.0;
    }

    @Override
    public double top() {
        return y - sizeY / 2.0;
    }

    @Override
    public double bottom() {
        return y + sizeY / 2.0;
    }
}
