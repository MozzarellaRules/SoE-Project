package entity.dynamic;

import tilemap.TileMap;

import java.security.InvalidParameterException;

/**
 * This is the concrete factory method we use to generate the enemy types.
 * The three subclasses below define the behaviour and the main characteristics of said enemies.
 */
public class EnemyFactoryConcrete implements EnemyFactory {

    private static EnemyFactory instace;

    private EnemyFactoryConcrete() { }

    public static EnemyFactory getInstace(){
        if (instace == null)
            instace = new EnemyFactoryConcrete();
        return instace;
    }

    /**
     * This method creates a certain enemy class depending on the wanted type
     * @param type indicates if an enemy is a pirate, an octpus or a shark
     * @param tm is the tilemap on where the enemy will be generated
     * @param row is the row on which to place the enemy
     * @param col is the column on which to place the enemy
     * @return returns one of the three enemy types
     * @throws InvalidParameterException
     */
    public DynamicSprite createEnemy(EnemyType type, TileMap tm,int row, int col) throws InvalidParameterException {

        switch(type){
            case PIRATE:
                EnemyGround enemyGround = new EnemyGround(tm);
                enemyGround.setPosition(tm.getTileSize()*col,tm.getTileSize()*row);
                return enemyGround;
            case OKTOPUS:
                EnemyWaterOctopus enemyOctopus = new EnemyWaterOctopus(tm);
                enemyOctopus.setPosition(tm.getTileSize()*col+16,tm.getTileSize()*row+16);
                return enemyOctopus;
            case SHARK:
                EnemyWaterShark enemyShark = new EnemyWaterShark(tm);
                enemyShark.setPosition(tm.getTileSize()*col,tm.getTileSize()*row+16);
                return enemyShark;
            default:
                throw new InvalidParameterException();

        }

    }
}
