package GameState;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import TileMap.Background;

public class MenuState extends GameState {

	//La schermata del menù
	
	private Background bg; //Un oggetto background
	private int currentChoice=0; //La scelta corrente tra i valori della stringa di sotto
	private String[] options =  {
			"Start",
			//"Help",
			"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm=gsm;
		
		try {
			
			bg = new Background("/Background/Immagine.png",1); //Carico il background
			bg.setVector(-0.1,0); // imposto come si deve muovere, in questo caso va all'indietro sull'asse x
			
			titleColor= new Color(128,0,0);
			titleFont= new Font("Century Gothic",Font.PLAIN,28);
			
			font= new Font("Arial",Font.PLAIN,12);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	




	//funzione per mettere a video il menu
	@Override
	public void draw(Graphics2D g) {
		//draw background
		bg.draw(g); //richiamo la draw dell'oggetto background
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Captain Corkleg",85,70);
		
		//draw menu options
		g.setFont(font);
		for(int i= 0; i< options.length;i++) {
			if(i == currentChoice) {
				g.setColor(Color.GREEN);
			}
			else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 175, 140+i*15);
		}
		
	}

	
	//Cosa succede se premi invio (Vedi keyPressed)
	private void select() {
		if (currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE); //Carico il livello 1
		}
		if (currentChoice == 1) {
			System.out.println("Hi, you'r playing our game. Have fun!"); //Bruno che scrive cose
			
		}
		if (currentChoice == 2) {
			System.exit(0);//Esci dal gioco
		}
	}
	
	
	//Cosa succede quando premi e rilasci i tasti
	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length -1;
			}
		}
		
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
		
	}


	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	//Come si aggiorna l'immagine ogni volta che il thread esce dal wait
	@Override
	public void update() {
		bg.update(); //richiamo l'update del background ( Vedi Background)
	}
	
}
