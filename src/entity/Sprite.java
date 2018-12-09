package entity;

import tilemap.TileMap;

import java.awt.*;

import main.GamePanelController;

public abstract class Sprite {
	protected TileMap tileMap;
	protected int tileSize;
	
	private int x;
	private int y;



	private double dx;
	private double dy;
	
	protected int width;
	protected int height;
	
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
		return x;
	}
	public int getY() {
		return y;
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
		this.x=(int)x;
		this.y=(int)y;
	}

	public boolean notOnScreen() {
		return  x + tileMap.getX() + width < 0 ||
				x + tileMap.getX() - width > GamePanelController.WIDTH ||
				y + tileMap.getY() + height < 0||
				y + tileMap.getY() - height > GamePanelController.HEIGHT;
	}



	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public void setX(int x){this.x = x;}

	public void setY(int y){this.y = y;}
}
