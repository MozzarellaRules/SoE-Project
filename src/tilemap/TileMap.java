package tilemap;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

import main.GamePanelController;



public class TileMap {
	private double x;
	private double y;

	// Borders
	private int xmin;
	private int xmax;
	private int ymin;
	private int ymax;

	// Map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// TileSet
	private BufferedImage tileset;
	private int numTilesX;
	private int numTilesY;
	private Tile[][] tiles;
	
	private int rowOffset; // which row to start drawing
	private int colOffset; // which column to start drawing
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;

		// Numbers of rows/cols to draw dependent on the size of the panel
		numRowsToDraw = GamePanelController.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanelController.WIDTH / tileSize + 2;
	}

	public void loadTiles(String s) {
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesX = tileset.getWidth()/tileSize;
			numTilesY = tileset.getHeight()/tileSize;
			tiles = new Tile[numTilesY][numTilesX];

			BufferedImage subimage;
			for(int col = 0; col < numTilesX; col++) {
				for(int row = 0; row < numTilesY; row++) {
					if(row < 2) { // The first two lines of the tileset are normal, the others are blocking instead
						subimage = tileset.getSubimage(col*tileSize, row*tileSize, tileSize, tileSize); // Extract a subimage from tileset
						tiles[row][col] = new Tile(subimage,Tile.NORMAL);
						//System.out.println("Normal:" + row);
					} else {
						subimage=tileset.getSubimage(col*tileSize, row*tileSize, tileSize, tileSize);
						tiles[row][col] = new Tile(subimage,Tile.BLOCKED);
						//System.out.println("Blocked:" + row);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String s) {
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br= new BufferedReader(new InputStreamReader(in));
			numCols = Integer.parseInt(br.readLine()); // Read from file the number of columns
			System.out.println("numCols:"+numCols);
			numRows = Integer.parseInt(br.readLine()); // Read from file the number of rows
			System.out.println("NumRows:"+numRows);
			map = new int[numRows][numCols];
			width = numCols*tileSize;
			height = numRows*tileSize;
			xmin = GamePanelController.WIDTH-width;
			xmax = 0;
			ymin = GamePanelController.HEIGHT-height;
			ymax = 0;
			String delims = ","; // Set the character" ," as a delimiter
			for(int row=0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col=0; col<numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getTileSize() {
		return tileSize;
	}
	public int getX() {
		return (int) x;
	}
	public int getY() {
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
		int r = rc / numTilesX;
		int c = rc % numTilesX;
		return tiles[r][c].getType();
	}
	
	//Imposto la posizione della mappa
	public void setPosition(double x,double y) {
		this.x += (x-this.x);
		this.y += (y-this.y);
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
				int r=rc/ numTilesX; //Identifico una riga
				int c=rc% numTilesX; //Identifico una colonna
				g.drawImage(tiles[r][c].getImage(),(int)x+col*tileSize,(int)y+row*tileSize,null);
				//Cerco nella matrice dei tile l'immagine associata a quella riga e a quella colonna e la disegno in quelle coordinate
			}
			
		}
	}

	public Tile[][] getTiles(){return tiles;}
		
}

	
	
	
	
	

