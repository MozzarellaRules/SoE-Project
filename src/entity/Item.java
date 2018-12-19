package entity;

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

    /**
     * Update the animation of the item
     */
    @Override
    public void update() {
        imageAnimator.update();
    }

    /**
     * This method draws the image Item on the g element
     * @param g
     */
    @Override
    public void draw(Graphics2D g) {
        g.drawImage(imageAnimator.getImage(), (int)(getX()+tileMap.getX()-width/2), (int)(getY()+tileMap.getY()-height/2), null);
    }
}
