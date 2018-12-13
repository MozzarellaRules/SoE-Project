package entity.dynamic;

import entity.DynamicSprite;
import entity.ImageAnimator;
import entity.strategy.*;
import tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyGround extends DynamicSprite {

    private int DEFAULT_ROW = 0;
    private final int[] numFrames = {12};

    private boolean facingRight;

    private boolean isDead;
    private int health;

    public EnemyGround(TileMap tm){
        super(tm);

        setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
        setStrategyY(StrategyFactory.getInstance().getFallStrategy());

        setCollisionBoxWidth(20);
        setCollisionBoxHeight(25);

        health = 2;
        isDead = false;
        facingRight = true;

        loadSpriteAsset(numFrames, "/Enemies/BaseEnemy.png");

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(70);
    }

    /**
     * GETTERS
     */
    public boolean isDead(){ return isDead; }
    public boolean isFacingRight() { return facingRight; }

    /**
     * SETTERS
     */
    public void setFacingRight(boolean facingRight) { this.facingRight = facingRight; }

    /**
     * The player hit the enemy. Set a damage.
     * @param damage
     */
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
    public void update(){
        getNextDelta();
        checkTileMapCollision();

        if (getDy() == 0) { // Not falling
            // Force no-movement on the Y-axis
            if (getDx() == 0) { // Not moving -> collision detected
                // Revert facing and moving strategy
                if(isFacingRight()) {
                    setFacingRight(false);
                    setStrategyX(StrategyFactory.getInstance().getMoveLeftStrategy());
                }
                else {
                    setFacingRight(true);
                    setStrategyX(StrategyFactory.getInstance().getMoveRightStrategy());
                }
            }
        } else if(getDy() > 1) { // If falling -> no movement on the X-axis
            setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
        }

        imageAnimator.update();
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

}
