package main;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import gamestate.GameStateManager;

public class GamePanelController {
	public static final int WIDTH=380, HEIGHT=250;
	public static final int SCALE=2; // (width*2),(height*2)

	private JPanel panel;
	private GameStateManager gsm;

	private Thread thread;

	private BufferedImage image;
	private Graphics2D graphicsImage;

	public GamePanelController() {
		super();
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		panel.setFocusable(true);
		panel.requestFocusInWindow(); // To request focus
		initPanel();
		initThread();
	}

	public JPanel getPanel() { return panel; }

	/**
	 * Init the panel creating an image for drawing things on it and creating the GameStateManager
	 */
	private void initPanel() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		graphicsImage = (Graphics2D) image.getGraphics();

		gsm = new GameStateManager();

		panel.addKeyListener(new TAdapter());
	}

	/**
	 * Make and start the game thread
	 */
	public void initThread() {
		if(thread == null) {
			thread = new GameThread();
			thread.start();
		}
	}

	/**
	 * Update the state
	 */
	private void update() {
		gsm.update();
	}

	/**
	 * Draw things related to a specific state (menu, level or gameover) on an image
	 */
	private void drawToImage() {
		gsm.draw(graphicsImage);
	}

	/**
	 * Draw an image on the panel
 	 */
	private void drawToScreen() {
		panel.getGraphics().drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
		panel.getGraphics().dispose();
	}

	/**
	 * Nested thread class
	 */
	private class GameThread extends Thread {
		private boolean gameRunning;
		private int FPS = 60;
		private long targetTime = 1000/FPS; // Wait duration of the thread... around 16 ms (60 fps)

		public GameThread() {
			gameRunning = true;
		}

		@Override
		public void run() {
			long start;
			long elapsed;
			long wait;

			while (gameRunning){
				start = System.nanoTime(); // Get the current time, in nanoseconds

				// These methods can take different times at each loop cycle
				update();
				drawToImage();
				drawToScreen();

				// Effective time taken by the methods above
				elapsed = System.nanoTime()-start;

				// Define the wait time
				wait = targetTime-elapsed/1000000;
				if(wait < 0) {
					wait = 5;
				}

				try {
					Thread.sleep(wait);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Handle key events
	 * In fact the events are related to a specific state, so the key pressed/released is passed to the gsm
	 */
	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			gsm.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			gsm.keyPressed(e);
		}
	}
	
	
}
