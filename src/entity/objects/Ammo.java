package entity.objects;


import entity.ImageAnimator;
import entity.Sprite;
import tilemap.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Ammo extends Sprite {

    private int DEFAULT_ROW = 0;
    private final int[] numFrames = {6};

    public Ammo(TileMap tm){
        super(tm);

        loadSpriteAsset(numFrames, "/Objects/AmmoDrop.png");

        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(100);
    }

    public void update() {
        imageAnimator.update();
    }

    public void draw(Graphics2D g){
        g.drawImage(imageAnimator.getImage(), getX()+tileMap.getX(), getY()+tileMap.getY(), null);
    }

}
