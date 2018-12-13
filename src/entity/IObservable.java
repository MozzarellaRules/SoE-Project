package entity;

import java.util.Observer;

public interface IObservable {

    enum PlayerEvent {
        HEALTH_MODIFIED,
        BULLETS_MODIFIED
    }

    void addObserver(IObserver obj);
    void deleteObserver(IObserver obj);
    void notifyObserver(PlayerEvent event);

}
