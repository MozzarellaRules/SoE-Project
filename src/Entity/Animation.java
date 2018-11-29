package Entity;

import java.awt.image.BufferedImage;


//Classe che si occupa di animare il personaggio
// In pratica abbiamo un'immagine dove sono rappresentate sulle righe tutti i disegni relativi a un certo movimento
//E sulle colonne ci sono tutte le animazioni per quel movimento
public class Animation {
	
	private BufferedImage[] frames; //Questo è il vettore di immagini per le animazioni
	private int currentFrame;//In quale momento dell'animazione ci troviamo
	
	private long startTime;//momento in cui parte l'animazione
	private long delay;//Dopo quanto passare all'animazione successiva
	
	private boolean playedOnce;//Per azioni come l'attacco c'è bisogno di stoppare l'animazione una volta eseguita una volta
	
	//Costruttore
	public void Animation() {
		playedOnce=false;
	}
	
	//
	public void setFrames(BufferedImage[] frames) {
		this.frames=frames;
		currentFrame=0;
		startTime=System.nanoTime();
		playedOnce=false;
	}
	
	
	public void setDelay(long d) {
		delay=d;
	}
	
	public void setFrame(int i) {
		currentFrame=i;
	}
	
	
	//Cuore dell'animazione
	public void update() {
		if(delay==-1) //Se il ritardo è -1 vuol dire che non ci sono animazioni per quell'azione e quindi non fa niente
			return;
		long elapsed=(System.nanoTime()-startTime)/1000000;
		if(elapsed>delay) { //Dopo un certo ritardo passa all'animazione successiva, cosi si simula la camminata per esempio
			currentFrame++;
			startTime=System.nanoTime();
		}
		if(currentFrame==frames.length) { //Quando hai passato tutte le animazione per un'azione ritorna all'inizio e imposta playedOnce=true
			currentFrame=0;
			playedOnce=true;
		}
	}

	public int getFrame() {
		return currentFrame;
	}
	public BufferedImage getImage() {
		return frames[currentFrame];
	}
	public boolean hasPlayedOnce() {
		return playedOnce;
	}
	
}
