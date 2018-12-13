package entity;

public interface IObserver {

    void updateObserver(IObservable context, IObservable.PlayerEvent event);

}
