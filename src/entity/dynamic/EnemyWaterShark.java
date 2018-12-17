package entity.dynamic;

import entity.DynamicSprite;
import entity.ImageAnimator;
import entity.strategy.StrategyFactory;
import tilemap.TileMap;

import java.awt.*;

public class EnemyWaterShark extends DynamicSprite {
    private int DEFAULT_ROW = 0;
    private final int[] numFrames = {8};
    private boolean up ;

    public EnemyWaterShark(TileMap tm) {
        super(tm);

        setStrategyX(StrategyFactory.getInstance().getMoveLeftStrategy());
        setStrategyY(StrategyFactory.getInstance().getStopStrategyY()); // Initially the shark is moving left

        up = false ; // This boolean value shows if the shark is moving left or right


        loadSpriteAsset(numFrames, "/Enemies/Shark.png");

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(100);
    }

    @Override
    public void update() {
        getNextDelta();
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

