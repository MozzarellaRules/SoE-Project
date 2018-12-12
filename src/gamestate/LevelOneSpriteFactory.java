package gamestate;

import entity.*;
import tilemap.TileMap;

import java.util.ArrayList;

public class LevelOneSpriteFactory implements ISpriteFactory {
    private static LevelOneSpriteFactory instance;

    private LevelOneSpriteFactory() { }

    public static LevelOneSpriteFactory getInstance() {
        if(instance == null) {
            instance = new LevelOneSpriteFactory();
        }
        return instance;
    }

    @Override
    public LevelOnePlayer createPlayer(TileMap tileMap) {
        LevelOnePlayer player = new LevelOnePlayer(tileMap);
        player.setPosition(tileMap.getTileSize()*13,tileMap.getTileSize()*45);
        return player;
    }

    @Override
    public ArrayList<Enemy> createEnemies(TileMap tileMap) {
        ArrayList<Enemy> enemies = new ArrayList<>();

        Enemy e1 = new EnemyGround(tileMap);
        Enemy e2 = new EnemyGround(tileMap);

        e1.setFacingRight(true);

        e1.setPosition(tileMap.getTileSize()*15,tileMap.getTileSize()*47);
        e2.setPosition(tileMap.getTileSize()*14,tileMap.getTileSize()*54);

        enemies.add(e1);

        return enemies;
    }

    @Override
    public ArrayList<Ammo> createAmmo(TileMap tileMap) {
        ArrayList<Ammo> ammo = new ArrayList<>();

        Ammo ammo1 = new Ammo(tileMap);
        Ammo ammo2 = new Ammo(tileMap);

        ammo1.setPosition(tileMap.getTileSize()*16,tileMap.getTileSize()*48+6);
        ammo2.setPosition(tileMap.getTileSize()*16,tileMap.getTileSize()*51+6);

        ammo.add(ammo1);
        ammo.add(ammo2);

        return ammo;
    }

    @Override
    public Projectile createProjectile(TileMap tileMap, LevelOnePlayer p) {
        Projectile projectile = new Projectile(tileMap, p.isFacingRight());
        projectile.setPosition(p.getX()-3, p.getY());
        return projectile;
    }

    @Override
    public Health createHealth(TileMap tileMap) {
        Health health = new Health(tileMap);
        health.setHealth(3);
        return health;
    }

}
