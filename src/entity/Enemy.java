package entity;

import tilemap.TileMap;

import java.awt.*;

public abstract class Enemy extends DynamicSprite {

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
        if(facingRight) {
            g.drawImage(animation.getImage(), (int)(x+tileMap.getX()-width/2), (int)(y+tileMap.getY()-height/2), null);
        }
        else {
            g.drawImage(animation.getImage(), (int)(x+tileMap.getX()-width/2+width), (int)(y+tileMap.getY()-height/2), -width, height, null);
        }
    }

    public abstract void update();
}
