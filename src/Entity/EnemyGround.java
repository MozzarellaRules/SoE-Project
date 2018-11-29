package Entity;

import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyGround extends Enemy{

    private final int[] numFrames =  {12};
    private double fallSpeed;
    private double maxFallSpeed;
    private static final int WALK = 0;

    // Sprite animation
    private ArrayList<BufferedImage[]> sprites;

    public EnemyGround(TileMap tm){
        super(tm);
        cheight = 20 ;
        cwidth = 20;
        moveSpeed = 0.4;
        maxSpeed = 1;

        maxFallSpeed = 4.0;
        fallSpeed = 0.15;
        health = 2;
        dead = false;


        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Enemies/BaseEnemy.png"));
            sprites= new ArrayList<BufferedImage[]>();
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

        currentAction = WALK;
        animation = new Animation();
        animation.setFrames(sprites.get(WALK));
        animation.setDelay(70);

    }


    public void getNextPosition(){

        if(falling){
            dx = 0;
            if(dy > maxFallSpeed){
                dy= maxFallSpeed;
            }
            else dy+=fallSpeed;
        }



        else {
            if(!facingRight) {
                dx -= moveSpeed; // speed increases progressively
                if(dx < -maxSpeed)
                    dx = -maxSpeed;

                // max speed reached
            }
            else{
                dx += moveSpeed;
                if(dx > maxSpeed)
                    dx = maxSpeed;

            }

        }

    }

    public void update(){
        checkTileMapCollision();

        if (!falling){
            if (dx == 0){
            if (facingRight == true)
                facingRight = false;
            else
                facingRight = true;
        }}


        getNextPosition();

        setPosition(xtemp, ytemp);

        animation.update();



    }


}
