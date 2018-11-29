package Main;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import GameState.GameStateManager;

//Pannello dove si visualizza il gioco
public class GamePanel extends JPanel implements Runnable, KeyListener {
	//Dimensions
	public static final int WIDTH=320; // larghezza dello schermo
	public static final int HEIGHT=240;// altezza schermo
	public static final int SCALE=2; // fattore di scala, usato per ingrandire lo schermo ( larghezza*2)(altezza*2)
	
	//Game Thread
	private Thread thread; //thread principale del gioco
	private boolean running; //ci dice quando il gioco è in esecuzione
	private int FPS=60; // vedi sotto
	private long targetTime= 1000/ FPS;// serve per impostare ogni quanto tempo deve stare in wait il thread
	
	//image
	private BufferedImage image;
	private Graphics2D g;
	
	//gameStateManager
	private GameStateManager gsm; 
	
	//Costruttore del pannello, con requestFocus gli do il focus in modo tale che i tasti funzionino
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		this.setFocusable(true);
		requestFocus();
	}
	
	//Crea il thread e aggiunge il listener al pannello
	public void addNotify() {
		super.addNotify();
		if(thread==null) {
			thread=new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	

	private void init() {
		image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		g=(Graphics2D) image.getGraphics();
		running=true;
		gsm= new GameStateManager();
	}
	
	// ciclo del gioco, in pratica quando è in esecuzione fa update,draw e drawToScreen in loop
	public void run() {
		init();
		long start;
		long elapsed;
		long wait;
		//game loop
		int i=0;
		while (running){
			start=System.nanoTime();
			update();
			draw();
			drawToScreen();
			
			elapsed=System.nanoTime()-start;
			
			wait=targetTime - elapsed / 1000000;
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
	
	//A seconda del livello in cui si trova il gsm avrà un update diverso
	private void update() {
		gsm.update();
	}
	
	//A seconda del livello in cui ci troviamo la funzione draw avrà un diverso funzionamento
	private void draw() {
		gsm.draw(g);
	}
	
	// funzione di disegno,lascia perdere  :D
	private void drawToScreen() {
		Graphics g2= getGraphics();
		g2.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
		g2.dispose();
	}
	
	
	//A seconda del livello in cui ci troviamo, le funzioni sotto avranno un comportamento diverso, infatti vengono chiamate su un oggetto GSM
	public void keyTyped(KeyEvent key) {
		
	}
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
	
	
}
