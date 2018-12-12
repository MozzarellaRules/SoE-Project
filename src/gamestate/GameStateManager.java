package gamestate;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameStateManager implements KeyListener {
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int GAMEOVER = 2;

	private ArrayList<GameState> states;
	private GameState currentState;
	
	public GameStateManager() {
		states = new ArrayList<>();
		states.add(new MenuState(this));
		states.add(new LevelOneState(this));
		states.add(new GameOverState(this));

		currentState = states.get(LEVEL1STATE);
	}
	
	public void setState(int state) {
		currentState = states.get(state);
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
		currentState.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentState.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) { }
}
