package tilemap;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import main.GamePanelController;
import java.awt.*;

public class Background {

	private BufferedImage image;
	private double x;
	private double y;
	private double dx;
	private double dy;
	private double moveScale;
	
	public Background(String s, double ms) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the x and y needed to change the background movement.
	 * @param x is the position of the player on the x-axis
	 * @param y is the position of the player on the y-axis
	 */
	public void setPosition(double x, double y) {
		this.x = (x*moveScale) % GamePanelController.WIDTH;
		this.y = (y*moveScale) % GamePanelController.HEIGHT;
	}

	/**
	 * Draws the background on the g element
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x,(int)y, null);

		if(x < 0) {
			g.drawImage(image,(int)x + GamePanelController.WIDTH, (int)y,null);
		}
		else if(x > 0) {
			g.drawImage(image,(int)x - GamePanelController.WIDTH, (int)y,null);
			
		}
	}
}
