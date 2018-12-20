package gamestate;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import main.GamePanelController;
import tilemap.Background;

public class MenuState extends GameState {

	private GameStateManager gsm;
	private Background bg;
	private int currentChoice = 0;
	private String[] options =  {"Start", "Demo", "Quit"};
	
	private Color titleColor;
	private Font titleFont;
	private Font optionsFont;
	
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	/**
	 * Init the MenuState Object
	 */
	@Override
	public void init() {
		bg = new Background("/Background/bg_menu2.jpeg",1);

		titleColor = new Color(57, 110, 249);
		titleFont = new Font("Century Gothic",Font.PLAIN,16);
		optionsFont = new Font("Arial",Font.PLAIN,14);
	}

	/**
	 * Draws the elements of the MenuState on the g element
	 * @param g
	 */
	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		
		// Draw title
        g.setColor(titleColor);
        g.setFont(titleFont);
        FontMetrics fm = g.getFontMetrics();
        String title = "Captain Corkleg";
        int x = (GamePanelController.WIDTH - fm.stringWidth(title)) / 2;
        g.drawString(title,x,70);
		
		// Draw menu options
		g.setFont(optionsFont);
		for(int i= 0; i<options.length; i++) {
			if(currentChoice == i) {
				g.setColor(Color.green);
			} else {
				g.setColor(Color.GRAY);
			}
			fm = g.getFontMetrics();
			x = (GamePanelController.WIDTH - fm.stringWidth(options[i])) / 2;
			g.drawString(options[i], x, 120+i*20);
		}
		
	}

	/**
	 *
	 * @param keyCode this parameter is used to choose between different options
	 */
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
			gsm.setState(GameStateManager.State.DEMO);
		else if(currentChoice == 2)
			System.exit(0);
	}


	@Override
	public void update() {

	}
}
