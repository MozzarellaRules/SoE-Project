package entity.visual;

public interface PlayerObserver {

    void updateObserver(PlayerObservable context, PlayerObservable.PlayerEvent event);

}
