package TileMap;
import java.awt.image.BufferedImage;
public class Tile {
	
	// Il tile, ovvero quel pezzo di immagine che serve a formare la mappa
	private BufferedImage image; //E' un'immagine
	private int type; //Ha un suo tipo
	
	//tile types
	public static final int NORMAL=0;//Si può attraversare
	public static final int BLOCKED=1; // non si può attraversare (Vedi MapObject)
	
	
	public Tile(BufferedImage image,int type) {
		this.image=image;
		this.type=type;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	public int getType() {
		return type;
	}
}
