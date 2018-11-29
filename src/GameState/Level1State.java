package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Player;
import Entity.Projectile;
import Main.GamePanel;
import TileMap.*;

public class Level1State extends GameState{
	
	//Il Livello 1
	
	private TileMap tileMap;// Oggetto mappa
	private Background bg;//Oggetto background
	private Player player2;
	private Player player; //Oggetto giocatore
	private ArrayList<Projectile> projectiles = new ArrayList<>(); //Array di proiettili per sparare

	
	//Costruttore
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap(32); //Creo la tileMap con grandezza dei Tile di 32
		tileMap.loadTiles("/Tilesets/new_tileset_test.png"); //Carico i tile
		tileMap.loadMap("/Maps/new_map.txt");//Carico la mappa
		tileMap.setPosition(0, 0);//Imposto la posizione iniziale della mappa
		tileMap.setTween(1); //Non serve a un beneamato niente
		bg = new Background("/Background/full_background.jpeg",0.5); //Background per livello 1
		player= new Player(tileMap);//Credo il personaggio
		player2=new Player(tileMap);
		player.setPosition(100,100);
		player2.setPosition(200,280);// Do una posizione iniziale al personaggio
	}

	//Quando il thread si aggiorna cos faccio? Aggiorno tutto ciò che devo aggiornare
	@Override
	public void update() {
			player.update();//Aggiorno quindi il personaggio(Se magari si è mosso o cose varie)
			 if(player.intersects(player2)) {
		        	player.hit();
		        }
			tileMap.setPosition(GamePanel.WIDTH/2-player.getx(), GamePanel.HEIGHT/2-player.gety());//Aggiorno la posizione della mappa per farla muovere col personaggio quando arriva a metà schermo
			if(player.isDead())
				gsm.setState(GameStateManager.GAMEOVER);
	}
		
	
	
	//Quando devo disegnare il livello uno devo disegnare il background, la mappa e il giocatore
	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		player.draw(g);
		player2.draw(g);
	}
	
	
	//Tasti da premere e il loro funzionamento sul livello 1
	@Override
	public void keyPressed(int k) {
		if(k==KeyEvent.VK_LEFT) 
			player.setLeft(true);
		if(k==KeyEvent.VK_RIGHT)
			player.setRight(true);
		if(k==KeyEvent.VK_UP)
			player.setJumping(true);
		if(k==KeyEvent.VK_DOWN)
			player.setDown(true);
		if(k==KeyEvent.VK_SPACE) 
			player.setFiring(true);
	}

	@Override
	public void keyReleased(int k) {
		if(k==KeyEvent.VK_LEFT)
			player.setLeft(false);
		if(k==KeyEvent.VK_RIGHT)
			player.setRight(false);
		if(k==KeyEvent.VK_UP)
		    player.setJumping(false);
		if(k==KeyEvent.VK_DOWN)
			player.setDown(false);
		if(k==KeyEvent.VK_SPACE)
			player.setFiring(false);
	}
	
}
