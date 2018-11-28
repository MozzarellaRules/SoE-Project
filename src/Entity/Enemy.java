package Entity;

import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Enemy extends MapObject {




    // Each element of the array refers to the number of frames (or columns) in a row of the sprite asset
    // The sprite asset now have 3 rows (to animate 3 states) so I'm defining the number of frames of each row

    protected boolean dead;
    protected int health;

    public Enemy(TileMap tm) {
        super(tm);
        facingRight = true;
    }

    public boolean isDead(){return dead;}


    public void hit(int damage){
        health-=damage;

        if (health < 0){
            health = 0;
        }
        if (health == 0){
            dead = true;
        }

    }


    public void draw(Graphics2D g) {

        //if (!notOnScreen()){ // Vogliamo gestire la condizione in cui il nemico non è sullo schermo
        //In questo caso la draw non dovrebbe essere eseguita per questioni di ottimizzazione
        setMapPosition(); // update xmap and ymap



        if(facingRight) {
            g.drawImage(animation.getImage(), (int)(x+xmap-width/2), (int)(y+ymap-height/2), null);
        }
        else {
            g.drawImage(animation.getImage(), (int)(x+xmap-width/2+width), (int)(y+ymap-height/2), -width, height, null);
        }
    }//}

    public abstract void update();


}
