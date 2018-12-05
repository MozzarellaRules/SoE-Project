package entity;

import tilemap.TileMap;

import java.awt.*;

import main.GamePanelController;

public abstract class Sprite {
	//tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	
	//position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	//dimensions
	protected int width;
	protected int height;
	
	//collision box
	protected int cwidth;
	protected int cheight;
	
	protected Animation animation;
	protected int currentAction;
	
	public Sprite(TileMap tm) {
		tileMap=tm;
		tileSize=tm.getTileSize();
		width = 32;
		height = 32;
		cwidth = 20;
		cheight = 20;
	}

	public Rectangle getRectangle() {
		return new Rectangle((int)x-cwidth,(int)y-cheight,cwidth,cheight);
	}
	public int getX() {
		return (int)x;
	}
	public int getY() {
		return (int)y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getCWidth() {
		return cwidth;
	}
	public int getCHeight() {
		return cheight;
	}
	
	public void setPosition(double x, double y) {
		this.x=x;
		this.y=y;
	}

	public boolean notOnScreen() {
		return x+tileMap.getX()+width<0||x+tileMap.getX()-width> GamePanelController.WIDTH||
				y+tileMap.getY()+height<0||y+tileMap.getY()-height> GamePanelController.HEIGHT;
	}
}
