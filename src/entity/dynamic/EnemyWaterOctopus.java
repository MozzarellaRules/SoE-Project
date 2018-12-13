package entity.dynamic;

import entity.DynamicSprite;
import entity.ImageAnimator;
import entity.strategy.StrategyFactory;
import tilemap.TileMap;

import java.awt.*;

public class EnemyWaterOctopus extends DynamicSprite {
    private int DEFAULT_ROW = 0;
    private final int[] numFrames = {6};

    public EnemyWaterOctopus(TileMap tm) {
        super(tm);

        setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
        setStrategyY(StrategyFactory.getInstance().getFallStrategy());

        loadSpriteAsset(numFrames, "/Enemies/Octopus.png");

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(70);
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
