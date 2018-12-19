package entity.dynamic;

import entity.ImageAnimator;
import entity.strategy.StrategyFactory;
import tilemap.TileMap;

import java.awt.*;

public class EnemyWaterShark extends DynamicSprite {
    private int DEFAULT_ROW = 0;
    private final int[] numFrames = {8};

    public EnemyWaterShark(TileMap tm) {
        super(tm);

        setFacingRight(false);
        setMovingRight(false);

        setStrategyX(StrategyFactory.getInstance().getMoveStrategyX()); // Initially the shark is moving to the left
        setStrategyY(StrategyFactory.getInstance().getStopStrategyY());

        setFactorX(1.0);
        setFactorY(0);

        loadSpriteAsset(numFrames, "/Enemies/enemy_shark.png");
        setCollisionBoxWidth((int)(getCollisionBoxWidth()*1.5));

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(100);
    }

    @Override
    public void update() {

        setNextDelta(getFactorX(),getFactorY()); //The next dx,dy that the shark should assume.
        checkTileMapCollision(); //Because the tiles should be "NORMAL" the strategy of the shark will be setted to MoveStrategyY
        setStrategyY(StrategyFactory.getInstance().getStopStrategyY());//So we have to force the StopStrategyY


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

        }

        imageAnimator.update();
    }

    @Override
    public void draw(Graphics2D g) {
        if(!isFacingRight()){


        g.drawImage(
                imageAnimator.getImage(),
                (int)(getX()+tileMap.getX()-width/2),
                (int)(getY()+tileMap.getY()-height/2),
                (int)(width*1.5),
                height,
                null);}
        else {
            g.drawImage(
                imageAnimator.getImage(),
                (int)(getX()+tileMap.getX()-width/2+width),
                (int)(getY()+tileMap.getY()-height/2),
                    (int)(-width*1.5),
                height,
                null);
            }

    }


}

