package entity.visual;

public interface PlayerObservable {

    enum PlayerEvent {
        HEALTH_MODIFIED,
        BULLETS_MODIFIED,
        OXYGEN_MODIFIED
    }

    void addObserver(PlayerObserver obj);
    void deleteObserver(PlayerObserver obj);
    void notifyObserver(PlayerEvent event);

}
