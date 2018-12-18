package gamestate;


import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import main.GamePanelController;
import tilemap.Background;

public class GameOverState extends GameState {

	private GameStateManager gsm;
	private Background bg;
	private int currentChoice = 0;
	private String[] options =  {"Retry", "Quit"};

	private Color titleColor;
	private Font titleFont;
	private Font optionsFont;
	
	public GameOverState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {
		bg = new Background("/Background/bg_gameover.jpeg",1);

		titleColor = new Color(128,0,0);
		titleFont = new Font("Century Gothic",Font.PLAIN,28);
		optionsFont = new Font("Arial",Font.PLAIN,14);
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);

		// Draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Captain Corkleg",76,70);

		// Draw menu options
		g.setFont(optionsFont);
		for(int i= 0; i<options.length; i++) {
			if(currentChoice == i) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.RED);
			}
			FontMetrics fm = g.getFontMetrics();
			int x = (GamePanelController.WIDTH - fm.stringWidth(options[i])) / 2;
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

	@Override
	public void keyReleased(int keyCode) { }

	private void submitOption() {
		if(currentChoice == 0)
			gsm.setState(GameStateManager.State.LEVEL1STATE);
		else if(currentChoice == 1)
			System.exit(0);
	}
	
	@Override
	public void update() {
		bg.update();
	}
	
}
