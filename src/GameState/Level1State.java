package GameState;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Entity.Player;
import Main.GamePanel;
import TileMap.*;

public class Level1State extends GameState{
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	public Level1State(GameStateManager gsm) {
		this.gsm=gsm;
		init();
	}

	@Override
	public void init() {
		tileMap=new TileMap(32);
		tileMap.loadTiles("/Tilesets/new_tileset.png");
		//tileMap.loadMap("/Maps/map1.txt");
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
		//clear screen
		/*g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);*/
		bg.draw(g);
		//draw tileMAp
		tileMap.draw(g);
		//player
		player.draw(g);
	}

	@Override
	public void keyPressed(int k) {
		if(k==KeyEvent.VK_LEFT) 
			player.setLeft(true);
			//player.move();
		if(k==KeyEvent.VK_RIGHT)
			player.setRight(true);
		if(k==KeyEvent.VK_UP)
			//player.setUp(true);
			player.setJumping(true);
		if(k==KeyEvent.VK_DOWN)
			player.setDown(true);
		if(k==KeyEvent.VK_SPACE) {
			player.setScratching();
			//player.setImage("Resources/Pirates/Pirate Captain (Shoot) GIF.gif");
		}
		if(k==KeyEvent.VK_E)
			player.setGliding(true);
		if(k==KeyEvent.VK_F)
			player.setFiring();
	}

	@Override
	public void keyReleased(int k) {
		if(k==KeyEvent.VK_LEFT)
			player.setLeft(false);
		if(k==KeyEvent.VK_RIGHT)
			player.setRight(false);
		if(k==KeyEvent.VK_UP)
			//player.setUp(false);
		    player.setJumping(false);
		if(k==KeyEvent.VK_DOWN)
			player.setDown(false);
		if(k==KeyEvent.VK_BACK_SPACE)
			player.setJumping(false);
		if(k==KeyEvent.VK_E)
			player.setGliding(false);
	}
}
