package gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import entity.dynamic.*;
import entity.Item;
import entity.strategy.*;
import entity.visual.Health;
import entity.visual.RemainingBullets;
import main.GamePanelController;
import music.MusicHandler;
import tilemap.*;


public class LevelOneState extends GameState {
	public static String BG_PATH = "/Background/bg_level_one.jpeg";
	public static String TILESET_PATH = "/Tilesets/tileset_level_one.png";
	public static String MAP_PATH = "/Maps/map_level_one.txt";

	private GameStateManager gsm;
	private TileMap tileMap;
	private Background bg;

	private PlayerGround player;
	private ArrayList<EnemyGround> enemies;
	private ArrayList<Projectile> projectiles;

	private Health health;
	private RemainingBullets remainingBullets;

	private ArrayList<Item> ammo;
	private Item treasureMap;

	/**
	 * This method sets the game state manager for the first level.
	 * @param gsm is the game state manager.
	 */
	public LevelOneState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	/**
	 * This method returns a player character instance.
	 * @return the player character.
	 */
	public PlayerGround getPlayer() { return player; }

	/**
	 * This method returns the tilemap, which is the actual map things are drawn on.
	 * @return the map.
	 */
	public TileMap getTileMap() { return tileMap; }

	/**
	 * This method initializes the map, the player and the enemies, plus the ammo pick-ups.
	 * It also starts the background music.
	 */
	@Override
	public void init() {
		tileMap = new TileMap(32);
		tileMap.loadTiles(TILESET_PATH);
		tileMap.loadMap(MAP_PATH);

		this.enemies = new ArrayList<>();
		this.projectiles = new ArrayList<>();
		this.ammo = new ArrayList<>();

		createPlayer();
		createRemainingBullets();
		createHealth();
		createEnemies();
		createAmmo();
		createTreasureMap();

		bg = new Background(BG_PATH,0.5);

		// The camera is centered on the player
		tileMap.setPosition(GamePanelController.WIDTH/2-player.getX(), GamePanelController.HEIGHT/2-player.getY());
		gsm.addObserver(MusicHandler.getInstance());

	}

	/**
	 * This method is used to create a new player character.
	 */
	private void createPlayer() {
		this.player = new PlayerGround(tileMap);
		this.player.setPosition(tileMap.getTileSize()*13,tileMap.getTileSize()*45);
	}

	/**
	 * This method is used to create the starting bullets the player character can use.
	 */
	private void createRemainingBullets() {
		this.remainingBullets = new RemainingBullets(tileMap);
		this.remainingBullets.setRemainingBullets(player.getRemainingBullets());
		this.player.addObserver(remainingBullets);
	}

	/**
	 * This method sets the remaining health for the newly generated player character.
	 */
	private void createHealth() {
		this.health = new Health(tileMap);
		this.health.setHealth(player.getHealth());
		this.player.addObserver(health);
	}

	/**
	 * This method creates an array of enemies set in different positions.
	 */
	private void createEnemies() {
		EnemyFactory enemyFactory = EnemyFactoryConcrete.getInstace();
		int pos[][] = {
				{48,14},
				{51,15},
				{51,121},
				{51,52},
				{51,68},
				{51,81},
				{48,95},
				{48,118}
		};

		try {
			for(int i=0; i<pos.length; i++) {
				enemies.add((EnemyGround) enemyFactory.createEnemy(EnemyFactory.EnemyType.PIRATE,tileMap,pos[i][0],pos[i][1]));
			}
		}
		catch (InvalidParameterException e){
			System.err.println("Invalid Enemy Creation Type");
		}
	}

	/**
	 * This method creates the ammo crates.
	 */
	private void createAmmo() {
		int pos[][] = {
				{49,12},
				{52,16},
				{52,60}
		};

		for(int i=0; i<pos.length; i++) {
			Item a = new Item(tileMap, "/Objects/asset_ammo.png", 6);
			a.setPosition(tileMap.getTileSize()*pos[i][1],tileMap.getTileSize()*pos[i][0]-a.getHeight()/2);
			ammo.add(a);
		}
	}

	/**
	 * This method creates a treasure map, that will end the first level and start the second one.
	 */
	private void createTreasureMap() {
		treasureMap = new Item(tileMap, "/Objects/asset_map.png", 12);
		treasureMap.setPosition(tileMap.getTileSize()*122,tileMap.getTileSize()*52-treasureMap.getHeight()/2);
	}

