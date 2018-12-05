package main;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import gamestate.GameStateManager;

//Pannello dove si visualizza il gioco
public class GamePanel extends JPanel implements Runnable {
	public static final int WIDTH=380, HEIGHT=250;
	public static final int SCALE=2; // (width*2),(height*2)
	
	// Game Thread
	private Thread thread;
	private boolean running;
	private int FPS=60;
	private long targetTime=1000/FPS; // Wait duration of the thread... around 16 ms (60 fps)

	private GameStateManager gsm;
	private BufferedImage image;
	private Graphics2D g;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		this.setFocusable(true);
		requestFocusInWindow(); // To request focus
	}

	/**
	 * Make and start the game thread and set an action listener on the panel
	 */
	public void addNotify() {
		super.addNotify();
		if(thread==null) {
			thread=new Thread(this);
			addKeyListener(new TAdapter());
			thread.start();
		}
	}

	/**
	 * Init the thread creating an image for drawing stuff on it and creating the GameStateManager
	 */
	private void init() {
		image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		g=(Graphics2D) image.getGraphics();
		running=true;
		gsm=new GameStateManager();
	}

	/**
	 * Run the game inside the thread
	 */
	public void run() {
		init();
		long start;
		long elapsed;
		long wait;

		while (running){
			start=System.nanoTime(); // Get the current time, in nanoseconds

			// These methods can take different times at each loop cycle
			update();
			draw();
			drawToScreen();

			// Effective time taken by the methods above
			elapsed=System.nanoTime()-start;

			// Define the wait time
			wait=targetTime-elapsed/1000000;
			if(wait <0) {
				wait=5;
			}
			
			try {
				Thread.sleep(wait);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Update the state
	 */
	private void update() {
		gsm.update();
	}

	/**
	 * Draw stuff on an image... related to a specific state (menu, level or gameover)
 	 */
	private void draw() {
		gsm.draw(g);
	}

	/**
	 * Draw an image on the panel
 	 */
	private void drawToScreen() {
		Graphics gPanel=getGraphics();
		gPanel.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
		gPanel.dispose();
	}

	/**
	 * Handle key events
	 * In fact the events are related to a specific state, so the key pressed/released is passed to the gsm
	 */
	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			gsm.keyReleased(e.getKeyCode());
		}

		@Override
		public void keyPressed(KeyEvent e) {
			gsm.keyPressed(e.getKeyCode());
		}
	}
	
	
}
