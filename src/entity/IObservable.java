package entity;

import java.util.Observer;

public interface IObservable {

    void addObserver(IObserver o);
    void deleteObserver(IObserver o);
    void notifyObserver();

}
