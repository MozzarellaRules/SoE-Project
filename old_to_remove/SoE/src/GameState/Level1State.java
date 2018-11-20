package GameState;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Main.GamePanel;
import TileMap.*;

public class Level1State extends GameState{
	
	private TileMap tileMap;
	private Background bg;
	
	public Level1State(GameStateManager gsm) {
		this.gsm=gsm;
		init();
	}

	@Override
	public void init() {
		tileMap=new TileMap(30);
		tileMap.loadTiles("/Tilesets/tilesetStart5.png");
		//tileMap.loadMap("/Maps/map1.txt");
		tileMap.loadMap("/Maps/map2.txt");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		bg = new Background("/Background/Red.png",0.1);
	}

	@Override
	public void update() {	
	}

	@Override
	public void draw(Graphics2D g) {
		//clear screen
		/*g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);*/
		bg.draw(g);
		//draw tileMAp
		tileMap.draw(g);
	}

	@Override
	public void keyPressed(int k) {
		
	}

	@Override
	public void keyReleased(int k) {
		
	}
}
