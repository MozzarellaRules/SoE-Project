package gamestate;

import entity.dynamic.PlayerGround;
import main.GamePanelController;
import tilemap.TileMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DemoState extends GameState {
    private GameStateManager gsm;

    private TileMap tileMap;
    private LevelOneState levelOne;
    private PlayerGround player;

    private boolean gamePaused;

    private Suggestion currentSuggestion;
    private ArrayList<Suggestion> suggests;

    private Map<Integer[],Integer> movementsPressed;
    private Map<Integer[],Integer> movementsReleased;

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

        this.suggests = new ArrayList<>();

        this.currentSuggestion = null;

        movementsPressed = new HashMap<>();
        movementsReleased = new HashMap<>();

        createSuggestions();
        createMovements();
    }

    private void createSuggestions() {
        Suggestion s1 = new Suggestion("resources/Objects/suggest_go_right.png", 45, 13, KeyEvent.VK_RIGHT);
        Suggestion s2 = new Suggestion("resources/Objects/suggest_go_left.png", 51, 31, KeyEvent.VK_LEFT);
        Suggestion s3 = new Suggestion("resources/Objects/suggest_ammo.png", 51, 16, -1);
        Suggestion s4 = new Suggestion("resources/Objects/suggest_shoot.png", 48, 19, KeyEvent.VK_SPACE);
        Suggestion s5 = new Suggestion("resources/Objects/suggest_jump.png", 48, 28, KeyEvent.VK_UP);
        Suggestion s6 = new Suggestion("resources/Objects/suggest_demo_completed.png", 51, 14, -1);
        Suggestion s7 = new Suggestion("resources/Objects/suggest_limited_ammo.png", 51, 15, -1);

        suggests.add(s1);
        suggests.add(s2);
        suggests.add(s3);
        suggests.add(s4);
        suggests.add(s5);
        suggests.add(s6);
        suggests.add(s7);
    }

    private void createMovements() {
        movementsPressed.put(new Integer[]{45,13},KeyEvent.VK_RIGHT);
        movementsReleased.put(new Integer[]{45,16},KeyEvent.VK_RIGHT);

        movementsPressed.put(new Integer[]{48,16},KeyEvent.VK_RIGHT);

        movementsPressed.put(new Integer[]{48,20},KeyEvent.VK_SPACE);
        movementsPressed.put(new Integer[]{48,22},KeyEvent.VK_SPACE);

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

        int current_row_player = player.getY()/tileMap.getTileSize();
        int current_col_player = player.getX()/tileMap.getTileSize();

        for(Suggestion s: suggests) {
            if(!player.isFalling() && s.getRow()==current_row_player && s.getCol()==current_col_player) {
                gamePaused = true;
                currentSuggestion = s;
            }
        }

        for(Integer[] pos : movementsPressed.keySet()) {
            if(pos[0] == current_row_player && pos[1] == current_col_player) {
                levelOne.keyPressed(movementsPressed.get(pos));
                movementsPressed.remove(pos);
                break;
            }
        }

        for(Integer[] pos : movementsReleased.keySet()) {
            if(pos[0] == current_row_player && pos[1] == current_col_player) {
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

        if(currentSuggestion != null) {
            g.drawImage(currentSuggestion.getImage(), GamePanelController.WIDTH-150, 20, null);
        }
    }

    @Override
    public void keyPressed(int keyCode) {
        if(gamePaused) {
            if(currentSuggestion.getWantedInput() == keyCode || currentSuggestion.getWantedInput() == -1) {
                suggests.remove(currentSuggestion);
                currentSuggestion = null;
                gamePaused = false;
                if(suggests.size() == 0) {
                    gsm.setState(GameStateManager.State.LEVEL1STATE);
                }
            }
        }
    }

    @Override
    public void keyReleased(int keyCode) {

    }

    class Suggestion {
        private int row;
        private int col;
        private Image image;
        private int wantedInput;

        Suggestion(String path, int row, int col, int wantedInput) {
            this.image = new ImageIcon(path).getImage();
            this.row = row;
            this.col = col;
            this.wantedInput = wantedInput;
        }

        public int getRow() { return row; }
        public int getCol() { return col; }
        public Image getImage() { return image; }
        public int getWantedInput() { return wantedInput; }
    }
}
