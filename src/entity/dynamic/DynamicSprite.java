package entity.dynamic;

import entity.Sprite;
import entity.strategy.StrategyFactory;
import entity.strategy.StrategyX;
import entity.strategy.StrategyY;
import tilemap.Tile;
import tilemap.TileMap;

import java.awt.*;

public abstract class DynamicSprite extends Sprite {
    private boolean topLeftBlocked;
    private boolean topRightBlocked;
    private boolean bottomLeftBlocked;
    private boolean bottomRightBlocked;

    private StrategyX strategyX;
    private StrategyY strategyY;

    private double factorX;
    private double factorY;

    private boolean facingRight;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean falling;

    /**
     * This constructor creates the dynamic sprite object .
     * @param tm is the map where the item is created
     */
    public DynamicSprite(TileMap tm) {
        super(tm);
    }

    /**
     * This method is used to get the X strategy used by the object.
     * @return the strategyX.
     */
    public StrategyX getStrategyX() { return strategyX; }

    /**
     * This method is used to get the Y strategy used by the object.
     * @return the strategyY
     */
    public StrategyY getStrategyY() { return strategyY; }

    /**
     * This method is used to get the multiplicative factor used to set the speed on the X axis.
     * @return the factorX
     */
    public double getFactorX() { return factorX; }

    /**
     * This method is used to get the multiplicative factor used to set the speed on the Y axis.
     * @return the factorY
     */
    public double getFactorY() { return factorY; }

    /**
     * This method is used to determine whether an object is facing right or not
     * @return the facingRight boolean.
     */
    public boolean isFacingRight(){return facingRight;}

    /**
     * This method is used to determine if an object is moving towards the left.
     * @return the movingLeft boolean.
     */
    public boolean isMovingLeft() { return movingLeft; }

    /**
     * This method is used to determine if an object is moving towards the left.
     * @return the movingRight boolean.
     */
    public boolean isMovingRight() { return movingRight; }

    /**
     * This method is used to determine if an object is falling or not.
     * @return the falling boolean
     */
    public boolean isFalling() { return falling; }

    /**
     * This method sets the X strategy for an object.
     * @param strategyX is the chosen strategy.
     */
    public void setStrategyX(StrategyX strategyX) { this.strategyX = strategyX; }

    /**
     * This method sets the Y strategy for an object.
     * @param strategyY is the chosen strategy.
     */
    public void setStrategyY(StrategyY strategyY) { this.strategyY = strategyY; }

    /**
     * This method is used to set the multiplicative factor for the X axis.
     * @param factorX is the multiplicative factor.
     */
    public void setFactorX(double factorX){this.factorX = factorX;}

    /**
     * This method is used to set the multiplicative factor for the Y axis.
     * @param factorY is the multiplicative factor.
     */
    public void setFactorY(double factorY){this.factorY = factorY;}

    /**
     * This method is used to set the facingRight property of an object.
     * @param facingRight is either true or false.
     */
    public void setFacingRight(boolean facingRight){this.facingRight=facingRight;}

    /**
     * This method is used to set the MovingLeft property of an object (therefore
     * telling the object to move to the left).
     * @param movingLeft is either true or false.
     */
    public void setMovingLeft(boolean movingLeft) { this.movingLeft = movingLeft; }

    /**
     * This method is used to set the MovingRight property of an object (therefore
     * telling the object to move to the right).
     * @param movingRight is either true or false.
     */
    public void setMovingRight(boolean movingRight) { this.movingRight = movingRight; }

    /**
     * This method is used to make an object fall (or have it fall).
     * @param falling is either true or false
     */
    public void setFalling(boolean falling){ this.falling = falling;}

    /**
     * Set dx/dy to zero if the character collide with a blocked tile
     * Set current position
     */
    public void checkTileMapCollision() {
        int currCol = (int) getX() / tileSize;
        int currRow = (int) getY() / tileSize;
        double xNew = getX() + getDx();
        double yNew = getY() + getDy();
        double xCurrent = getX();
        double yCurrent = getY();

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
            if(topLeftBlocked || bottomLeftBlocked) { // The block on the left is blocked
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
     * Determines if the current sprite collided with another sprite
     * @param s is the target sprite.
     * @return is true or false.
     */
    public boolean intersects(Sprite s){
        Rectangle r1 = getRectangle();
        Rectangle r2 = s.getRectangle();
        return r1.intersects(r2);
    }

    /**
     * Determines if the tiles around the player are blocked.
     * Set the variables topLeftBlocked, topRightBlocked, bottomLeftBlocked, bottomRightBlocked
     * @param x is the object's x axis
     * @param y is the object's y axis
     */
    public void calculateCorners(double x, double y) {
        // Determine the column of the tile on the left/right of the character
        int leftTileColumn = (int)(x-collisionBoxWidth/2)/tileSize;
        int rightTileColumn = (int)(x+collisionBoxWidth/2-1)/tileSize;

        // Determine the row of the tile above/below the character
        int topTileRow = (int)(y-collisionBoxHeight/2)/tileSize;
        int bottomTileRow = (int)(y+collisionBoxHeight/2-1)/tileSize;

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
