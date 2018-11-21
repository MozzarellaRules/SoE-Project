package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Player;
import Entity.Projectile;
import Main.GamePanel;
import TileMap.*;

public class Level1State extends GameState{
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private ArrayList<Projectile> projectiles = new ArrayList<>();

	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/new_tileset.png");
		tileMap.loadMap("/Maps/new_map4.txt");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		bg = new Background("/Background/full_background.jpeg",0.5);
		player= new Player(tileMap);
		player.setPosition(100,100);
	}

	@Override
	public void update() {
		player.update();

		tileMap.setPosition(GamePanel.WIDTH/2-player.getx(), GamePanel.HEIGHT/2-player.gety());
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		player.draw(g);
	}

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
		if(k==KeyEvent.VK_SPACE) {
			player.setFiring(true);
		}
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
