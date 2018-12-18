package entity.dynamic;

import entity.ImageAnimator;
import entity.strategy.StrategyFactory;
import tilemap.TileMap;

import java.awt.*;

public class EnemyWaterShark extends DynamicSprite {
    private int DEFAULT_ROW = 0;
    private final int[] numFrames = {8};

    private double factorX;
    private double factorY;



    public EnemyWaterShark(TileMap tm) {
        super(tm);

        setFacingRight(false);
        setMovingRight(false);

        setStrategyX(StrategyFactory.getInstance().getMoveStrategyX()); // Initially the shark is moving to the left
        setStrategyY(StrategyFactory.getInstance().getStopStrategyY());

        factorX = 1.0;
        factorY= 0;


        loadSpriteAsset(numFrames, "/Enemies/enemy_shark.png");

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
        imageAnimator.setDelay(100);
    }

    @Override
    public void update() {


        setNextDelta(factorX,factorY); //I prossimi dx e dy che dovrebbe assumere lo shark
        checkTileMapCollision(); // Qui dato che i tile al di sotto dello squalo potrebbero essere non bloccanti la strategy viene settata automaticamante
        //a MoveStrategyY con falling a true
        setStrategyY(StrategyFactory.getInstance().getStopStrategyY());


        if (getDx() == 0) { // Not moving -> collision detected
            // Revert facing and moving strategy
            if(isFacingRight()) {
                setMovingRight(false);
                setFacingRight(false);
            }
            else {
                setMovingRight(true);
                setFacingRight(true);
            }

        }


        imageAnimator.update();

    }

    @Override
    public void draw(Graphics2D g) {

        if(!isFacingRight()){
        g.drawImage(
                imageAnimator.getImage(),
                (int)(getX()+tileMap.getX()-width/2),
                (int)(getY()+tileMap.getY()-height/2),
                width,
                height,
                null);}
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

