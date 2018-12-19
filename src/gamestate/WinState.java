package gamestate;

import main.GamePanelController;
import tilemap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class WinState extends GameState {

    private GameStateManager gsm;
    private Background bg;
    private int currentChoice = 0;
    private String[] options =  {"Restart", "Quit"};

    private Color titleColor;
    private Font titleFont;
    private Font optionsFont;

    public WinState(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }

    @Override
    public void init() {
        bg = new Background("/Background/bg_level_two.jpeg",1);

        titleColor = new Color(212, 198, 61);
        titleFont = new Font("Century Gothic",Font.PLAIN,28);
        optionsFont = new Font("Arial",Font.PLAIN,14);
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);

        // Draw title
        g.setColor(titleColor);
        g.setFont(titleFont);
        FontMetrics fm = g.getFontMetrics();
        String title = "You win!";
        int x = (GamePanelController.WIDTH - fm.stringWidth(title)) / 2;
        g.drawString(title,x,70);

        // Draw menu options
        g.setFont(optionsFont);
        for(int i=0; i<options.length; i++) {
            if(currentChoice == i) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }
            fm = g.getFontMetrics();
            x = (GamePanelController.WIDTH - fm.stringWidth(options[i])) / 2;
            g.drawString(options[i], x, 140+i*20);
        }

    }

    @Override
    public void keyPressed(int keyCode) {
        if(keyCode == KeyEvent.VK_ENTER) {
            submitOption();
        }
        if(keyCode == KeyEvent.VK_UP) {
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = options.length-1;
            }
        }

        if(keyCode == KeyEvent.VK_DOWN) {
            currentChoice++;
            if(currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    private void submitOption() {
        if(currentChoice == 0)
            gsm.setState(GameStateManager.State.LEVEL1STATE);
        else if(currentChoice == 1)
            System.exit(0);
    }

    @Override
    public void keyReleased(int keyCode) { }

    @Override
    public void update() {
        bg.update();
    }
}
