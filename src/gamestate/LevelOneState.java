package gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.*;
import main.GamePanelController;
import tilemap.*;


public class LevelOneState extends GameState {
	public static String BG_PATH = "/Background/full_background2.jpeg";
	public static String TILESET_PATH = "/Tilesets/tileset_sarah.png";
	public static String MAP_PATH = "/Maps/map_sarah.txt";

	private GameStateManager gsm;
	
	private TileMap tileMap;
	private Background bg;

	private LevelOnePlayer player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Ammo> ammo;
	private ArrayList<Projectile> projectiles;
	private Health health;

	public LevelOneState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap(32);
		tileMap.loadTiles(TILESET_PATH);
		tileMap.loadMap(MAP_PATH);

		this.player = LevelOneSpriteFactory.getInstance().createPlayer(tileMap);
		this.enemies = LevelOneSpriteFactory.getInstance().createEnemies(tileMap);
		this.ammo = LevelOneSpriteFactory.getInstance().createAmmo(tileMap);
		this.health = LevelOneSpriteFactory.getInstance().createHealth(tileMap);
		this.projectiles = new ArrayList<>();

		player.addObserver(health);

		bg = new Background(BG_PATH,0.5);

		// The camera is centered on the player
		tileMap.setPosition(GamePanelController.WIDTH/2-player.getX(), GamePanelController.HEIGHT/2-player.getY());
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

		for(Ammo a : ammo) {
			a.update();
			if(player.intersects(a)) {
				ammo.remove(a);
				player.gatherAmmo();
				break;
			}
		}

		for(Projectile p : projectiles) {
			p.update();
			checkEnemyHit(p);
			if(p.shouldRemove()) {
				projectiles.remove(p);
				break;
			}
		}

		health.update();
	}

	private void checkEnemyHit(Projectile p){
		for(Enemy e : enemies){
			if(e.intersects(p)) {
				e.hit(1);
				p.setRemove(true);
			}

			if (e.isDead()){
				enemies.remove(e);
			}

			break;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);

		player.draw(g);
		health.draw(g);

		for(Enemy e : enemies) {
			e.draw(g);
		}
		for(Ammo a : ammo) {
			a.draw(g);
		}
		for(Projectile p : projectiles) {
			p.draw(g);
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
			if(player.getRemainingBullets() > 0) {
				Projectile p = LevelOneSpriteFactory.getInstance().createProjectile(tileMap, player);
				projectiles.add(p);
				player.setFiring(true);
				player.setRemainingBullets(player.getRemainingBullets()-1);
			}
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
