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
            setDx(speed);
        else
            setDx(-speed);
    }

    public void update() {
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if(getDx() == 0.0 || notOnScreen()) // The projectile is not moving... so it hit something... remove it
            remove = true;

        setX(getX()+(int)getDx());

    }

    public void draw(Graphics2D g) {
        if(facingRight) {
            g.drawImage(image.getImage(), (int)(getX()+tileMap.getX()-width/2), (int)(getY()+tileMap.getY()-height/2), null);
        }
        else {
            g.drawImage(image.getImage(), (int)(getX()+tileMap.getX()-width/2+width), (int)(getY()+tileMap.getY()-height/2), -width, height, null);
        }
    }

    public boolean shouldRemove() {
        return remove;
    }
}
