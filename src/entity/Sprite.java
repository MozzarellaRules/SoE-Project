package entity;

import tilemap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanelController;

import javax.imageio.ImageIO;

public abstract class Sprite {
	protected TileMap tileMap;
	protected int tileSize;

	private ArrayList<BufferedImage[]> sprites;
	private int currentRow;

	protected int width;
	protected int height;
	protected int collisionBoxWidth;
	protected int collisionBoxHeight;

	protected ImageAnimator imageAnimator;
	
	private int x;
	private int y;
	private double dx;
	private double dy;
	
	public Sprite(TileMap tm) {
		this.tileMap = tm;
		this.tileSize = tm.getTileSize();
		this.width = 32;
		this.height = 32;
		this.collisionBoxWidth = 20;
		this.collisionBoxHeight = 20;
	}

	/**
	 * GETTERS
	 */
	public int getX() {	return x; }
	public int getY() {	return y; }
	public double getDx() {	return dx; }
	public double getDy() {	return dy; }
	public TileMap getTileMap() { return tileMap; }
	public int getTileSize() { return tileSize; }
	public int getWidth() {	return width; }
	public int getHeight() { return height; }
	public int getCollisionBoxWidth() { return collisionBoxWidth; }
	public int getCollisionBoxHeight() { return collisionBoxHeight;	}
	public ImageAnimator getImageAnimator() { return imageAnimator; }
	public ArrayList<BufferedImage[]> getSprites() { return sprites; }
	public int getCurrentRow() { return currentRow; }

	/**
	 * SETTERS
	 */
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setDx(double dx) { this.dx = dx; }
	public void setDy(double dy) { this.dy = dy; }
	public void setCollisionBoxWidth(int collisionBoxWidth) { this.collisionBoxWidth = collisionBoxWidth; }
	public void setCollisionBoxHeight(int collisionBoxHeight) { this.collisionBoxHeight = collisionBoxHeight; }
	public void setCurrentRow(int currentRow) { this.currentRow = currentRow; }

	public void setPosition(double x, double y) {
		this.x = (int)x;
		this.y = (int)y;
	}

	public Rectangle getRectangle() {
		return new Rectangle(
				(int)x-collisionBoxWidth,
				(int)y-collisionBoxHeight,
				collisionBoxWidth,
				collisionBoxHeight);
	}

	public boolean notOnScreen() {
		return  x + tileMap.getX() + width < 0 ||
				x + tileMap.getX() - width > GamePanelController.WIDTH ||
				y + tileMap.getY() + height < 0||
				y + tileMap.getY() - height > GamePanelController.HEIGHT;
	}

	/**
	 * Load the frames of the sprite from an image
	 * @param numFrames define the number of columns of each row
	 * @param path define the path of the image
	 */
	public void loadSpriteAsset(int numFrames[], String path) {
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(path));
			this.sprites = new ArrayList<BufferedImage[]>();
			for(int i=0; i<numFrames.length; i++) { // i = number of rows
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j=0; j<numFrames[i]; j++) { // j = number of columns
					bi[j] = spritesheet.getSubimage(j*width, i*height, width, height);
				}
				sprites.add(bi);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void update();
	public abstract void draw(Graphics2D g);
}
