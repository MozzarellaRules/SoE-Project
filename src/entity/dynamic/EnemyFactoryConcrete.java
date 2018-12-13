package entity.dynamic;

import entity.DynamicSprite;
import tilemap.TileMap;

import java.security.InvalidParameterException;

public class EnemyFactoryConcrete implements EnemyFactory {

    private static EnemyFactory instace;

    private EnemyFactoryConcrete() { }

    public static EnemyFactory getInstace(){
        if (instace==null)
            instace = new EnemyFactoryConcrete();
        return instace;
    }

    public DynamicSprite createEnemy(EnemyType type, TileMap tm,int row, int col) throws InvalidParameterException {

        switch(type){
            case PIRATE:
                EnemyGround enemyGround = new EnemyGround(tm);
                enemyGround.setPosition(tm.getTileSize()*col,tm.getTileSize()*row);
                return enemyGround;

            case OKTOPUS:
                EnemyWaterOktopus enemyOctopus = new EnemyWaterOktopus(tm);
                enemyOctopus.setPosition(tm.getTileSize()*col,tm.getTileSize()*row);
                return enemyOctopus;

            case SHARK:
                EnemyGround enemyShark = new EnemyGround(tm);
                enemyShark.setPosition(tm.getTileSize()*col,tm.getTileSize()*row);
                return enemyShark;

            default:
                throw new InvalidParameterException();

        }

    }
}
