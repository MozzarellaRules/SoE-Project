package entity;

import tilemap.Tile;
import tilemap.TileMap;

import java.awt.*;

public abstract class DynamicSprite extends Sprite {
    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingDown;
    private boolean jumping;
    private boolean falling;

    private boolean facingRight;

    private boolean isTopLeftBlocked;
    private boolean isTopRightBlocked;
    private boolean isBottomLeftBlocked;
    private boolean isBottomRightBlocked;

    private int currRow;
    private int currCol;
    private double xNew;
    private double yNew;

    private double xCurrent;
    private double yCurrent;

    public DynamicSprite(TileMap tm) {
        super(tm);
    }

    public boolean isMovingLeft() { return movingLeft; }
    public boolean isMovingRight() { return movingRight; }
    public boolean isMovingDown() { return movingDown; }
    public boolean isJumping() { return jumping; }
    public boolean isFalling() { return falling; }
    public boolean isFacingRight() { return facingRight; }

    public void setMovingLeft(boolean movingLeft) { this.movingLeft = movingLeft; }
    public void setMovingRight(boolean movingRight) { this.movingRight = movingRight; }
    public void setMovingDown(boolean movingDown) { this.movingDown = movingDown; }
    public void setJumping(boolean jumping) { this.jumping = jumping; }
    public void setFalling(boolean falling) { this.falling = falling; }
    public void setFacingRight(boolean facingRight) { this.facingRight = facingRight; }

    /**
     * Set dx/dy to zero if the character collide with a blocked tile
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
        if(getDy()<0) { // Jumping
            if(isTopLeftBlocked|| isTopRightBlocked) { // Tile above is blocked
                setDy(0); // Stop jumping
                yCurrent = currRow*tileSize+ collisionBoxHeight /2;
            }
            else {
                yCurrent += getDy();
            }
        }
        else if(getDy()>0) { // Falling
            if(isBottomLeftBlocked || isBottomRightBlocked) { // Tile below is blocked
                setDy(0); // Stop falling
                falling = false;
                yCurrent = (currRow+1)*tileSize- collisionBoxHeight /2;
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
            if(isTopLeftBlocked|| isTopRightBlocked) { // The block on the left is blocked
                setDx(0); // Stop
                xCurrent = (currCol)*tileSize+ collisionBoxWidth /2;
            }
            else {
                xCurrent += getDx();
            }
        }
        else if(getDx()>0) { // Right movement
            if(isTopRightBlocked || isBottomRightBlocked) { // The block on the right is blocked
                setDx(0); // Stop
                xCurrent = (currCol+1)*tileSize- collisionBoxWidth /2;
            }
            else {
                xCurrent += getDx();
            }
        }

        // Set falling True if the block below the character are normal
        if(!falling) {
            calculateCorners(getX(), yNew +1);
            if(!isBottomLeftBlocked && !isBottomRightBlocked) {
                falling = true;
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
     * Set the variables isTopLeftBlocked, isTopRightBlocked, isBottomLeftBlocked, isBottomRightBlocked
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
            isTopLeftBlocked = isTopRightBlocked = isBottomLeftBlocked = isBottomRightBlocked = false;
            return;
        }

        // Determine the type of the tile around the character
        isTopLeftBlocked = tileMap.getType(topTileRow, leftTileColumn) == Tile.BLOCKED;
        isTopRightBlocked = tileMap.getType(topTileRow, rightTileColumn) == Tile.BLOCKED;
        isBottomLeftBlocked = tileMap.getType(bottomTileRow, leftTileColumn) == Tile.BLOCKED;
        isBottomRightBlocked = tileMap.getType(bottomTileRow, rightTileColumn) == Tile.BLOCKED;
    }
}
