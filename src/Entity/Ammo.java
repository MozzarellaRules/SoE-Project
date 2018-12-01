package Entity;


import TileMap.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Ammo extends MapObject {

    private int raw;
    private int col;
    private BufferedImage bfImage;
    private ImageIcon imageIc;
    private Tile tile;
    private int numberOfFrames;
    private BufferedImage[] frames;
    private boolean isGrabbed;

    public Ammo(TileMap tm){
        super(tm);
        cwidth = 20;
        cheight = 20;

        raw = 2;
        col = 2;
        numberOfFrames = 6;
        isGrabbed = false;

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
        animation.update(); // Update the sprite animation
    }

    public void draw(Graphics2D g){
        setMapPosition(); // Update xmap and ymap

        // Draw ammo
        g.drawImage(animation.getImage(),(int) (x+xmap),(int)( y+ymap),null);
    }


}
