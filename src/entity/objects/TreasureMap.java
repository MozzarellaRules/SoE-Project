package entity.objects;

import entity.ImageAnimator;
import entity.Sprite;
import tilemap.TileMap;

import java.awt.*;

public class TreasureMap extends Sprite {

    private int DEFAULT_ROW = 0;
    private final int[] numFrames = {12};

    public TreasureMap(TileMap tm) {
        super(tm);

        loadSpriteAsset(numFrames, "/Objects/Map.png");

        setCollisionBoxWidth(25);
        setCollisionBoxHeight(25);

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(70);
    }

    @Override
    public void update() {
        imageAnimator.update();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(imageAnimator.getImage(), getX()+tileMap.getX(), getY()+tileMap.getY(), null);
    }
}
