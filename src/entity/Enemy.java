package entity;

import tilemap.TileMap;

import java.awt.*;

public abstract class Enemy extends DynamicSprite {

    protected boolean isDead;
    protected int health;

    public Enemy(TileMap tm) {
        super(tm);
        setFacingRight(false);
    }

    public boolean isDead(){ return isDead; }

    public void hit(int damage){
        health -= damage;

        if (health < 0) {
            health = 0;
        }
        if (health == 0) {
            isDead = true;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if(isFacingRight()) {
            g.drawImage(
                    imageAnimator.getImage(),
                    (int)(getX()+tileMap.getX()-width/2),
                    (int)(getY()+tileMap.getY()-height/2),
                    null);
        }
        else {
            g.drawImage(
                    imageAnimator.getImage(),
                    (int)(getX()+tileMap.getX()-width/2+width),
                    (int)(getY()+tileMap.getY()-height/2),
                    -width,
                    height,
                    null);
        }
    }

    public abstract void update();
}
