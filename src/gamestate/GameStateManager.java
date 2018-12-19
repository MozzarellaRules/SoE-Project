package gamestate;
import music.MusicHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameStateManager implements KeyListener,StateObservable {


	public static enum State {
		MENUSTATE,
		LEVEL1STATE,
		LEVEL2STATE,
		GAMEOVER,
		DEMO
	}

	private ArrayList<gamestate.GameState> states;
	private gamestate.GameState currentState;
	private ArrayList<StateObserver> observers;
	
	public GameStateManager()  {
		observers = new ArrayList<>();
		currentState = new LevelOneState(this);
	}
	
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
		}
		notifyObserver(state);
		currentState.init();
	}
	
	public void update() {
		currentState.update();
	}

	public void draw(java.awt.Graphics2D g) {
		currentState.draw(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		currentState.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentState.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) { }


	@Override
	public void addObserver(StateObserver obj) {
		observers.add(obj);
	}

	@Override
	public void deleteObserver(StateObserver obj) {
		observers.remove(obj);
	}

	@Override
	public void notifyObserver(State state) {
		for (StateObserver o:observers){
			o.updateObserver(state);
		}
	}

}
