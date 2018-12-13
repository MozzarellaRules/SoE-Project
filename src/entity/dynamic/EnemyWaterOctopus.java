package entity.dynamic;

import entity.DynamicSprite;
import entity.ImageAnimator;
import entity.strategy.StrategyFactory;
import tilemap.TileMap;

import java.awt.*;

public class EnemyWaterOctopus extends DynamicSprite {
    private int DEFAULT_ROW = 0;
    private final int[] numFrames = {6};
    private boolean up ;

    public EnemyWaterOctopus(TileMap tm) {
        super(tm);

        setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
        setStrategyY(StrategyFactory.getInstance().getSwimDownStrategyY()); // Initially the oktopus is moving down

        up = false ; // This boolean value shows if the oktopus is moving up or down


        loadSpriteAsset(numFrames, "/Enemies/Oktopus.png");

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(70);
    }

    @Override
    public void update() {

        getNextDelta();
        checkTileMapCollision();

        if(getDy()==0){ // collision detected
            if(up){
                up = false;
                this.setStrategyY(StrategyFactory.getInstance().getSwimDownStrategyY());
            }else {
                up = true;
                this.setStrategyY(StrategyFactory.getInstance().getSwimUpStrategyY());
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

}
