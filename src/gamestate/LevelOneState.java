package gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.*;
import entity.strategy.*;
import main.GamePanelController;
import tilemap.*;


public class LevelOneState extends GameState {
	public static String BG_PATH = "/Background/full_background2.jpeg";
	public static String TILESET_PATH = "/Tilesets/tileset_sarah.png";
	public static String MAP_PATH = "/Maps/map_sarah.txt";

	private GameStateManager gsm;
	
	private TileMap tileMap;
	private Background bg;

	private PlayerGround player;
	private ArrayList<EnemyGround> enemies;
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

		this.enemies = new ArrayList<>();
		this.projectiles = new ArrayList<>();
		this.ammo = new ArrayList<>();

		createPlayer();
		createHealth();
		createEnemies();
		createAmmo();

		bg = new Background(BG_PATH,0.5);

		// The camera is centered on the player
		tileMap.setPosition(GamePanelController.WIDTH/2-player.getX(), GamePanelController.HEIGHT/2-player.getY());
	}

	public void createPlayer() {
		this.player = new PlayerGround(tileMap);
		this.player.setPosition(tileMap.getTileSize()*13,tileMap.getTileSize()*45);
	}

	public void createHealth() {
		this.health = new Health(tileMap);
		this.health.setHealth(player.getMaxHealth());
		this.player.addObserver(health);
	}

	public void createEnemies() {
		EnemyGround e1 = new EnemyGround(tileMap);
		EnemyGround e2 = new EnemyGround(tileMap);

		e1.setFacingRight(true);

		e1.setPosition(tileMap.getTileSize()*16,tileMap.getTileSize()*47);
		e2.setPosition(tileMap.getTileSize()*14,tileMap.getTileSize()*54);

		enemies.add(e1);
	}

	public void createAmmo() {
		Ammo ammo1 = new Ammo(tileMap);
		Ammo ammo2 = new Ammo(tileMap);

		ammo1.setPosition(tileMap.getTileSize()*16,tileMap.getTileSize()*48+6);
		ammo2.setPosition(tileMap.getTileSize()*16,tileMap.getTileSize()*51+6);

		ammo.add(ammo1);
		ammo.add(ammo2);
	}

	public void createProjectile() {
		Projectile projectile = new Projectile(tileMap, player.isFacingRight());
		projectile.setPosition(player.getX()-3, player.getY());
		projectiles.add(projectile);
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
		for(EnemyGround e : enemies){
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
		for(EnemyGround e : enemies){
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

		for(EnemyGround e : enemies) {
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
			player.setStrategyX(StrategyFactory.getInstance().getMoveLeftStrategy());
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			player.setStrategyX(StrategyFactory.getInstance().getMoveRightStrategy());
		else if(e.getKeyCode()==KeyEvent.VK_UP) {
			if(!player.isFalling()) // No jump in mid-air
				player.setStrategyY(StrategyFactory.getInstance().getJumpStrategy());
		}
		else if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			if(player.getRemainingBullets() > 0) {
				createProjectile();
				player.setFiring(true);
				player.setRemainingBullets(player.getRemainingBullets()-1);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
			player.setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			player.setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
		else if(e.getKeyCode()==KeyEvent.VK_UP)
		    player.setStrategyY(StrategyFactory.getInstance().getFallStrategy());
		else if(e.getKeyCode()==KeyEvent.VK_SPACE)
			player.setFiring(false);
	}
}
