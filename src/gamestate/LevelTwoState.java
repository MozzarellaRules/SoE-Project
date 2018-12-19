package gamestate;

import entity.dynamic.*;
import entity.Item;
import entity.strategy.StrategyFactory;
import entity.visual.Health;
import entity.visual.OxygenLevel;
import main.GamePanelController;
import tilemap.Background;
import tilemap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class LevelTwoState extends GameState {
    public static String BG_PATH = "/Background/bg_level_two.jpeg";
    public static String TILESET_PATH = "/Tilesets/tileset_level_two.png";
    public static String MAP_PATH = "/Maps/map_level_two.txt";


    private GameStateManager gsm;

    private TileMap tileMap;
    private Background bg;

    private PlayerWater player;
    private ArrayList<EnemyWaterOctopus> octopus;
    private ArrayList<EnemyWaterShark> sharks;
    private ArrayList<Item> oxygenBubbles;

    private Health health;
    private OxygenLevel oxygenLevel;

    private Item treasure;

    public LevelTwoState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    @Override
    public void init() {
        tileMap = new TileMap(32);
        tileMap.loadTiles(TILESET_PATH);
        tileMap.loadMap(MAP_PATH);

        this.octopus = new ArrayList<>();
        this.sharks = new ArrayList<>();
        this.oxygenBubbles = new ArrayList<>();
        createPlayer();
        createOctopusEnemies();
        createSharkEnemies();
        createHealth();
        createOxygenBubbles();
        this.oxygenLevel = new OxygenLevel(tileMap);
        this.oxygenLevel.setOxygen(player.getOxygen());
        player.addObserver(oxygenLevel);
        createTresure();

        bg = new Background(BG_PATH,1);

        // The camera is centered on the player
        tileMap.setPosition(GamePanelController.WIDTH/2-player.getX(), GamePanelController.HEIGHT/2-player.getY());
    }

    public void createPlayer() {
        this.player = new PlayerWater(tileMap);
        this.player.setPosition(tileMap.getTileSize()*3,tileMap.getTileSize()*3);
    }

    public void createOctopusEnemies() {
        EnemyFactory enemyFactory = EnemyFactoryConcrete.getInstace();
        int[][] pos = {
                {16,6},
                {10,23},
                {13,42},
                {6,48},
                {14,47}
        };

        try {
            for(int i=0; i<pos.length; i++) {
                octopus.add((EnemyWaterOctopus) enemyFactory.createEnemy(EnemyFactory.EnemyType.OKTOPUS,tileMap,pos[i][0],pos[i][1]));
            }
        }
        catch (InvalidParameterException e){
            System.err.println("Invalid Enemy Creation Type");
        }
    }

    public void createSharkEnemies() {
        EnemyFactory enemyFactory = EnemyFactoryConcrete.getInstace();
        int[][] pos = {
                {13,17},
                {9,14},
                {10,33},
                {6,48},
                {16,49}
        };

        try {
            for(int i=0; i<pos.length; i++) {
                sharks.add((EnemyWaterShark) enemyFactory.createEnemy(EnemyFactory.EnemyType.SHARK,tileMap,pos[i][0],pos[i][1]));
            }
        }
        catch (InvalidParameterException e){
            System.err.println("Invalid Enemy Creation Type");
        }
    }

    private void createHealth() {
        this.health = new Health(tileMap);
        this.health.setHealth(player.getHealth());
        this.player.addObserver(health);
    }

    public void createOxygenBubbles() {
        int[][] pos = {
                {15, 19},
                {7,7},
                {7,12},
                {18,21},
                {14,45}
        };

        for(int i=0; i<pos.length; i++) {
            Item o = new Item(tileMap, "/Objects/asset_bubbles.png", 5);
            o.setPosition(tileMap.getTileSize()*pos[i][1]+16,tileMap.getTileSize()*pos[i][0]+16);
            oxygenBubbles.add(o);
        }
    }

    public void createTresure(){
        treasure = new Item(tileMap,"/Objects/asset_final_chest.png",16);
        treasure.setPosition(32,(32*18)+16);
    }

    public void checkWin()  {
        if(player.intersects(treasure)) {
            gsm.setState(GameStateManager.State.WINSTATE);
        }
    }

    @Override
    public void update() {
        player.update();

        // The camera follows the character
        tileMap.setPosition(GamePanelController.WIDTH/2-player.getX(), GamePanelController.HEIGHT/2-player.getY());

        // The background moves with the character
        bg.setPosition(tileMap.getX(), 0);

        // If the player is dead... set state GameOver
        if(player.isDead() || player.notOnScreen()) {
            gsm.setState(GameStateManager.State.GAMEOVER);
        }

        for(EnemyWaterOctopus o : octopus) {
            // If the enemy is not on the screen, he does not move
            o.update();
            if(o.intersects(player)){
                    player.hit(1);

            }
        }

        for(EnemyWaterShark s : sharks){
            // If the enemy is not on the screen, he does not move
            s.update();
            if(s.intersects(player)) {
                player.hit(1);
            }
        }

        for(Item o : oxygenBubbles) {
            if(!o.notOnScreen()){
            o.update();
            if(player.intersects(o)){
                oxygenBubbles.remove(o);
                player.incrementOxygenLevel();
                break;
            }
        }
        }

        health.update();

        treasure.update();
        checkWin();
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
        tileMap.draw(g);

        player.draw(g);

        for(EnemyWaterOctopus o : octopus) {
            o.draw(g);
        }

        for(EnemyWaterShark s : sharks) {
            s.draw(g);
        }

        for(Item o : oxygenBubbles) {
            o.draw(g);
        }

        health.draw(g);
        oxygenLevel.draw(g);
        treasure.draw(g);
    }

    @Override
    public void keyPressed(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                player.setMovingLeft(true);
                player.setMovingRight(false);
                player.setFacingRight(false);
                player.setStrategyX(StrategyFactory.getInstance().getMoveStrategyX());
                break;
            case KeyEvent.VK_RIGHT:
                player.setMovingRight(true);
                player.setMovingLeft(false);
                player.setFacingRight(true);
                player.setStrategyX(StrategyFactory.getInstance().getMoveStrategyX());
                break;
            case KeyEvent.VK_UP:
                player.setStrategyY(StrategyFactory.getInstance().getMoveStrategyY());
                player.setFalling(false);
                break;
        }
    }

    @Override
    public void keyReleased(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                if(!player.isMovingRight())
                    player.setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
                player.setMovingLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                if(!player.isMovingLeft())
                    player.setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
                player.setMovingRight(false);
                break;
            case KeyEvent.VK_UP:
                player.setStrategyY(StrategyFactory.getInstance().getMoveStrategyY());
                player.setFalling(true);
                break;
        }
    }
}
