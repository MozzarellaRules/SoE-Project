package gamestate;

import java.awt.event.KeyEvent;

public abstract class GameState {

	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
	
}
