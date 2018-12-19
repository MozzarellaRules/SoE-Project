package entity.dynamic;

import entity.ImageAnimator;
import entity.strategy.*;
import tilemap.TileMap;

import java.awt.*;

public class EnemyGround extends DynamicSprite {

    private int DEFAULT_ROW = 0;
    private final int[] numFrames = {12};

    private boolean isDead;
    private int health;

    public EnemyGround(TileMap tm){
        super(tm);

        setFalling(true);
        setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
        setStrategyY(StrategyFactory.getInstance().getMoveStrategyY());
        setFactorX(1.5);
        setFactorY(1.0);
        setCollisionBoxWidth(20);
        setCollisionBoxHeight(30);

        health = 2;
        isDead = false;

        loadSpriteAsset(numFrames, "/Enemies/enemy_ground.png");

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(70);
    }

    /**
     * GETTERS
     */
    public boolean isDead(){ return isDead; }

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
        setNextDelta(getFactorX(),getFactorY());
        checkTileMapCollision();

        if(!isFalling()) { // Not falling
            setStrategyY(StrategyFactory.getInstance().getStopStrategyY());
            if (getDx() == 0) { // Not moving -> collision detected
                // Revert facing and moving strategy
                if(isFacingRight()) {
                    setMovingRight(false);
                    setFacingRight(false);
                }
                else {
                    setMovingRight(true);
                    setFacingRight(true);
                }
                setStrategyX(StrategyFactory.getInstance().getMoveStrategyX());
            }
        } else { // If falling -> no movement on the X-axis
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
