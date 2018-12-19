package entity.dynamic;

import entity.ImageAnimator;
import entity.strategy.StrategyFactory;
import tilemap.TileMap;

import java.awt.*;

public class EnemyWaterOctopus extends DynamicSprite {
    private int DEFAULT_ROW = 0;
    private final int[] numFrames = {6};
    private boolean up ;

    /**
     * The octopus is an enemy exclusive of the second level and moves only on the Y axis.
     * @param tm is the tilemap piece we generate the enemy on
     */
    public EnemyWaterOctopus(TileMap tm) {
        super(tm);

        setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
        setStrategyY(StrategyFactory.getInstance().getSwimStrategyY());
        setFactorX(0);
        setFactorY(1.5);


        setCollisionBoxHeight((int)(getHeight()*1.3));
        up = false ; // This boolean value shows if the octopus is moving up or down


        loadSpriteAsset(numFrames, "/Enemies/enemy_octopus.png");

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(70);

    }

    @Override
    public void update() {
        setNextDelta(getFactorX(),getFactorY());
        checkTileMapCollision();

        if(getDy()==0){ // collision detected
            if(up){
                up = false;

            }else {
                up = true;

            }
        }

        imageAnimator.update();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(
                imageAnimator.getImage(),
                (int)(getX()+tileMap.getX()-width/2),
                (int)(getY()+tileMap.getY()-height/2),
                width,
                (int) (height *1.3),
                null);
    }

    /**
     * We override this method because the octopus rises very quickly but "falls down"  in a slower
     * way in order to give players enough time to get under/over it.
     * @param factorX is unused here
     * @param factorY is set in its init method.
     */
    @Override
    public void setNextDelta(double factorX,double factorY) {
        double dy = getStrategyY().recalcDy(getDy(),up,factorY);


        setDy(dy);
    }

}
