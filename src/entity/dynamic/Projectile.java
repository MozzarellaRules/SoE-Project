package entity.dynamic;

import entity.DynamicSprite;
import entity.strategy.StrategyFactory;
import tilemap.TileMap;

import javax.swing.*;
import java.awt.*;

public class Projectile extends DynamicSprite {

    private Image image;
    private boolean facingRight;
    private boolean remove;


    public Projectile(TileMap tm, boolean facingRight) {
        super(tm);
        this.facingRight = facingRight;





        if(facingRight) { // Set the direction of the projectile
            setStrategyX(StrategyFactory.getInstance().getMoveRightStrategy());
        } else {
            setStrategyX(StrategyFactory.getInstance().getMoveLeftStrategy());
        }

        // No movement on the Y-axis
        setStrategyY(StrategyFactory.getInstance().getStopStrategyY());

        this.image = new ImageIcon("resources/Objects/Bullet.png").getImage();
    }

    public boolean shouldRemove() {
        return remove;
    }
    public void setRemove(boolean remove) { this.remove = remove; }

    @Override
    public void update() {
        System.out.println(getX()+": Before Update");
        getNextDelta();
        setDx(getDx()*2);
        checkTileMapCollision();


        // Force no-movement on the Y-axis
        setStrategyY(StrategyFactory.getInstance().getStopStrategyY());

        //setX(getX() + (int)getDx());

        // The projectile is not moving... so it hit something... remove it
        if(getDx() == 0.0 || notOnScreen())
            remove = true;
    }

    @Override
    public void draw(Graphics2D g) {
        if(facingRight) {
            g.drawImage(image, (int)(getX()+tileMap.getX()-width/2), (int)(getY()+tileMap.getY()-height/2), null);
        }
        else {
            g.drawImage(image, (int)(getX()+tileMap.getX()-width/2+width), (int)(getY()+tileMap.getY()-height/2), -width, height, null);
        }
    }

}
