package entity;

import tilemap.TileMap;

import java.awt.*;

public abstract class Enemy extends MapObject {

    protected boolean isDead;
    protected int health;

    public Enemy(TileMap tm) {
        super(tm);
        facingRight = false;
    }

    public boolean isDead(){ return isDead; }

    public void hit(int damage){
        health -= damage;

        if (health < 0){ health = 0; }
        if (health == 0){ isDead = true; }
    }


    public void draw(Graphics2D g) {
        setMapPosition(); // update xmap and ymap

        if(facingRight) {
            g.drawImage(animation.getImage(), (int)(x+xmap-width/2), (int)(y+ymap-height/2), null);
        }
        else {
            g.drawImage(animation.getImage(), (int)(x+xmap-width/2+width), (int)(y+ymap-height/2), -width, height, null);
        }
    }

    public abstract void update();
}
