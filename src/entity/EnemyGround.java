package entity;

import entity.strategy.*;
import tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyGround extends DynamicSprite {

    private int WALK_ROW = 0; // Walk state use row 0 of the sprite asset
    private final int[] numFrames = {12}; // Row 0 has 12 frames

    private boolean facingRight;

    // Sprite imageAnimator
    private ArrayList<BufferedImage[]> sprites;

    public EnemyGround(TileMap tm){
        super(tm);

        setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
        setStrategyY(StrategyFactory.getInstance().getFallStrategy());

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

    public boolean isFacingRight() { return facingRight; }
    public void setFacingRight(boolean facingRight) { this.facingRight = facingRight; }

    private boolean isDead;
    private int health;

    public boolean isDead(){ return isDead; }

    public void hit(int damage){
        health -= damage;

        if (health < 0) {
            health = 0;
        }
        if (health == 0) {
            isDead = true;
        }
    }

    @Override
    public void update(){
        getNextDelta();
        checkTileMapCollision();

        if (getDy() == 0){
            if (getDx() == 0){
                if(isFacingRight()) {
                    setFacingRight(false);
                    setStrategyX(StrategyFactory.getInstance().getMoveLeftStrategy());
                }
                else {
                    setFacingRight(true);
                    setStrategyX(StrategyFactory.getInstance().getMoveRightStrategy());
                }
            }
        } else {
            setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
        }

        imageAnimator.update();
    }

    @Override
    public void draw(Graphics2D g) {
        if(isFacingRight()) {
            g.drawImage(
                    imageAnimator.getImage(),
                    (int)(getX()+tileMap.getX()-width/2),
                    (int)(getY()+tileMap.getY()-height/2),
                    null);
        }
        else {
            g.drawImage(
                    imageAnimator.getImage(),
                    (int)(getX()+tileMap.getX()-width/2+width),
                    (int)(getY()+tileMap.getY()-height/2),
                    -width,
                    height,
                    null);
        }
    }

}
