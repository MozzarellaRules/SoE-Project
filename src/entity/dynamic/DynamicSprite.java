package entity.dynamic;

import entity.Sprite;
import entity.strategy.StrategyFactory;
import entity.strategy.StrategyX;
import entity.strategy.StrategyY;
import tilemap.Tile;
import tilemap.TileMap;

import java.awt.*;

public abstract class DynamicSprite extends Sprite {
    private boolean falling;

    protected boolean topLeftBlocked;
    protected boolean topRightBlocked;
    private boolean bottomLeftBlocked;
    private boolean bottomRightBlocked;

    private int currRow;
    private int currCol;
    private double xNew;
    private double yNew;

    private double xCurrent;
    private double yCurrent;

    private StrategyX strategyX;
    private StrategyY strategyY;

    private boolean facingRight;


    private boolean movingLeft;
    private boolean movingRight;


    public DynamicSprite(TileMap tm) {
        super(tm);
    }

    /**
     * GETTERS
     */
    public boolean isFalling() { return falling; }
    public boolean isFacingRight(){return facingRight;}

    public StrategyX getStrategyX() { return strategyX; }
    public StrategyY getStrategyY() { return strategyY; }

    public boolean isMovingLeft() { return movingLeft; }
    public boolean isMovingRight() { return movingRight; }

    /**
     * SETTERS
     */
    public void setStrategyX(StrategyX strategyX) { this.strategyX = strategyX; }
    public void setStrategyY(StrategyY strategyY) { this.strategyY = strategyY; }
    public void setFalling(boolean falling){ this.falling = falling;}
    public void setFacingRight(boolean facingRight){this.facingRight=facingRight;}

    public void setMovingLeft(boolean movingLeft) { this.movingLeft = movingLeft; }
    public void setMovingRight(boolean movingRight) { this.movingRight = movingRight; }

    /**
     * Set dx/dy to zero if the character collide with a blocked tile
     * Set current position
     */
    public void checkTileMapCollision() {
        currCol = (int)getX()/tileSize;
        currRow = (int)getY()/tileSize;
        xNew = getX()+getDx();
        yNew = getY()+getDy();
        xCurrent = getX();
        yCurrent = getY();

        /**
         * Movement on the Y-axis
         */
        calculateCorners(getX(), yNew);
        if(getDy() < 0) { // Jumping
            if(topLeftBlocked || topRightBlocked) { // Tile above is blocked
                setDy(0);
                yCurrent = currRow*tileSize+collisionBoxHeight/2;
                falling = true;
            }
            else {
                yCurrent += getDy();
            }
        }
        else if(getDy() > 0) { // Falling
            if(bottomLeftBlocked || bottomRightBlocked) { // Tile below is blocked
                setDy(0);
                yCurrent = (currRow+1)*tileSize-collisionBoxHeight/2;
                falling = false;
            }
            else {
                yCurrent += getDy();
            }
        }

        /**
         * Movement on the X-axis
         */
        calculateCorners(xNew,getY());
        if(getDx() < 0) { // Left movement
            if(topLeftBlocked || topRightBlocked) { // The block on the left is blocked
                setDx(0);
                xCurrent = (currCol)*tileSize+collisionBoxWidth/2;
            }
            else {
                xCurrent += getDx();
            }
        }
        else if(getDx() > 0) { // Right movement
            if(topRightBlocked || bottomRightBlocked) { // The block on the right is blocked
                setDx(0);
                xCurrent = (currCol+1)*tileSize-collisionBoxWidth/2;
            }
            else {
                xCurrent += getDx();
            }
        }

        // Set falling True if the block below the character are normal
        if(!falling) {
            calculateCorners(getX(), yNew +1);
            if(!bottomLeftBlocked && !bottomRightBlocked) {
                falling = true;
                setStrategyY(StrategyFactory.getInstance().getMoveStrategyY());

            }
        }

        // Set the current position
        setPosition(xCurrent, yCurrent);
    }

    /**
     * Determine if the current sprite collided another sprite
     * @param s
     * @return
     */
    public boolean intersects(Sprite s){
        Rectangle r1 = getRectangle();
        Rectangle r2 = s.getRectangle();
        return r1.intersects(r2);
    }



    /**
     * Determine if the tiles around the player are blocked
     * Set the variables topLeftBlocked, topRightBlocked, bottomLeftBlocked, bottomRightBlocked
     * @param x
     * @param y
     */
    public void calculateCorners(double x, double y) {
        // Determine the column of the tile on the left/right of the character
        int leftTileColumn = (int)(x- collisionBoxWidth /2)/tileSize;
        int rightTileColumn = (int)(x+ collisionBoxWidth /2-1)/tileSize;

        // Determine the row of the tile above/below the character
        int topTileRow = (int)(y- collisionBoxHeight /2)/tileSize;
        int bottomTileRow = (int)(y+ collisionBoxHeight /2-1)/tileSize;

        // Reached the bounds of the map
        if(topTileRow < 0 || bottomTileRow >= tileMap.getNumRows() ||
                leftTileColumn < 0 || rightTileColumn >= tileMap.getNumCols()) {
            topLeftBlocked = topRightBlocked = bottomLeftBlocked = bottomRightBlocked = false;
            return;
        }

        // Determine the type of the tile around the character
        topLeftBlocked = tileMap.getType(topTileRow, leftTileColumn) == Tile.BLOCKED;
        topRightBlocked = tileMap.getType(topTileRow, rightTileColumn) == Tile.BLOCKED;
        bottomLeftBlocked = tileMap.getType(bottomTileRow, leftTileColumn) == Tile.BLOCKED;
        bottomRightBlocked = tileMap.getType(bottomTileRow, rightTileColumn) == Tile.BLOCKED;
    }

    /**
     * Update dx/dy according to the current strategy
     */
    public void setNextDelta(double factorX,double factorY) {
        double dx = getStrategyX().recalcDx(getDx(),movingRight,factorX);
        double dy = getStrategyY().recalcDy(getDy(),falling,factorY);

        setDx(dx);
        setDy(dy);
    }
}
