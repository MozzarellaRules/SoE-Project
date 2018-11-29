package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Enemy;
import Entity.EnemyGround;
import Entity.Player;
import Entity.Projectile;
import Main.GamePanel;
import TileMap.*;


public class Level1State extends GameState{
	
	private TileMap tileMap;
	private Background bg;
	private ArrayList<Enemy> enemies ;
	
	private Player player;
	private ArrayList<Projectile> projectiles = new ArrayList<>();

	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/new_tileset_test.png");
		tileMap.loadMap("/Maps/new_map.txt");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		bg = new Background("/Background/full_background.jpeg",0.5);
		player= new Player(tileMap);
		player.setPosition(100,100);
		enemies = new ArrayList<>();
		createEnemies();


	}

	@Override
	public void update() {

		player.update();

		tileMap.setPosition(GamePanel.WIDTH/2-player.getx(), GamePanel.HEIGHT/2-player.gety());

		//Movimento del Background in funzione del personaggio
		bg.setPosition(GamePanel.WIDTH-player.getx(),0);

        player.setMapPosition();

		for (Enemy e : enemies){
		    if(!e.notOnScreen()){
				e.update();}
		    	if(e.intersects(player)){
		    		player.hit();
		    		if(player.isDead()){
		    			gsm.setState(GameStateManager.GAMEOVER);
					}
				}

		}

		player.checkAttack(enemies);


	}


	private void createEnemies(){
		Enemy e1 = new EnemyGround(tileMap);
		//Enemy e2 = new EnemyGround(tileMap);

		e1.setPosition(150,player.gety()-64);
		//e2.setPosition(180,player.gety()-64);


		enemies.add(e1);
		//enemies.add(e2);



	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		player.draw(g);

		for(Enemy e: enemies){
			e.draw(g);

		}
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
