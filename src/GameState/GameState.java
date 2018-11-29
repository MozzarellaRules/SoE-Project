package GameState;

public abstract class GameState {
	
	//Classe astratta:tutti i livelli(compreso il menù) sono dei gameState e quindi avranno tutti i metodi sottostanti
	
	protected GameStateManager gsm;
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	
}
