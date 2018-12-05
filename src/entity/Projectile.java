package entity;

import tilemap.TileMap;

import javax.swing.*;
import java.awt.*;

public class Projectile extends DynamicSprite {

    private int speed;
    private ImageIcon image;
    private boolean facingRight;
    private boolean hit = false;
    private boolean remove;

    public Projectile(TileMap tm, boolean facingRight) {
        super(tm);
        this.facingRight = facingRight;

        speed = 2;
        image = new ImageIcon("resources/Objects/Bullet.png");

        if(facingRight)
            dx = speed;
        else
            dx = -speed;
    }

    public void update() {
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if(dx == 0.0 || notOnScreen()) // The projectile is not moving... so it hit something... remove it
            remove = true;

        x += dx;
    }

    public void draw(Graphics2D g) {
        if(facingRight) {
            g.drawImage(image.getImage(), (int)(x+tileMap.getX()-width/2), (int)(y+tileMap.getY()-height/2), null);
        }
        else {
            g.drawImage(image.getImage(), (int)(x+tileMap.getX()-width/2+width), (int)(y+tileMap.getY()-height/2), -width, height, null);
        }
    }

    public boolean getRemove() { return remove; }

    public boolean shouldRemove() {
        return remove;
    }
}
