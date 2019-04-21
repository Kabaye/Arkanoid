package by.bsu.kulich;

public abstract class AbstractGameElement {

    /**
     * all methods counts border of game element
     *
     * @return double, which contain coordinate of border
     * of game element
     */

    abstract double left();

    abstract double right();

    abstract double top();

    abstract double bottom();

}
