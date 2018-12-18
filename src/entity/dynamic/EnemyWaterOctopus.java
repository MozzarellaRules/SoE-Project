package entity.dynamic;

import entity.ImageAnimator;
import entity.strategy.StrategyFactory;
import tilemap.TileMap;

import java.awt.*;

public class EnemyWaterOctopus extends DynamicSprite {
    private int DEFAULT_ROW = 0;
    private final int[] numFrames = {6};
    private boolean up ;


    private double factorX;
    private double factorY;


    public EnemyWaterOctopus(TileMap tm) {
        super(tm);

        factorX = 0;
        factorY = 2.0;

        setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
        setStrategyY(StrategyFactory.getInstance().getSwimStrategyY()); // Initially the oktopus is moving down

        up = false ; // This boolean value shows if the oktopus is moving up or down


        loadSpriteAsset(numFrames, "/Enemies/enemy_octopus.png");

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(70);
    }

    @Override
    public void update() {

        setNextDelta(factorX,factorY);
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
                height,
                null);
    }


    @Override
    public void setNextDelta(double factorX,double factorY) {

        double dy = getStrategyY().recalcDy(getDy(),up,factorY);

        setDx(0);
        setDy(dy);
    }

}
