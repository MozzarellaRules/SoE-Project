package entity.dynamic;

import entity.DynamicSprite;
import tilemap.TileMap;

import java.security.InvalidParameterException;

public class EnemyFactoryConcrete implements EnemyFactory {

    private static EnemyFactory instace;

    private EnemyFactoryConcrete(){

    }

    public static EnemyFactory getInstace(){
        if (instace==null)
            instace = new EnemyFactoryConcrete();
        return instace;

    }

    public DynamicSprite createEnemy(EnemyType type, TileMap tm,int row, int col) throws InvalidParameterException {

        switch(type){
            case PIRATE:
                EnemyGround enemy = new EnemyGround(tm);
                enemy.setPosition(tm.getTileSize()*col,tm.getTileSize()*row);
                return enemy;

            case OKTOPUS:
                EnemyGround enemy1 = new EnemyGround(tm);
                enemy1.setPosition(row,col);
                return enemy1;
               // EnemyOktopus oktopus = new EnemyOktopus(tm);
                //oktopus.setPosition(tm.getTileSize()*col,tm.getTileSize()*row);
                //return oktopus;

            case SHARK:
                EnemyGround enemy2 = new EnemyGround(tm);
                enemy2.setPosition(row,col);
                return enemy2;
                // EnemyShark shark = new EnemyOktopus(tm);
                //shark.setPosition(tm.getTileSize()*col,tm.getTileSize()*row);
                //return shark;

                default:
                    throw new InvalidParameterException();

        }


    }
}
