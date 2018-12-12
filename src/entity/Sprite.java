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
	protected int collisionBoxWidth;
	protected int collisionBoxHeight;
	
	protected ImageAnimator imageAnimator;
	protected int current_row;
	
	public Sprite(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
		width = 32;
		height = 32;
		collisionBoxWidth = 20;
		collisionBoxHeight = 20;
	}

	public Rectangle getRectangle() {
		return new Rectangle(
				(int)x- collisionBoxWidth,
				(int)y- collisionBoxHeight,
				collisionBoxWidth,
				collisionBoxHeight);
	}

	public void setPosition(double x, double y) {
		this.x = (int)x;
		this.y = (int)y;
	}

	public boolean notOnScreen() {
		return  x + tileMap.getX() + width < 0 ||
				x + tileMap.getX() - width > GamePanelController.WIDTH ||
				y + tileMap.getY() + height < 0||
				y + tileMap.getY() - height > GamePanelController.HEIGHT;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public double getDx() {
		return dx;
	}
	public double getDy() {
		return dy;
	}
	public int getWidth() {	return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return collisionBoxWidth; }
	public int getCHeight() { return collisionBoxHeight; }

	public void setDx(double dx) { this.dx = dx; }
	public void setDy(double dy) {
		this.dy = dy;
	}
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; }
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) {	this.height = height; }
	public void setCollisionBoxWidth(int collisionBoxWidth) {	this.collisionBoxWidth = collisionBoxWidth; }
	public void setCollisionBoxHeight(int collisionBoxHeight) { this.collisionBoxHeight = collisionBoxHeight; }

	public abstract void update();
	public abstract void draw(Graphics2D g);
}
