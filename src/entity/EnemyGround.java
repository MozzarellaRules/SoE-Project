package entity;

import entity.strategy.*;
import tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyGround extends Enemy {

    private int WALK_ROW = 0; // Walk state use row 0 of the sprite asset
    private final int[] numFrames = {12}; // Row 0 has 12 frames

    private ArrayList<IStrategy> strategies;
    private final static int
            moveLeftStrategy = 0,
            moveRightStrategy = 1,
            fallStrategy = 2;

    // Sprite imageAnimator
    private ArrayList<BufferedImage[]> sprites;

    public EnemyGround(TileMap tm){
        super(tm);

        strategies = new ArrayList<>();
        strategies.add(new MoveLeftStrategy());
        strategies.add(new MoveRightStrategy());
        strategies.add(new FallStrategy());

        // Init parameters
        collisionBoxHeight = 25 ;
        collisionBoxWidth = 20;
        health = 2;
        isDead = false;
        current_row = WALK_ROW;

        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Enemies/BaseEnemy.png"));
            sprites = new ArrayList<BufferedImage[]>();
            for(int i=0; i<1; i++) { // i = number of row
                BufferedImage[] bi= new BufferedImage[numFrames[i]];
                for(int j=0; j<numFrames[i]; j++) { // j = number of column
                    bi[j] = spritesheet.getSubimage(j*width, i*height, width, height);
                }
                sprites.add(bi);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(sprites.get(WALK_ROW));
        imageAnimator.setDelay(70);
    }

    private void getNextDelta() {
        double dx = 0, dy = 0;

        if(isFalling()) {
            dy = strategies.get(EnemyGround.fallStrategy).recalcDy(getDy());
        } else {
            if(!isFacingRight()) {
                dx = strategies.get(EnemyGround.moveLeftStrategy).recalcDx(getDx());
            }
            else {
                dx = strategies.get(EnemyGround.moveRightStrategy).recalcDx(getDx());
            }
        }

        setDx(dx);
        setDy(dy);
    }

    public void update(){
        getNextDelta();
        checkTileMapCollision();

        if (!isFalling()){
            if (getDx() == 0){
                if(isFacingRight())
                    setFacingRight(false);
                else
                    setFacingRight(true);
            }
        }

        imageAnimator.update();
    }

}
