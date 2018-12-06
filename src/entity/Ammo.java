package entity;


import tilemap.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Ammo extends Sprite {

    /*
    private int raw;
    private int col;
    private BufferedImage bfImage;
    private ImageIcon imageIc;
    private Tile tile;
    */

    private int numberOfFrames;
    private BufferedImage[] frames;

    public Ammo(TileMap tm){
        super(tm);
        numberOfFrames = 6;

        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Objects/AmmoDrop.png"));
            frames = new BufferedImage[numberOfFrames];
            for(int j=0; j<numberOfFrames; j++) { // j = number of columns/frames
                frames[j] = spritesheet.getSubimage(j*width, 0, width, height);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(frames);
        animation.setDelay(100);
    }

    public void update() {
        animation.update();
    }

    public void draw(Graphics2D g){
        g.drawImage(animation.getImage(), (int) (getX() + tileMap.getX()), (int) (getY() + tileMap.getY()), null);
    }

}
