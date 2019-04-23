package main.java.by.bsu.kulich.game.elements.observer;

import java.util.HashSet;
import java.util.Set;

public class DefaultUpdator implements Observable {
    Set<Observer> observers = new HashSet<>();

    @Override
    public void add(Observer o) {
        observers.add(o);
    }

    @Override
    public void delete(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (var elem : observers) {
            elem.update();
        }
    }
}
