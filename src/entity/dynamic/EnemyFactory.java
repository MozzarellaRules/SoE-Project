package entity.dynamic;

import tilemap.TileMap;

import java.security.InvalidParameterException;

/**
 * This is the abstract factory interface we use to create the different types of enemies.
 */
public interface EnemyFactory {

    enum EnemyType{
        OKTOPUS,
        PIRATE,
        SHARK
    }

    /**
     * This is the interface method that we will make "real" in a concrete class.
     * @param type is the type of the enemy.
     * @param tm is the map on which to generate the object.
     * @param row is the position on the X axis.
     * @param col is the position on the Y axis.
     * @return is a Dynamic sprite object (in this case, an enemy).
     * @throws InvalidParameterException
     */
    public DynamicSprite createEnemy(EnemyType type, TileMap tm, int row, int col) throws InvalidParameterException;

}
