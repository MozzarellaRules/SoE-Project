package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.*;
import Main.GamePanel;
import TileMap.*;


public class Level1State extends GameState{
	
	private TileMap tileMap;
	private Background bg;

	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Ammo> ammo;

	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		this.enemies = new ArrayList<>();
		this.projectiles = new ArrayList<>();
		this.ammo = new ArrayList<>();
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/tileset_sarah.png");
		tileMap.loadMap("/Maps/map_sarah.txt");
		tileMap.setTween(1);
		bg = new Background("/Background/full_background.jpeg",0.5);

		// Creating main character, enemies and ammo
		player = new Player(tileMap);
		createEnemies();
		Ammo munition1 = new Ammo(tileMap);
		Ammo munition2 = new Ammo(tileMap);
		ammo.add(munition1);
		//ammo.add(munition2);

		// Default positions
		player.setPosition(tileMap.getTileSize()*13,tileMap.getTileSize()*46);
		munition1.setPosition(tileMap.getTileSize()*16,tileMap.getTileSize()*50);
		munition2.setPosition(tileMap.getTileSize()*16,tileMap.getTileSize()*54);

		// The camera follows the character
		tileMap.setPosition(GamePanel.WIDTH/2-player.getx(), GamePanel.HEIGHT/2-player.gety());
	}

	private void createEnemies(){
		enemies = new ArrayList<>();

		Enemy e1 = new EnemyGround(tileMap);
		Enemy e2 = new EnemyGround(tileMap);

		e1.setPosition(tileMap.getTileSize()*14,tileMap.getTileSize()*46);
		e2.setPosition(tileMap.getTileSize()*14,tileMap.getTileSize()*54);

		enemies.add(e1);
		//enemies.add(e2);
	}

	@Override
	public void update() {
		// Update player
		player.update();

		// The camera follows the character
		tileMap.setPosition(GamePanel.WIDTH/2-player.getx(), GamePanel.HEIGHT/2-player.gety());

		// The background moves with the character
		bg.setPosition(tileMap.getx(), 0);

		// If the player is dead... gameover
		player.setMapPosition();
		if(player.isDead() || player.notOnScreen()){
			gsm.setState(GameStateManager.GAMEOVER);
		}

		// Check if the character hit an enemy
		player.checkAttack(enemies);

        // Update enemies
		for(Enemy e : enemies){
			// If the enemy is not on the screen, he does not move
		    if(!e.notOnScreen()){
				e.update();
		    }

		    // If the enemy hit player, update the health of the character
		    if(e.intersects(player)){
		    	player.hit(1);
		    }
		}

		// Update ammo
		for(int i=0; i<ammo.size(); i++) {
			ammo.get(i).update(); // Update position
			if(ammo.get(i).intersects(player)) {
				ammo.remove(ammo.get(i));
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);

		player.draw(g);
		for(Enemy e : enemies) { e.draw(g); }
		for(Ammo a : ammo) { a.draw(g); }
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
