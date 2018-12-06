package gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.*;
import main.GamePanelController;
import tilemap.*;


public class Level1State extends GameState {
	public static String BG_PATH = "/Background/full_background2.jpeg";
	public static String TILESET_PATH = "/Tilesets/tileset_sarah.png";
	public static String MAP_PATH = "/Maps/map_sarah.txt";

	private GameStateManager gsm;
	
	private TileMap tileMap;
	private Background bg;

	private FirstLevelPlayer player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Ammo> ammo;

	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {
		this.enemies = new ArrayList<>();
		this.projectiles = new ArrayList<>();
		this.ammo = new ArrayList<>();

		tileMap = new TileMap(32);
		tileMap.loadTiles(TILESET_PATH);
		tileMap.loadMap(MAP_PATH);

		bg = new Background(BG_PATH,0.5);

		createPlayer();
		createEnemies();
		createStaticAmmo();

		// The camera is centered on the player
		tileMap.setPosition(GamePanelController.WIDTH/2-player.getX(), GamePanelController.HEIGHT/2-player.getY());
	}

	private void createPlayer() {
		player = new FirstLevelPlayer(tileMap);
		player.setPosition(tileMap.getTileSize()*13,tileMap.getTileSize()*45);
	}

	private void createEnemies(){
		Enemy e1 = new EnemyGround(tileMap);
		Enemy e2 = new EnemyGround(tileMap);

		e1.setPosition(tileMap.getTileSize()*14,tileMap.getTileSize()*50);
		e2.setPosition(tileMap.getTileSize()*14,tileMap.getTileSize()*54);

		enemies.add(e1);
		//enemies.add(e2);
	}

	private void createStaticAmmo() {
		Ammo ammo1 = new Ammo(tileMap);
		Ammo ammo2 = new Ammo(tileMap);

		ammo1.setPosition(tileMap.getTileSize()*16,tileMap.getTileSize()*48+6);
		ammo2.setPosition(tileMap.getTileSize()*16,tileMap.getTileSize()*51+6);

		ammo.add(ammo1);
		ammo.add(ammo2);
	}

	@Override
	public void update() {
		player.update();

		// The camera follows the character
		tileMap.setPosition(GamePanelController.WIDTH/2- player.getX(), GamePanelController.HEIGHT/2- player.getY());

		// The background moves with the character
		bg.setPosition(tileMap.getX(), 0);

		// If the player is dead... set state GameOver
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

		// Update and remove ammo if the player intersects them
		for(int i=0; i<ammo.size(); i++) {
			ammo.get(i).update(); // Update animation
			if(player.intersects(ammo.get(i))) {
				ammo.remove(ammo.get(i));
				player.gatherAmmo();
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);

		player.draw(g);
		for(Enemy e : enemies) {
			e.draw(g);
		}
		for(Ammo a : ammo) {
			a.draw(g);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
			player.setMovingLeft(true);
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			player.setMovingRight(true);
		else if(e.getKeyCode()==KeyEvent.VK_UP)
			player.setJumping(true);
		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
			player.setMovingDown(true);
		else if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			player.setFiring(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
			player.setMovingLeft(false);
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			player.setMovingRight(false);
		else if(e.getKeyCode()==KeyEvent.VK_UP)
		    player.setJumping(false);
		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
			player.setMovingDown(false);
		else if(e.getKeyCode()==KeyEvent.VK_SPACE)
			player.setFiring(false);
	}
}
