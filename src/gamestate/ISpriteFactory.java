package gamestate;

import entity.*;
import tilemap.TileMap;
import java.util.ArrayList;

public interface ISpriteFactory {

    Player createPlayer(TileMap tileMap);
    ArrayList<DynamicSprite> createEnemies(TileMap tileMap);

    ArrayList<Ammo> createAmmo(TileMap tileMap);
    Projectile createProjectile(TileMap tileMap, Player p);
    Health createHealth(TileMap tileMap);

}
