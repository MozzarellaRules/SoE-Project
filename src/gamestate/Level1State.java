package gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.*;
import main.GamePanelController;
import tilemap.*;


public class Level1State extends GameState{
	public static String tilesetPath = "/Tilesets/tileset_sarah.png";
	public static String mapPath = "/Maps/map_sarah.txt";
	
	private TileMap tileMap;
	private Background bg;

	private FirstLevelPlayer firstLevelPlayer;
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
		tileMap.loadTiles(tilesetPath);
		tileMap.loadMap(mapPath);
		bg = new Background("/Background/full_background2.jpeg",0.5);

		// Creating main character, enemies and ammo
		firstLevelPlayer = new FirstLevelPlayer(tileMap);
		createEnemies();
		Ammo ammo1 = new Ammo(tileMap);
		Ammo ammo2 = new Ammo(tileMap);
		ammo.add(ammo1);
		//ammo.add(munition2);

		// Default positions
		firstLevelPlayer.setPosition(tileMap.getTileSize()*13,tileMap.getTileSize()*46);
		ammo1.setPosition(tileMap.getTileSize()*16,tileMap.getTileSize()*50+6);
		ammo2.setPosition(tileMap.getTileSize()*16,tileMap.getTileSize()*54+6);

		// The camera follows the character
		tileMap.setPosition(GamePanelController.WIDTH/2- firstLevelPlayer.getX(), GamePanelController.HEIGHT/2- firstLevelPlayer.getY());
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
		// Update firstLevelPlayer
		firstLevelPlayer.update();

		// The camera follows the character
		tileMap.setPosition(GamePanelController.WIDTH/2- firstLevelPlayer.getX(), GamePanelController.HEIGHT/2- firstLevelPlayer.getY());

		// The background moves with the character
		bg.setPosition(tileMap.getX(), 0);

		// If the firstLevelPlayer is dead... gameover
		if(firstLevelPlayer.isDead() || firstLevelPlayer.notOnScreen()){
			gsm.setState(GameStateManager.GAMEOVER);
		}

		// Update ammo
		for(int i=0; i<ammo.size(); i++) {
			ammo.get(i).update(); // Update animation
			if(firstLevelPlayer.intersects(ammo.get(i))) {
				ammo.remove(ammo.get(i));
			}
		}

		// Check if the character hit an enemy
		firstLevelPlayer.checkAttack(enemies);

        // Update enemies
		for(Enemy e : enemies){
			// If the enemy is not on the screen, he does not move
		    if(!e.notOnScreen()){
				e.update();
		    }

		    // If the enemy hit firstLevelPlayer, update the health of the character
		    if(e.intersects(firstLevelPlayer)){
		    	firstLevelPlayer.hit(1);
		    }
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);

		firstLevelPlayer.draw(g);
		for(Enemy e : enemies) { e.draw(g); }
		for(Ammo a : ammo) { a.draw(g); }
	}

	@Override
	public void keyPressed(int k) {
		if(k==KeyEvent.VK_LEFT) 
			firstLevelPlayer.setLeft(true);
		if(k==KeyEvent.VK_RIGHT)
			firstLevelPlayer.setRight(true);
		if(k==KeyEvent.VK_UP)
			firstLevelPlayer.setJumping(true);
		if(k==KeyEvent.VK_DOWN)
			firstLevelPlayer.setDown(true);
		if(k==KeyEvent.VK_SPACE) {
			firstLevelPlayer.setFiring(true);
		}
	}

	@Override
	public void keyReleased(int k) {
		if(k==KeyEvent.VK_LEFT)
			firstLevelPlayer.setLeft(false);
		if(k==KeyEvent.VK_RIGHT)
			firstLevelPlayer.setRight(false);
		if(k==KeyEvent.VK_UP)
		    firstLevelPlayer.setJumping(false);
		if(k==KeyEvent.VK_DOWN)
			firstLevelPlayer.setDown(false);
		if(k==KeyEvent.VK_SPACE)
			firstLevelPlayer.setFiring(false);
	}
}
