package gamestate;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import tilemap.Background;

public class MenuState extends GameState {

	private GameStateManager gsm;
	private Background bg;
	private int currentChoice = 0;
	private String[] options =  {"Start", "Quit"};
	
	private Color titleColor;
	private Font titleFont;
	private Font optionsFont;
	
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {
		bg = new Background("/Background/Immagine.png",1);
		bg.setVector(-0.1,0);

		titleColor = new Color(128,0,0);
		titleFont = new Font("Century Gothic",Font.PLAIN,28);
		optionsFont = new Font("Arial",Font.PLAIN,12);
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		
		// Draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Captain Corkleg",80,70);
		
		// Draw menu options
		g.setFont(optionsFont);
		for(int i= 0; i<options.length; i++) {
			if(currentChoice == i) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 175, 140+i*15);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			submitOption();
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length-1;
			}
		}

		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { }

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
