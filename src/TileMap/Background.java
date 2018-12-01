package TileMap;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import Main.GamePanel;

import java.awt.*;

public class Background {
	//Oggetto di tipo background
	private BufferedImage image; //L'immagine del background
	
	private double x; //Coordinata x
	private double y; // coordinata y
	private double dx;//Quanto deve spostarsi sull'asse x
	private double dy;// Quanto deve spostarsi sull'asse y
	
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
	
	//Imposto la posizione
	public void setPosition(double x, double y) {
		this.x=(x*moveScale) % GamePanel.WIDTH;
		this.y=(y*moveScale) % GamePanel.HEIGHT;
	}
	
	//Imposto gli spostamenti dx e dy
	public void setVector(double dx, double dy) {
		this.dx=dx;
		this.dy=dy;
	}
	
	//quando aggiorno il background semplicemente faccio muovere l'immagine 
	public void update() {
		x+=dx;
		y+=dy;
	}
	
	//Disegno il background
	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int)x,(int)y, null); //metodo che ci dice quale immagine disegnare e in quali coordinate..dopo ci vuole sempre null
		if(x < 0) {
			g.drawImage(image,(int)x + GamePanel.WIDTH, (int)y,null); //Se esci fuori dallo schermo a sinistra ridisegna a destra
		}
		if(x > 0) {
			g.drawImage(image,(int)x - GamePanel.WIDTH, (int)y,null);//Se esci fuori dallo schermo a destra ridisegna a sinistra
			
		}
	}
	
	
}
