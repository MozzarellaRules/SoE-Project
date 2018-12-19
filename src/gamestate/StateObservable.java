package gamestate;

public interface StateObservable {

    void addObserver(StateObserver obj);
    void deleteObserver(StateObserver obj);
    void notifyObserver(GameStateManager.State state);

}
