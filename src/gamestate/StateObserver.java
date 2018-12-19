package gamestate;



public interface StateObserver {
    void updateObserver(GameStateManager.State state);
}
