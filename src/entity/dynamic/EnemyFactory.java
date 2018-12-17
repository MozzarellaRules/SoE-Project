package entity.dynamic;

import tilemap.TileMap;

import java.security.InvalidParameterException;

public interface EnemyFactory {

    enum EnemyType{
        OKTOPUS,
        PIRATE,
        SHARK
    }

    public DynamicSprite createEnemy(EnemyType type, TileMap tm, int row, int col) throws InvalidParameterException;

}
