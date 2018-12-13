package gamestate;

import entity.dynamic.*;
import entity.strategy.StrategyFactory;
import main.GamePanelController;
import tilemap.Background;
import tilemap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class LevelTwoState extends GameState {
    public static String BG_PATH = "/Background/full_background2.jpeg";
    public static String TILESET_PATH = "/Tilesets/tileset_sarah.png";
    public static String MAP_PATH = "/Maps/mappa_livello2.txt";

    private GameStateManager gsm;

    private TileMap tileMap;
    private Background bg;

    private PlayerWater player;
    private ArrayList<EnemyWaterOctopus> octopus;

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
        createPlayer();
        createEnemies();

        bg = new Background(BG_PATH,0.5);

        // The camera is centered on the player
        tileMap.setPosition(GamePanelController.WIDTH/2-player.getX(), GamePanelController.HEIGHT/2-player.getY());
    }

    public void createPlayer() {
        this.player = new PlayerWater(tileMap);
        this.player.setPosition(tileMap.getTileSize()*3,tileMap.getTileSize()*3);
    }

    public void createEnemies() {
        EnemyFactory enemyFactory = EnemyFactoryConcrete.getInstace();
        int pos[][] = {
                {5,5}
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

    @Override
    public void update() {
        player.update();

        // The camera follows the character
        tileMap.setPosition(GamePanelController.WIDTH/2-player.getX(), GamePanelController.HEIGHT/2-player.getY());

        // The background moves with the character
        bg.setPosition(tileMap.getX(), 0);

        // If the player is dead... set state GameOver
        if(player.isDead() || player.notOnScreen()){
            gsm.setState(GameStateManager.State.GAMEOVER);
        }

        for(EnemyWaterOctopus o : octopus){
            // If the enemy is not on the screen, he does not move
            if(!o.notOnScreen()){
                o.update();
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
        tileMap.draw(g);

        player.draw(g);

        for(EnemyWaterOctopus o : octopus){
            o.draw(g);
        }

        g.setFont(new Font("Arial",Font.BOLD,12));
        g.setColor(new Color(0, 109, 225));
        g.drawString("Level Two", 25, 50);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.setStrategyX(StrategyFactory.getInstance().getMoveLeftStrategy());
                player.setMovingLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                player.setStrategyX(StrategyFactory.getInstance().getMoveRightStrategy());
                player.setMovingRight(true);
                break;
            case KeyEvent.VK_UP:
                if(!player.isFalling()) // No jump in mid-air
                    player.setStrategyY(StrategyFactory.getInstance().getJumpStrategy());
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
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
                player.setStrategyY(StrategyFactory.getInstance().getFallStrategy());
                break;
        }
    }
}
