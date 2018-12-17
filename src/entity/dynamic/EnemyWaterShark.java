package entity.dynamic;

import entity.ImageAnimator;
import entity.strategy.StrategyFactory;
import tilemap.TileMap;

import java.awt.*;

public class EnemyWaterShark extends DynamicSprite {
    private int DEFAULT_ROW = 0;
    private final int[] numFrames = {8};

    private double factorX;
    private double factorY;



    public EnemyWaterShark(TileMap tm) {
        super(tm);

        setStrategyX(StrategyFactory.getInstance().getMoveStrategyX());
        setStrategyY(StrategyFactory.getInstance().getStopStrategyY()); // Initially the shark is moving left

        factorX = 2.0;
        factorY= 0;


        loadSpriteAsset(numFrames, "/Enemies/Shark.png");

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(100);
    }

    @Override
    public void update() {

        setNextDelta(factorX,factorY);
        checkTileMapCollision();


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

