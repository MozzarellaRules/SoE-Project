package gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import entity.dynamic.*;
import entity.objects.Ammo;
import entity.objects.TreasureMap;
import entity.strategy.*;
import entity.visual.Health;
import entity.visual.RemainingBullets;
import main.GamePanelController;
import tilemap.*;


public class LevelOneState extends GameState {
	public static String BG_PATH = "/Background/full_background2.jpeg";
	public static String TILESET_PATH = "/Tilesets/tileset_sarah.png";
	public static String MAP_PATH = "/Maps/new_map.txt";

	private GameStateManager gsm;
	
	private TileMap tileMap;
	private Background bg;

	private PlayerGround player;
	private ArrayList<EnemyGround> enemies;
	private ArrayList<Ammo> ammo;
	private ArrayList<Projectile> projectiles;
	private Health health;
	private RemainingBullets remainingBullets;
	private TreasureMap treasureMap;

	private EnemyWaterOctopus octopus;

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
		createRemainingBullets();
		createHealth();
		createEnemies();
		createAmmo();
		createTreasureMap();

		bg = new Background(BG_PATH,0.5);

		// The camera is centered on the player
		tileMap.setPosition(GamePanelController.WIDTH/2-player.getX(), GamePanelController.HEIGHT/2-player.getY());
	}

	public void createPlayer() {
		this.player = new PlayerGround(tileMap);
		this.player.setPosition(tileMap.getTileSize()*13,tileMap.getTileSize()*45);
	}

	private void createRemainingBullets() {
		this.remainingBullets = new RemainingBullets(tileMap);
		this.remainingBullets.setRemainingBullets(player.getRemainingBullets());
		this.player.addObserver(remainingBullets);
	}

	public void createHealth() {
		this.health = new Health(tileMap);
		this.health.setHealth(player.getMaxHealth());
		this.player.addObserver(health);
	}

	public void createEnemies() {
		EnemyFactory enemyFactory = EnemyFactoryConcrete.getInstace();
		int pos[][] = {
				//{48,14},
				{51,15},
				{51,121},
				{51,52},
				{51,68},
				{48,95},
				{48,118},
		};

		try {
			for(int i=0; i<pos.length; i++) {
				enemies.add((EnemyGround) enemyFactory.createEnemy(EnemyFactory.EnemyType.PIRATE,tileMap,pos[i][0],pos[i][1]));
			}
		}
		catch (InvalidParameterException e){
			System.err.println("Invalid Enemy Creation Type");
		}

		octopus = new EnemyWaterOctopus(tileMap);
		octopus.setPosition(18*tileMap.getTileSize(), 48*tileMap.getTileSize());
	}

	public void createAmmo() {
		int pos[][] = {
				{48,16},
				{51,16}
		};

		for(int i=0; i<pos.length; i++) {
			Ammo a = new Ammo(tileMap);
			a.setPosition(tileMap.getTileSize()*pos[i][1],tileMap.getTileSize()*pos[i][0]+6);
			ammo.add(a);
		}
	}

	public void createTreasureMap() {
		treasureMap = new TreasureMap(tileMap);
		treasureMap.setPosition(tileMap.getTileSize()*121,tileMap.getTileSize()*51);
	}

	public void createProjectile() {
		Projectile p = new Projectile(tileMap, player.isFacingRight());
		p.setPosition(player.getX(), player.getY());
		projectiles.add(p);
	}

	@Override
	public void update() {
		player.update();
		octopus.update();

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
		remainingBullets.update();
		treasureMap.update();

		checkWin();
	}

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

	private void checkWin() {
		if(player.intersects(treasureMap)) {
			gsm.setState(GameStateManager.State.LEVEL2STATE);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);

		octopus.draw(g);

		player.draw(g);
		health.draw(g);
		remainingBullets.draw(g);
		treasureMap.draw(g);

		for(Ammo a : ammo) {
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

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				player.setStrategyX(StrategyFactory.getInstance().getMoveLeftStrategy());
				player.setMovingLeft(true);
				break;
			case KeyEvent.VK_RIGHT:
				player.setStrategyX(StrategyFactory.getInstance().getMoveRightStrategy());
				player.setMovingRight(true);
				break;
			case KeyEvent.VK_UP:
				if(!player.isFalling()) // No jump in mid-air
					player.setStrategyY(StrategyFactory.getInstance().getJumpStrategy());
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

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
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
				player.setStrategyY(StrategyFactory.getInstance().getFallStrategy());
				break;
			case KeyEvent.VK_SPACE:
				player.setFiring(false);
				break;
		}
	}
}
