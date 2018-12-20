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
	 * @return the position x of the sprite
	 */
	public int getX() {	return x; }

	/**
	 *
	 * @return the position y of the sprite
	 */
	public int getY() {	return y; }

	/**
	 *
	 * @return the dx field
	 */
	public double getDx() {	return dx; }

	/**
	 *
	 * @return the dy field
	 */
	public double getDy() {	return dy; }

	/**
	 *
	 * @return the tileMap field
	 */
	public TileMap getTileMap() { return tileMap; }

	/**
	 *
	 * @return the size of the tile
	 */
	public int getTileSize() { return tileSize; }

	/**
	 *
	 * @return the width of the sprite
	 */
	public int getWidth() {	return width; }

	/**
	 *
	 * @return the height of the sprite
	 */
	public int getHeight() { return height; }


	/**
	 *
	 * @return the collisionBoxWidth
	 */
	public int getCollisionBoxWidth() { return collisionBoxWidth; }

	/**
	 *
	 * @return the collisionBoxHeight
	 */
	public int getCollisionBoxHeight() { return collisionBoxHeight;	}

	/**
	 *
	 * @return the imageAnimator
	 */
	public ImageAnimator getImageAnimator() { return imageAnimator; }

	/**
	 *
	 * @return the matrix that contains the images of the sprite. Each row contains a particular action
	 */
	public ArrayList<BufferedImage[]> getSprites() { return sprites; }

	/**
	 *
	 * @return the currentRow field
	 */
	public int getCurrentRow() { return currentRow; }


	/**
	 *
	 * @param x set the position x of the sprite
	 */
	public void setX(int x) { this.x = x; }
	/**
	 *
	 * @param y set the position y of the sprite
	 */
	public void setY(int y) { this.y = y; }
	/**
	 *
	 * @param dx set the position dx of the sprite
	 */
	public void setDx(double dx) { this.dx = dx; }
	/**
	 *
	 * @param dy set the position dy of the sprite
	 */
	public void setDy(double dy) { this.dy = dy; }
	/**
	 *
	 * @param collisionBoxWidth set the collisionBoxWidth
	 */
	public void setCollisionBoxWidth(int collisionBoxWidth) { this.collisionBoxWidth = collisionBoxWidth; }
	/**
	 *
	 * @param collisionBoxHeight set the collisionBoxHeight
	 */
	public void setCollisionBoxHeight(int collisionBoxHeight) { this.collisionBoxHeight = collisionBoxHeight; }

	/**
	 *
	 * @param currentRow set the currentRow
	 */
	public void setCurrentRow(int currentRow) { this.currentRow = currentRow; }

	/**
	 * Set the x,y positions of the sprite
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y) {
		this.x = (int)x;
		this.y = (int)y;
	}

	/**
	 *
	 * @return the rectangle that needs to check possible collisions between sprites and other objects
	 */
	public Rectangle getRectangle() {
		return new Rectangle(
				(int)x-collisionBoxWidth/2,
				(int)y-collisionBoxHeight/2,
				collisionBoxWidth,
				collisionBoxHeight);
	}

	/**
	 *
	 * @return if the sprite is visible
	 */
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
