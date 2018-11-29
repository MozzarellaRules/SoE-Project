package Entity;


import TileMap.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TreasureChest extends MapObject {


    private int raw;
    private int col;
    private BufferedImage bfImage;
    private ImageIcon imageIc;
    private Tile tile;

    public TreasureChest(TileMap tm){
        super(tm);
        raw = 2;
        col = 2;
        //bfImage = tm.getTiles()[raw][col].getImage(); //Load the Image from the tileset
        imageIc = new ImageIcon("resources/Objects/BulletDrop.png");
    }


    public void draw(Graphics2D g){
        setMapPosition();
        g.drawImage(imageIc.getImage(),(int) (x+xmap),(int)( y+ymap),null);
    }


}
