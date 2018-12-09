package entity;

import tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyGround extends Enemy {

    private static final int WALK = 0; // Walk state use row 0 of the sprite asset
    private final int[] numFrames = {12}; // Row 0 has 12 frames
    private double fallSpeed;
    private double maxFallSpeed;

    // Sprite animation
    private ArrayList<BufferedImage[]> sprites;

    public EnemyGround(TileMap tm){
        super(tm);

        // Init parameters
        cheight = 25 ;
        cwidth = 20;
        moveSpeed = 0.4;
        maxSpeed = 1;
        maxFallSpeed = 4.0;
        fallSpeed = 0.15;
        health = 2;
        isDead = false;
        currentAction = WALK;

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
        animation = new Animation();
        animation.setFrames(sprites.get(WALK));
        animation.setDelay(70);
    }


    public void getNextPosition(){
        if(falling){
            setDx(0);
            if(getDy() > maxFallSpeed){
                setDy(maxFallSpeed);
            }
            else setDy(getDy()+fallSpeed);
        }
        else {
            if(!facingRight) {
                setDx(getDx()-moveSpeed);

                if(getDx() < -maxSpeed)
                    setDx(-maxSpeed);
            }
            else{
                setDx(getDx()+moveSpeed);

                if(getDx() > maxSpeed)
                    setDx(maxSpeed);
            }
        }
    }

    public void update(){
        checkTileMapCollision();

        if (!falling){
            if (getDx() == 0){
                if(facingRight)
                    facingRight = false;
                else
                    facingRight = true;
            }
        }

        getNextPosition();
        setPosition(xtemp, ytemp);
        animation.update();
    }

}
