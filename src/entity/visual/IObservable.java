package entity.visual;

public interface IObservable {

    enum PlayerEvent {
        HEALTH_MODIFIED,
        BULLETS_MODIFIED,
        OXYGEN_MODIFIED
    }

    void addObserver(IObserver obj);
    void deleteObserver(IObserver obj);
    void notifyObserver(PlayerEvent event);

}
