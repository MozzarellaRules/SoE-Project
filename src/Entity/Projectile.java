package Entity;

import TileMap.TileMap;

import javax.swing.*;
import java.awt.*;

public class Projectile extends MapObject {

    private int speed;
    private ImageIcon image;
    private boolean facingRight;
    private boolean hit = false;
    private boolean remove;

    public Projectile(TileMap tm, boolean facingRight) {
        super(tm);
        this.facingRight = facingRight;

        width = 32; // width of the sprite image
        height = 32; // height of the sprite image
        cwidth = 20; // width of the collision box
        cheight = 20; // height of the collision box

        speed = 4;

        image = new ImageIcon("Resources/Objects/projectile.png");

        if(facingRight)
            dx = speed;
        else
            dx = -speed;
    }

    public void update() {
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if(dx == 0.0) // The projectile is not moving... so it hit something... remove it
            remove = true;

        x += dx;
    }


    public void draw(Graphics2D g) {
        setMapPosition(); // update xmap and ymap

        // draw player
        if(facingRight) {
            g.drawImage(image.getImage(), (int)(x+xmap-width/2), (int)(y+ymap-height/2), null);
        }
        else {
            g.drawImage(image.getImage(), (int)(x+xmap-width/2+width), (int)(y+ymap-height/2), -width, height, null);
        }
    }

    public boolean getRemove() { return remove; }

    public boolean shouldRemove() {
        return remove;
    }
}
