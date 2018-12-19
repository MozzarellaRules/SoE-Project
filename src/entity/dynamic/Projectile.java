package entity.dynamic;

import entity.strategy.StrategyFactory;
import tilemap.TileMap;

import javax.swing.*;
import java.awt.*;

public class Projectile extends DynamicSprite {

    private Image image;
    private boolean remove;

    /**
     * This method creates a new projectile whenever the player character attacks.
     * @param tm is the map.
     * @param facingRight is used to determine if the bullet is moving to the right or not.
     */
    public Projectile(TileMap tm, boolean facingRight) {
        super(tm);

        setStrategyX(StrategyFactory.getInstance().getMoveStrategyX());
        setStrategyY(StrategyFactory.getInstance().getStopStrategyY());
        setFactorX(2.0);
        setFactorY(0);
        setMovingRight(facingRight);
        setFacingRight(facingRight);

        this.image = new ImageIcon("resources/Objects/bullet.png").getImage();
    }

    /**
     * This method tells if a bullet should be removable or not.
     * @return true.
     */
    public boolean shouldRemove() {
        return remove;
    }

    /**
     * This method removes a bullet whenever it touches a wall/enemy or goes beyond the screen boundaries.
     * @param remove
     */
    public void setRemove(boolean remove) { this.remove = remove; }

    /**
     * This method deals with the movement of the projectile and its evolution.
     * Once a projectile hits an enemy or a wall, it's removed.
     */
    @Override
    public void update() {
        setNextDelta(getFactorX(),getFactorY());
        checkTileMapCollision();

        setStrategyY(StrategyFactory.getInstance().getStopStrategyY());

        // The projectile is not moving... so it hit something... remove it
        if(getDx() == 0.0 || notOnScreen())
            remove = true;
    }

    /**
     * This method draws the projectile on the right or the left of our character.
     * @param g is the Graphics2D object we use to draw it.
     */
    @Override
    public void draw(Graphics2D g) {
        if(isFacingRight()) {
            g.drawImage(image, (int)(getX()+tileMap.getX()-width/2), (int)(getY()+tileMap.getY()-height/2), null);
        }
        else {
            g.drawImage(image, (int)(getX()+tileMap.getX()-width/2+width), (int)(getY()+tileMap.getY()-height/2), -width, height, null);
        }
    }

}
