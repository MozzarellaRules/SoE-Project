package gamestate;

import entity.*;
import tilemap.TileMap;
import java.util.ArrayList;

public interface ISpriteFactory {

    LevelOnePlayer createPlayer(TileMap tileMap);
    ArrayList<Enemy> createEnemies(TileMap tileMap);
    ArrayList<Ammo> createAmmo(TileMap tileMap);
    Projectile createProjectile(TileMap tileMap, LevelOnePlayer p);
    Health createHealth(TileMap tileMap);

}
