package entity.visual;

public interface IObserver {

    void updateObserver(IObservable context, IObservable.PlayerEvent event);

}