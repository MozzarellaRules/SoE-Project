package TileMap;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

import Main.GamePanel;


//Mettetevi comodi, questa è la mappa 
public class TileMap {
	//position
	private double x;
	private double y;

	//bordi
	private int xmin;
	private int xmax;
	private int ymin;
	private int ymax;
	private double tween;

	//map
	private int[][] map;   //Matrice di interi
	private int tileSize;  //dimensione del Tile (quello che passiamo nel costruttore)
	private int numRows;   //numero di righe
	private int numCols;   // numero di colonne
	private int width;     //Larghezza
	private int height;     //altezza
	
	//tileset
	private BufferedImage tileset; //Il tileSet è l'immagine che spezzettiamo per creare i Tile
	private int numTilesAcross;	   //Numero di tile lungo l'asse X
	private int numTilesLines;		// Numero di tile lungo l'asse y
	private Tile[][] tiles;         //Il tileset è una matrice di Tile
	
	//drawing
	private int rowOffset; // which row to start drawing
	private int colOffset; // which column to start drawing
	private int numRowsToDraw; //Quante righe disegnare
	private int numColsToDraw;  // Quante colonne disegnare
	
	
	//Costruttore
	public TileMap(int tileSize) {
		this.tileSize = tileSize; //dimensione dei tile della mappa
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2; //Disegno soltanto le righe che ci entrano nello schermo (+2 perché così non rischio che non si aggiornino in tempo)
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;  //Tale e quale ma per le colonne
		tween = 0.07; //Ignorate questa  cosa,tanto poi lo mette a 1 -.-
	}
	
	
	//Metodo per caricare i Tile da un'immagine
	public void loadTiles(String s) {
		try {
			tileset=ImageIO.read(getClass().getResourceAsStream(s)); // reading image
			numTilesAcross = tileset.getWidth()/tileSize; //  number of columns
			numTilesLines = tileset.getHeight()/tileSize; // number of rows
			tiles = new Tile[numTilesLines][numTilesAcross]; // tiles matrix

			// Caricamento immagini delle tiles
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) {
				for(int row = 0; row < numTilesLines; row++) {
					if(row < 2) { // The first two lines of the tileset are normal, the others are blocking instead
						subimage=tileset.getSubimage(col*tileSize, row*tileSize, tileSize, tileSize); // Extract a subimage from tileset
						tiles[row][col]=new Tile(subimage,Tile.NORMAL);
						System.out.println("Normal:" + row);
					} else {
						subimage=tileset.getSubimage(col*tileSize, row*tileSize, tileSize, tileSize);
						tiles[row][col]=new Tile(subimage,Tile.BLOCKED);
						System.out.println("Blocked:" + row);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Metodo per caricare la mappa
	public void loadMap(String s) {
		try {
			InputStream in= getClass().getResourceAsStream(s);
			BufferedReader br= new BufferedReader(new InputStreamReader(in));
			numCols = Integer.parseInt(br.readLine()); // Read from file the number of columns
			System.out.println("numCols:"+numCols);
			numRows = Integer.parseInt(br.readLine()); // Read from file the number of rows
			System.out.println("NumRows:" +numRows);
			map = new int[numRows][numCols]; // map matrix
			width = numCols*tileSize;
			height = numRows*tileSize;
			xmin = GamePanel.WIDTH -width;
			xmax = 0;
			ymin = GamePanel.HEIGHT-height;
			ymax = 0;
			String delims = ","; //Segnalo il carattere " ," come delimitatore per tutti i numeri del file della mappa
			for(int row=0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col=0; col<numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]); //Leggo il numero sul file della mappa e lo salvo
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getTileSize() {
		return tileSize;
	}
	public int getx() {
		return (int) x;
	}
	public int gety() {
		return (int) y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getNumRows() { 
		return numRows; 
	}
	public int getNumCols() { 
		return numCols; 
	}
	
	
	//A partira da una riga e una colonna identifico il tile e scopro se è NORMAL o BLOCKED
	public int getType(int row,int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	
	//Imposto la posizione della mappa
	public void setPosition(double x,double y) {
		this.x += (x-this.x) * tween;
		this.y += (y-this.y) * tween;
		fixBounds();
		
		colOffset=(int)-this.x /tileSize;
		rowOffset=(int)-this.y/tileSize;
	}
	//Se esco dai bordi la rimetto a posto
	private void fixBounds() {
		if(x<xmin)
			x=xmin;
		if(y<ymin)
			y=ymin;
		if(x>xmax)
			x=xmax;
		if(y>ymax)
			y=ymax;
	}
	
	//Come si disegna la mappa
	public void draw(Graphics2D g) {
		for(int row=rowOffset;row<rowOffset+numRowsToDraw;row++) { //Per tutte le righe
			if(row>=numRows)
				break;
			for(int col=colOffset;col<colOffset+numColsToDraw;col++) { // E per tutte le colonne
				if(col>=numCols)
					break;
				if(map[row][col]==0) //ZERO sarebbe il numero che identifica lo spazio vuoto nel file della mappa
					continue;
				int rc=map[row][col]; //Prendo il numero della mappa
				int r=rc/numTilesAcross; //Identifico una riga
				int c=rc%numTilesAcross; //Identifico una colonna
				g.drawImage(tiles[r][c].getImage(),(int)x+col*tileSize,(int)y+row*tileSize,null);
				//Cerco nella matrice dei tile l'immagine associata a quella riga e a quella colonna e la disegno in quelle coordinate
			}
			
		}
	}

	public void setTween(double i) {
		this.tween=i;
	}
		
}

	
	
	
	
	

