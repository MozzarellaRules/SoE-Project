package entity;

import tilemap.TileMap;

import javax.swing.*;
import java.awt.*;

public class Projectile extends DynamicSprite {

    private ImageIcon image;
    private boolean facingRight;
    private boolean hit = false;
    private boolean remove;

    public Projectile(TileMap tm, boolean facingRight) {
        super(tm);
        this.facingRight = facingRight;

        image = new ImageIcon("resources/Objects/Bullet.png");
    }

    public void setRemove(boolean remove) { this.remove = remove; }
    public boolean shouldRemove() {
        return remove;
    }

    @Override
    public void update() {
        setDx(facingRight ? 2 : -2);
        setX(getX() + (int)getDx());

        checkTileMapCollision();

        // The projectile is not moving... so it hit something... remove it
        if(getDx() == 0.0 || notOnScreen())
            remove = true;
    }

    @Override
    public void draw(Graphics2D g) {
        if(facingRight) {
            g.drawImage(image.getImage(), (int)(getX()+tileMap.getX()-width/2), (int)(getY()+tileMap.getY()-height/2), null);
        }
        else {
            g.drawImage(image.getImage(), (int)(getX()+tileMap.getX()-width/2+width), (int)(getY()+tileMap.getY()-height/2), -width, height, null);
        }
    }

}
