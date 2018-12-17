package entity.objects;

import entity.ImageAnimator;
import entity.Sprite;
import tilemap.TileMap;

import java.awt.*;

public class Item extends Sprite {

    private int DEFAULT_ROW = 0;

    public Item(TileMap tm, String pathImage, int numFrames) {
        super(tm);

        loadSpriteAsset(new int[] {numFrames}, pathImage);

        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(100);
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
