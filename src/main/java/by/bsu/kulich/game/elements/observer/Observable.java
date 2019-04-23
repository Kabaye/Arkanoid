package main.java.by.bsu.kulich.game.elements.observer;

public interface Observable {
    void add(Observer o);

    void delete(Observer o);

    void notifyObservers();
}
