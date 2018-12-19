package entity.dynamic;

import java.util.ArrayList;

import entity.visual.PlayerObservable;
import entity.visual.PlayerObserver;
import entity.strategy.*;
import tilemap.TileMap;
import java.awt.*;

public abstract class Player extends DynamicSprite implements PlayerObservable {
    private int health;
    private boolean isDead;
    private ArrayList<PlayerObserver> observers;

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
        this.health = 3;
        this.flinching = false;
    }

    /**
     * This method is used to determine if the player character has depleted all of its health.
     * @return the isDead boolean.
     */
    public boolean isDead() { return isDead; }

    /**
     * This method is used to return the amount of remaining health the player character has.
     * @return the remaining health.
     */
    public int getHealth() { return health; }

    /**
     * This method is used to set the amount of health the player character starts the level with.
     * @param health is the number of "hearts".
     */
    public void setHealth(int health){this.health = health;}

    /**
     * This method is used to kill the player whenever its remaining health reaches zero.
     * @param dead is a boolean (it's set to true).
     */
    public void setDead(boolean dead) { isDead = dead; }

    /**
     * An enemy can hit the player. Decreases the health only if the flinching is set to false.
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

    /**
     * This method is used to update the animation of the character and to check for eventual collisions
     * with the map or with the enemies/items.
     */
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

    /**
     * This method draws the sprite depending on which direction it's facing (right or left).
     * @param g is the Graphics2D object we use to draw the image.
     */
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

    /**
     * Adds an "observer" that is used to monitor the state of the character.
     * @param o is the type of observer.
     */
    @Override
    public void addObserver(PlayerObserver o) {
        observers.add(o);
    }

    /**
     * Deletes an observer.
     * @param o is the observer to remove.
     */
    @Override
    public void deleteObserver(PlayerObserver o) {
        observers.remove(o);
    }

    /**
     * This method is used to update an observer and notify changes.
     * @param event is the event that has to be notified.
     */
    @Override
    public void notifyObserver(PlayerEvent event) {
        for(PlayerObserver o:observers) {
            o.updateObserver(this, event);
        }
    }
}
