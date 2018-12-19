package gamestate;

import entity.dynamic.PlayerGround;
import main.GamePanelController;
import tilemap.TileMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class DemoState extends GameState {
    private GameStateManager gsm;

    private TileMap tileMap;
    private LevelOneState levelOne;
    private PlayerGround player;

    private boolean gamePaused;

    private DemoSuggestion currentSuggestion;
    private Map<Integer[],DemoSuggestion> suggestions;
    private Map<DemoSuggestion,Boolean> completedSuggestions;

    private Map<Integer[],Integer> movementsPressed;
    private Map<Integer[],Integer> movementsReleased;

    public enum DemoSuggestion {
        MOVE_LEFT,
        MOVE_RIGHT,
        JUMP,
        SHOOT,
        GRAB_AMMO,
        COMPLETED_DEMO,
        NO_SUGGEST
    }

    public DemoState(GameStateManager gsm) {
        this.gsm = gsm;
        this.levelOne = new LevelOneState(gsm);
        this.tileMap = levelOne.getTileMap();
        this.player = levelOne.getPlayer();
        init();
    }

    @Override
    public void init() {
        this.gamePaused = false;

        this.currentSuggestion = DemoSuggestion.NO_SUGGEST;
        this.suggestions = new HashMap<>();
        this.completedSuggestions = new HashMap<>();

        movementsPressed = new HashMap<>();
        movementsReleased = new HashMap<>();

        createSuggestions();
        createMovements();
    }

    private void createSuggestions() {
        suggestions.put(new Integer[]{45,13},DemoSuggestion.MOVE_RIGHT);
        suggestions.put(new Integer[]{51,31},DemoSuggestion.MOVE_LEFT);
        suggestions.put(new Integer[]{48,12},DemoSuggestion.GRAB_AMMO);
        suggestions.put(new Integer[]{48,20},DemoSuggestion.SHOOT);
        suggestions.put(new Integer[]{48,14},DemoSuggestion.JUMP);
        suggestions.put(new Integer[]{51,14},DemoSuggestion.COMPLETED_DEMO);
    }

    private void createMovements() {
        movementsPressed.put(new Integer[]{45,13},KeyEvent.VK_RIGHT);
        movementsReleased.put(new Integer[]{45,16},KeyEvent.VK_RIGHT);

        movementsPressed.put(new Integer[]{48,16},KeyEvent.VK_RIGHT);

        movementsPressed.put(new Integer[]{48,21},KeyEvent.VK_SPACE);
        movementsPressed.put(new Integer[]{48,23},KeyEvent.VK_SPACE);

        movementsPressed.put(new Integer[]{48,28},KeyEvent.VK_UP);
        movementsReleased.put(new Integer[]{47,29},KeyEvent.VK_UP);

        movementsReleased.put(new Integer[]{48,32},KeyEvent.VK_RIGHT);

        movementsPressed.put(new Integer[]{51,32},KeyEvent.VK_LEFT);

        movementsPressed.put(new Integer[]{51,24},KeyEvent.VK_SPACE);
        movementsPressed.put(new Integer[]{51,22},KeyEvent.VK_SPACE);
    }

    @Override
    public void update() {
        if(!gamePaused) {
            levelOne.update();
        }

        int tile_row_player = player.getY()/tileMap.getTileSize();
        int tile_col_player = player.getX()/tileMap.getTileSize();

        for(Integer[] pos : suggestions.keySet()) {
            if(pos[0] == tile_row_player && pos[1] == tile_col_player && !player.isFalling()) {
                if(!completedSuggestions.containsKey(suggestions.get(pos))) {
                    gamePaused = true;
                    currentSuggestion = suggestions.get(pos);
                }
            }
        }

        for(Integer[] pos : movementsPressed.keySet()) {
            if(pos[0] == tile_row_player && pos[1] == tile_col_player) {
                levelOne.keyPressed(movementsPressed.get(pos));
                movementsPressed.remove(pos);
                break;
            }
        }

        for(Integer[] pos : movementsReleased.keySet()) {
            if(pos[0] == tile_row_player && pos[1] == tile_col_player) {
                levelOne.keyReleased(movementsReleased.get(pos));
                movementsReleased.remove(pos);
                break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        levelOne.draw(g);
        g.setFont(new Font("Arial",Font.BOLD,9));
        g.setColor(new Color(0, 0, 0));

        if(currentSuggestion == DemoSuggestion.MOVE_RIGHT) {
            g.drawImage(new ImageIcon("resources/Objects/suggest_go_right.png").getImage(),
                    GamePanelController.WIDTH-150, 20, null);
        } else if(currentSuggestion == DemoSuggestion.MOVE_LEFT) {
            g.drawImage(new ImageIcon("resources/Objects/suggest_go_left.png").getImage(),
                    GamePanelController.WIDTH-150, 20, null);
        } else if(currentSuggestion == DemoSuggestion.JUMP) {
            g.drawImage(new ImageIcon("resources/Objects/suggest_jump.png").getImage(),
                    GamePanelController.WIDTH-150, 20, null);
        } else if(currentSuggestion == DemoSuggestion.SHOOT) {
            g.drawImage(new ImageIcon("resources/Objects/suggest_shoot.png").getImage(),
                    GamePanelController.WIDTH-150, 20, null);
        } else if(currentSuggestion == DemoSuggestion.GRAB_AMMO) {
            g.drawImage(new ImageIcon("resources/Objects/suggest_ammo.png").getImage(),
                    GamePanelController.WIDTH-150, 20, null);
        } else if(currentSuggestion == DemoSuggestion.COMPLETED_DEMO) {
            g.drawImage(new ImageIcon("resources/Objects/suggest_demo_completed.png").getImage(),
                    GamePanelController.WIDTH-150, 20, null);
        }
    }

    @Override
    public void keyPressed(int keyCode) {
        if(gamePaused) {
            if(currentSuggestion == DemoSuggestion.COMPLETED_DEMO) {
                gsm.setState(GameStateManager.State.LEVEL1STATE);
            }
            completedSuggestions.put(currentSuggestion, true);
            gamePaused = false;
            currentSuggestion = DemoSuggestion.NO_SUGGEST;
        }
    }

    @Override
    public void keyReleased(int keyCode) {

    }
}
