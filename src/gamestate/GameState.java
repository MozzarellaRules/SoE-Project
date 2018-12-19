package gamestate;


public abstract class GameState {

	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int keyCode);
	public abstract void keyReleased(int keyCode);
	
}
