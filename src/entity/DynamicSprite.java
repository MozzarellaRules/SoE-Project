package entity;

import entity.strategy.StrategyFactory;
import entity.strategy.StrategyX;
import entity.strategy.StrategyY;
import tilemap.Tile;
import tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class DynamicSprite extends Sprite {
    private boolean falling;

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

    private StrategyX strategyX;
    private StrategyY strategyY;

    public DynamicSprite(TileMap tm) {
        super(tm);
    }

    /**
     * GETTERS
     */
    public boolean isFalling() { return falling; }
    public StrategyX getStrategyX() { return strategyX; }
    public StrategyY getStrategyY() { return strategyY; }

    /**
     * SETTERS
     */
    public void setStrategyX(StrategyX strategyX) { this.strategyX = strategyX; }
    public void setStrategyY(StrategyY strategyY) { this.strategyY = strategyY; }

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

        if(isFalling()) {

            setStrategyY(StrategyFactory.getInstance().getFallStrategy());
        }
        if(getDy()<0) { // Jumping
            if(isTopLeftBlocked|| isTopRightBlocked) { // Tile above is blocked
                setDy(0);
                yCurrent = currRow*tileSize+collisionBoxHeight/2;
                falling = true;
            }
            else {
                yCurrent += getDy();
            }
        }
        else if(getDy()>0) { // Falling
            if(isBottomLeftBlocked || isBottomRightBlocked) { // Tile below is blocked
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
            if(isTopLeftBlocked|| isTopRightBlocked) { // The block on the left is blocked
                setDx(0);
                xCurrent = (currCol)*tileSize+collisionBoxWidth/2;
            }
            else {
                xCurrent += getDx();
            }
        }
        else if(getDx() > 0) { // Right movement
            if(isTopRightBlocked || isBottomRightBlocked) { // The block on the right is blocked
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
            if(!isBottomLeftBlocked && !isBottomRightBlocked) {
                setStrategyY(StrategyFactory.getInstance().getFallStrategy());
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

    /**
     * Update dx/dy according to the current strategy
     */
    public void getNextDelta() {
        double dx = getStrategyX().recalcDx(getDx());
        double dy = getStrategyY().recalcDy(getDy());

        setDx(dx);
        setDy(dy);
    }
}
