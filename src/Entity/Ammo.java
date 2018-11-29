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
    private ArrayList<BufferedImage[]> sprites;

    public Ammo(TileMap tm){
        super(tm);
        raw = 2;
        col = 2;
        numberOfFrames = 6;

        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Objects/AmmoDrop.png"));
            sprites= new ArrayList<BufferedImage[]>();
            for(int i=0; i<1; i++) { // i = number of row
                BufferedImage[] bi= new BufferedImage[numberOfFrames];
                for(int j=0; j<numberOfFrames; j++) { // j = number of column
                    bi[j] = spritesheet.getSubimage(j*width, i*height, width, height);
                }
                sprites.add(bi);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites.get(0));
        animation.setDelay(100);
    }


    public void update() {
        animation.update();
    }


    public void draw(Graphics2D g){
        setMapPosition();
        g.drawImage(animation.getImage(),(int) (x+xmap),(int)( y+ymap),null);
    }


}