	/**
	 * This method creates a new projectile in front of the player and adds it to the projectiles currently on the screen.
	 */
	public void createProjectile() {
		Projectile p = new Projectile(tileMap, player.isFacingRight());
		p.setPosition(player.getX(), player.getY());
		projectiles.add(p);
	}

	/**
	 * This method deals with the camera, the position of the background and the animation for every sprite on the map.
	 */
	@Override
	public void update() {
		player.update();

		// The camera follows the character
		tileMap.setPosition(GamePanelController.WIDTH/2-player.getX(), GamePanelController.HEIGHT/2-player.getY());

		// The background moves with the character
		bg.setPosition(tileMap.getX(), 0);

		// If the player is dead... set state GameOver
		if(player.isDead() || player.notOnScreen()){
			gsm.setState(GameStateManager.State.GAMEOVER);
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

		for(Item a : ammo) {
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
		treasureMap.update();

		checkWin();
	}

	/**
	 * This method checks if an enemy has been hit by a projectile
	 * @param p is the projectile.
	 */
	private void checkEnemyHit(Projectile p){
		for(EnemyGround e : enemies){
			if(p.intersects(e)) {
				e.hit(1);
				p.setRemove(true);
			}

			if (e.isDead()){
				enemies.remove(e);
				break;
			}
		}
	}

	/**
	 * This method checks if the treasure map has been acquired, and therefore the second level
	 * can be started.
	 */
	private void checkWin() {
		if(player.intersects(treasureMap)) {
			gsm.setState(GameStateManager.State.LEVEL2STATE);
		}
	}

	/**
	 * This method draws the map and all the sprites within it.
	 * @param g
	 */
	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);

		player.draw(g);
		health.draw(g);
		remainingBullets.draw(g);
		treasureMap.draw(g);

		for(Item a : ammo) {
			//Rectangle r = a.getRectangle();
			//g.fillRect((int)r.getX()+tileMap.getX(),(int)r.getY()+tileMap.getY(), (int)r.getWidth(), (int)r.getHeight());
			a.draw(g);
		}
		for(Projectile p : projectiles) {
			p.draw(g);
		}
		for(EnemyGround e : enemies) {
			//Rectangle r = e.getRectangle();
			//g.fillRect((int)r.getX()+tileMap.getX(),(int)r.getY()+tileMap.getY(), (int)r.getWidth(), (int)r.getHeight());
			e.draw(g);
		}
	}

	/**
	 * This method handles what key has been pressed (and therefore what action the main character is performing).
	 * @param keyCode is the key that has been pressed.
	 */
	@Override
	public void keyPressed(int keyCode) {
		switch(keyCode) {
			case KeyEvent.VK_LEFT:
				player.setMovingLeft(true);
				player.setMovingRight(false);
				player.setFacingRight(false);
				player.setStrategyX(StrategyFactory.getInstance().getMoveStrategyX());
				break;

			case KeyEvent.VK_RIGHT:
				player.setMovingRight(true);
				player.setMovingLeft(false);
				player.setFacingRight(true);
				player.setStrategyX(StrategyFactory.getInstance().getMoveStrategyX());
				break;

			case KeyEvent.VK_UP:
				if(!player.isFalling()){ // No jump in mid-air
					player.setStrategyY(StrategyFactory.getInstance().getMoveStrategyY());
					player.setFalling(false);
				}
				break;

			case KeyEvent.VK_SPACE:
				if(player.getRemainingBullets() > 0) {
					createProjectile();
					player.setFiring(true);
					player.setRemainingBullets(player.getRemainingBullets()-1);
				}
				break;
		}
	}

	/**
	 * This method tells what key has been released (and therefore which action to stop).
	 * @param keyCode is the key that has been released.
	 */
	@Override
	public void keyReleased(int keyCode) {
		switch(keyCode) {
			case KeyEvent.VK_LEFT:
				if(!player.isMovingRight())
					player.setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
				player.setMovingLeft(false);
				break;

			case KeyEvent.VK_RIGHT:
				if(!player.isMovingLeft())
					player.setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
				player.setMovingRight(false);
				break;

			case KeyEvent.VK_UP:
				player.setStrategyY(StrategyFactory.getInstance().getMoveStrategyY());
				player.setFalling(true);
				break;

			case KeyEvent.VK_SPACE:
				player.setFiring(false);
				break;
		}
	}
}
