package entity.dynamic;

import tilemap.TileMap;

import java.security.InvalidParameterException;

public class EnemyFactoryConcrete implements EnemyFactory {

    private static EnemyFactory instace;

    private EnemyFactoryConcrete() { }

    public static EnemyFactory getInstace(){
        if (instace == null)
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
                EnemyWaterOctopus enemyOctopus = new EnemyWaterOctopus(tm);
                enemyOctopus.setPosition(tm.getTileSize()*col+15,tm.getTileSize()*row);
                return enemyOctopus;
            case SHARK:
                EnemyWaterShark enemyShark = new EnemyWaterShark(tm);
                enemyShark.setPosition(tm.getTileSize()*col,tm.getTileSize()*row+15);
                return enemyShark;
            default:
                throw new InvalidParameterException();

        }

    }
}
