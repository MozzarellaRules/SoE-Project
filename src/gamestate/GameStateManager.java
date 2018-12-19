package gamestate;
import music.MusicHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameStateManager implements KeyListener,StateObservable {

	public enum State {
		MENUSTATE,
		LEVEL1STATE,
		LEVEL2STATE,
		GAMEOVER,
		DEMO,
		WINSTATE
	}

	private gamestate.GameState currentState;
	private ArrayList<StateObserver> observers;

	/**
	 * This method initialises the gamestatemanager by setting the observer array and the current state of the game.
	 */
	public GameStateManager()  {
		observers = new ArrayList<>();
		currentState = new LevelTwoState(this);
	}

	/**
	 *This method sets the current state.
	 * @param state is the current state.
	 */
	public void setState(State state) {
		switch(state) {
			case MENUSTATE:
				currentState = new MenuState(this);
				break;
			case LEVEL1STATE:
				currentState = new LevelOneState(this);
				break;
			case LEVEL2STATE:
				currentState = new LevelTwoState(this);
				break;
			case GAMEOVER:
				currentState = new GameOverState(this);
				break;
			case DEMO:
				currentState = new DemoState(this);
				break;
			case WINSTATE:
				currentState = new WinState(this);
				break;
		}
		notifyObserver(state);
		currentState.init();
	}

	/**
	 * This method updates the current state.
	 */
	public void update() {
		currentState.update();
	}

	/**
	 * This method calls the draw method on the current state.
	 * @param g is the Graphics2 java object used to draw.
	 */
	public void draw(java.awt.Graphics2D g) {
		currentState.draw(g);
	}

	/**
	 * This method is used to determine that a key has been pressed.
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		currentState.keyPressed(e.getKeyCode());
	}

	/**
	 * This metod is used to determine when and which key has been released
	 * @param e
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		currentState.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) { }

	/**
	 * This method adds an observer for a certain state (the current one, of course).
	 * @param obj is the observer.
	 */
	@Override
	public void addObserver(StateObserver obj) {
		observers.add(obj);
	}

	/**
	 * This method removes an observer from the array assigned to a game state.
	 * @param obj is the observer to delete.
	 */
	@Override
	public void deleteObserver(StateObserver obj) {
		observers.remove(obj);
	}

	/**
	 * This method is used to update one of the observers.
	 * @param state is the state to notify.
	 */
	@Override
	public void notifyObserver(State state) {
		for (StateObserver o:observers){
			o.updateObserver(state);
		}
	}

}
