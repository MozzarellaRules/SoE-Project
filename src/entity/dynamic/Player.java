package entity.dynamic;

import java.util.ArrayList;

import entity.visual.IObservable;
import entity.visual.IObserver;
import entity.strategy.*;
import tilemap.TileMap;
import java.awt.*;

public abstract class Player extends DynamicSprite implements IObservable {
    private int health;
    private int maxHealth;
    private boolean isDead;
    private ArrayList<IObserver> observers;

    private boolean flinching;
    private long flinchTimer;

    public Player(TileMap tm) {
        super(tm);

        observers = new ArrayList<>();
        setFalling(true);

        // Falling when the game starts
        setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
        setStrategyY(StrategyFactory.getInstance().getMoveStrategyY());
        setFactorX(1.0);
        setFactorY(1.0);
        setFacingRight(true);

        // Init parameters
        this.maxHealth = 3;
        this.health = maxHealth;
        this.flinching = false;
    }

    /**
     * GETTERS
     */
    public boolean isDead() { return isDead; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }

    /**
     * SETTERS
     *
     */
    public void setHealth(int health){this.health = health;}
    public void setMaxHealth(int maxHealth){this.maxHealth = maxHealth;}

    public void setDead(boolean dead) { isDead = dead; }

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
    public abstract void hookUpdate();

    @Override
    public void update(){
        setNextDelta(getFactorX(),getFactorY());
        checkTileMapCollision();

        if(!isFalling())
            setStrategyY(StrategyFactory.getInstance().getStopStrategyY());

        hookUpdate();
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
