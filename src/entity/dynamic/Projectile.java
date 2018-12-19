package entity.dynamic;

import entity.strategy.StrategyFactory;
import tilemap.TileMap;

import javax.swing.*;
import java.awt.*;

public class Projectile extends DynamicSprite {

    private Image image;
    private boolean remove;

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

    public boolean shouldRemove() {
        return remove;
    }
    public void setRemove(boolean remove) { this.remove = remove; }

    @Override
    public void update() {
        setNextDelta(getFactorX(),getFactorY());
        checkTileMapCollision();

        setStrategyY(StrategyFactory.getInstance().getStopStrategyY());

        // The projectile is not moving... so it hit something... remove it
        if(getDx() == 0.0 || notOnScreen())
            remove = true;
    }

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
