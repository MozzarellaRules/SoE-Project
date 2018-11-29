package GameState;
import java.util.ArrayList;
public class GameStateManager {
	
	//Classe che gestisce lo scorrere dei livelli. Permette di passare da un livello ad un altro
	private ArrayList<GameState> gameStates; //Giustamente è un vettore di "GameState" cioè di livelli
	private int currentState; //tengo traccia del livello corrente
	public static final int MENUSTATE=0;  //Associo al menu il valore 0
	public static final int LEVEL1STATE=1;//Associo al livello 1 il valore 1
	public static final int GAMEOVER=2;
	
	
	//Costruttore
	public GameStateManager() {
		gameStates=new ArrayList<GameState>();
		currentState=MENUSTATE; //Dovrebbe essere MENUSTATE perché questo rappresenta la prima cos che vuoi caricare
		gameStates.add(new MenuState(this)); //Aggiungiamo il menu
		gameStates.add(new Level1State(this));// Aggiungiamo il primo livello
		gameStates.add(new GameOverState(this));
	}
	
	//metodo Setter per impostare il livello
	public void setState(int state) {
		currentState=state;
		gameStates.get(currentState).init();
	}
	
	//A seconda del livello in cui ci troviamo avrà un comportamento diverso
	public void update() {
		gameStates.get(currentState).update();
	}
	 //Come sopra
	public void draw(java.awt.Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	
	//Idem
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}
	//Uguale
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}
}
