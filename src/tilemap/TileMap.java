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
	
	// Tileset
	private BufferedImage tileset;
	private int numTilesX;
	private int numTilesY;
	private Tile[][] tiles;

	// Draw params
	private int rowOffset; // which row to start drawing
	private int colOffset; // which column to start drawing
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;

		numRowsToDraw = GamePanelController.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanelController.WIDTH / tileSize + 2;
	}

	/**
	 * GETTERS
	 */
	public int getTileSize() { return tileSize;	}
	public int getX() {	return (int) x;	}
	public int getY() {	return (int) y;	}
	public int getWidth() {	return width; }
	public int getHeight() { return height; }
	public int getNumRows() { return numRows; }
	public int getNumCols() { return numCols; }
	public Tile[][] getTiles(){return tiles;}

	public void setPosition(double x,double y) {
		this.x += (x-this.x);
		this.y += (y-this.y);
		fixBounds();

		colOffset = (int)-this.x/tileSize;
		rowOffset = (int)-this.y/tileSize;
	}

	private void fixBounds() {
		if(x < xmin)
			x = xmin;
		if(y < ymin)
			y = ymin;
		if(x > xmax)
			x = xmax;
		if(y > ymax)
			y = ymax;
	}

	/**
	 * Load the tiles from tileset
	 * Fill the matrix called "tiles" with "Tile" objects
	 * @param s
	 */
	public void loadTiles(String s) {
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesX = tileset.getWidth()/tileSize;
			numTilesY = tileset.getHeight()/tileSize;
			tiles = new Tile[numTilesY][numTilesX];

			BufferedImage subimage;
			for(int col = 0; col < numTilesX; col++) {
				for(int row = 0; row < numTilesY; row++) {
					// The first two lines of the tileset are normal, the others are blocking instead
					if(row < 2) {
						subimage = tileset.getSubimage(col*tileSize, row*tileSize, tileSize, tileSize); // Extract a subimage from tileset
						tiles[row][col] = new Tile(subimage,Tile.NORMAL);
					} else {
						subimage = tileset.getSubimage(col*tileSize, row*tileSize, tileSize, tileSize);
						tiles[row][col] = new Tile(subimage,Tile.BLOCKED);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the map from .txt file
	 * Fill the integer matrix called containing the id of the tile to be displayed
	 * @param s
	 */
	public void loadMap(String s) {
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br= new BufferedReader(new InputStreamReader(in));
			numCols = Integer.parseInt(br.readLine()); // The first line of the .txt file contains the number of rows
			numRows = Integer.parseInt(br.readLine()); // The second line of the .txt file contains the number of columns
			//System.out.println("numCols:"+numCols);
			//System.out.println("NumRows:"+numRows);
			map = new int[numRows][numCols];
			width = numCols*tileSize;
			height = numRows*tileSize;
			xmin = GamePanelController.WIDTH-width;
			xmax = 0;
			ymin = GamePanelController.HEIGHT-height;
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

	/**
	 * Returns the type of the tile related to a specific row/column of the map
	 * @param row row of the map
	 * @param col column of the map
	 * @return
	 */
	public int getType(int row,int col) {
		int rc = map[row][col];
		int r = rc / numTilesX;
		int c = rc % numTilesX;
		return tiles[r][c].getType();
	}

	public void draw(Graphics2D g) {
		for(int row = rowOffset; row<rowOffset+numRowsToDraw; row++) {
			if(row >= numRows)
				break;
			for(int col=colOffset; col<colOffset+numColsToDraw; col++) {
				if(col >= numCols)
					break;
				if(map[row][col] == 0) // Zero identify an empty tile of the map
					continue;
				int tileId = map[row][col];
				int r = tileId / numTilesX;
				int c = tileId % numTilesX;
				g.drawImage(tiles[r][c].getImage(),(int)x+col*tileSize,(int)y+row*tileSize,null);
			}
		}
	}
}

	
	
	
	
	

