package by.bsu.kulich.game.elements;

public abstract class AbstractGameElement {

    /**
     * all methods counts border of game element
     *
     * @return double, which contain coordinate of border
     * of game element
     */

    abstract public double left();

    abstract public double right();

    abstract public double top();

    abstract public double bottom();

}
