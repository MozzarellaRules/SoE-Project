package entity.dynamic;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

import entity.DynamicSprite;
import entity.IObservable;
import entity.IObserver;
import entity.strategy.*;
import tilemap.TileMap;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Player extends DynamicSprite implements IObservable {
    private int health;
    private int maxHealth;
    private boolean isDead;
    private ArrayList<IObserver> observers;

    private boolean flinching;
    private long flinchTimer;

    private boolean movingLeft;
    private boolean movingRight;

    private boolean facingRight;

    public Player(TileMap tm) {
        super(tm);

        observers = new ArrayList<>();

        // Falling when the game starts
        setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
        setStrategyY(StrategyFactory.getInstance().getFallStrategy());

        // Init parameters
        this.maxHealth = 3;
        this.health = maxHealth;
        this.flinching = false;

        setFacingRight(true);
    }

    /**
     * GETTERS
     */
    public boolean isDead() { return isDead; }
    public boolean isMovingLeft() { return movingLeft; }
    public boolean isMovingRight() { return movingRight; }
    public boolean isFacingRight() { return facingRight; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }

    /**
     * SETTERS
     */
    public void setMovingLeft(boolean movingLeft) { this.movingLeft = movingLeft; }
    public void setMovingRight(boolean movingRight) { this.movingRight = movingRight; }
    public void setFacingRight(boolean facingRight) { this.facingRight = facingRight; }

    /**
     * An enemy hit the player. Decreases the health only if the flinching is set to false.
     * Notify the update to the observers.
     * @param damage
     */
    public void hit(int damage) {
        if(flinching) // If the character is immune, go out
            return;
        health -= damage;
        if(health < 0) {
            health = 0;
        }
        if(health == 0) {
            isDead = true;
        } else {
            flinching = true; // The sprite is now immune for 1 sec
            flinchTimer = System.nanoTime();
        }
        notifyObserver(PlayerEvent.HEALTH_MODIFIED);
    }

    /**
     * This method must be implemented by a concrete class.
     * It deals with the animation of the sprite.
     */
    public abstract void hookAnimation();

    @Override
    public void update(){
        getNextDelta();
        checkTileMapCollision();

        hookAnimation();
        getImageAnimator().update();

        // If the character was hit, he's immune for 1 sec
        // Check if the flinching must be set to false because the time is expired
        if(flinching) {
        	long elapsed = (System.nanoTime()-flinchTimer)/1000000;
        	if(elapsed > 1000) {
        		flinching = false;
        	}
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

    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void deleteObserver(IObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObserver(PlayerEvent event) {
        for(IObserver o:observers) {
            o.updateObserver(this, event);
        }
    }
}
