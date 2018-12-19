package tilemap;
import java.awt.image.BufferedImage;
public class Tile {
	
	private BufferedImage image;
	private int type;
	
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;

	/**
	 * This method sets an appearance for a certain tile.
	 * @param image
	 * @param type
	 */
	public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}

	/**
	 * This method returns the image representing the tile.
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Returns the type of tile (blocking or not).
	 * @return the type of the tile.
	 */
	public int getType() {
		return type;
	}
}
