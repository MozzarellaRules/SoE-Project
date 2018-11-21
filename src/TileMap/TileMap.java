package TileMap;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileMap {
	//position
	private double x;
	private double y;

	//bounds
	private int xmin;
	private int xmax;
	private int ymin;
	private int ymax;
	private double tween;

	//map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	//tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private int numTilesLines;
	private Tile[][] tiles;
	
	//drawing
	private int rowOffset; // which row to start drawing
	private int colOffset; // which column to start drawing
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}
	
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
			String delims = ",";
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
	
	public int getType(int row,int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}

	public void setPosition(double x,double y) {
		this.x += (x-this.x) * tween;
		this.y += (y-this.y) * tween;
		fixBounds();
		
		colOffset=(int)-this.x /tileSize;
		rowOffset=(int)-this.y/tileSize;
	}

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
	
	public void draw(Graphics2D g) {
		for(int row=rowOffset;row<rowOffset+numRowsToDraw;row++) {
			if(row>=numRows)
				break;
			for(int col=colOffset;col<colOffset+numColsToDraw;col++) {
				if(col>=numCols)
					break;
				if(map[row][col]==0)
					continue;
				int rc=map[row][col];
				int r=rc/numTilesAcross;
				int c=rc%numTilesAcross;
				g.drawImage(tiles[r][c].getImage(),(int)x+col*tileSize,(int)y+row*tileSize,null);
			}
			
		}
	}

	public void setTween(double i) {
		this.tween=i;
	}
		
}

	
	
	
	
	

