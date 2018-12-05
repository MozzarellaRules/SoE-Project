package entity;

import tilemap.Tile;
import tilemap.TileMap;

import java.awt.*;

public class DynamicSprite extends Sprite {
    protected boolean left;
    protected boolean right;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;

    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;

    protected boolean facingRight;

    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    //collision
    protected int currRow;
    protected int currCol;
    protected double xdest;
    protected double ydest;
    protected double xtemp;
    protected double ytemp;

    public DynamicSprite(TileMap tm) {
        super(tm);
    }

    public void setLeft(boolean b) {
        left=b;
    }
    public void setRight(boolean b) {
        right=b;
    }
    public void setDown(boolean b) {
        down=b;
    }
    public void setJumping(boolean b) {
        jumping=b;
    }

    //Metodo per controllare le collisioni
    public void checkTileMapCollision() {
        currCol= (int)x/tileSize;//Colonna corrente
        currRow=(int)y/tileSize;//Riga corrente
        xdest=x+dx;//Prevedo la prossima posizione su X PRIMA di muovermi
        ydest=y+dy;//Prevedo la prossima posizione su Y PRIMA di muovermi
        xtemp=x;//Salvo la posizione corrente della coordinata X
        ytemp=y;//Salvo la posizione corrente della coordinata Y
        calculateCorners(x,ydest); //Controllo se quando mi muovo SU o GIU il blocco che andrò a toccare è BLOCKED
        if(dy<0) {//Quando salto
            if(topLeft||topRight) { //Se il blocco sopra di me è BLOCKED  (SPIEGAZIONE SOGGETTA A MODIFICHE :D)
                dy=0;//smetti di salire ( PErché sopra di me è BLOCKED
                ytemp=currRow*tileSize+cheight/2;//(FORSE)Imposta la posizione proprio sotto al blocco BLOCKED
            }
            else {//SE invece il blocco sopra di me è NORMAL
                ytemp+=dy;//Continua a salire ( Ricordo che adesso è ytemp che mi fa muovere)
            }
        }
        if(dy>0) {//Quando scendo
            if(bottomLeft||bottomRight) {//Se il blocco sotto di me è BLOCKED
                dy=0;//smetti di scendere
                falling=false;//NON STO PIU' CADENDO
                ytemp=(currRow+1)*tileSize-cheight/2;// (FORSE) Imposto la posizione SOPRA al blocco BLOCKED
            }
            else {//Se invece il blocco è NORMAL
                ytemp+=dy;//Muoviti normalmente, continua a cadere
            }
        }
        calculateCorners(xdest,y); //Stesso identico procedimento, questa volta per il movimento sull'asse X
        if(dx<0) {//Se mi muovo all'indietro
            if(topLeft||topRight) { //E il blocco è BLOCKED
                dx=0;//Fermati
                xtemp=(currCol)*tileSize+cwidth/2;//(FORSE) Imposta la posizione alla desra del blocco BLOCKED
            }
            else {//SE il blocco è NORMAL
                xtemp+=dx;	//Muoviti normalmente
            }
        }
        if(dx>0) {//Se ti muovi verso destra
            if(topRight||bottomRight) {//E il blocco è BLOCKED
                dx=0;//Fermati
                xtemp=(currCol+1)*tileSize-cwidth/2;//(FORSE) Imposta la posizione alla sinistra del blocco BLOCKED
            }
            else {//Se il blocco è NORMAL
                xtemp+=dx;//Continua a muoverti normalmente
            }
        }
        //Ho dei dubbi su questo piccolo pezzo, al massimo vediamo di persona
        if(!falling) {
            calculateCorners(x,ydest+1);
            if(!bottomLeft && !bottomRight) {
                falling=true;
            }
        }
    }

    public boolean intersects(Sprite o){
        Rectangle r1= getRectangle();
        Rectangle r2= o.getRectangle();
        return r1.intersects(r2);
    }

    //Funzione che calcola i blocchi intorno al personaggio (su giu destra e sinistra) e capisce se sono BLOCKED o NORMAL
    public void calculateCorners(double x, double y) {
        int leftTile=(int)(x-cwidth/2)/tileSize; //Prendo il tile alla mia sinistra
        int rightTile=(int)(x+cwidth/2-1)/tileSize;//Tile alla mia destra

        int topTile=(int)(y-cheight/2)/tileSize;//Tile sopra di me
        int bottomTile=(int)(y+cheight/2-1)/tileSize;//Tile sotto di me

        //going outside of the map give an out of bounds exception(here the fix)
        if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
                leftTile < 0 || rightTile >= tileMap.getNumCols()) {
            topLeft = topRight = bottomLeft = bottomRight = false;
            return;
        }

        //Acquisisco il TIPO dei Tile intorno a me
        int tl=tileMap.getType(topTile, leftTile);
        int tr=tileMap.getType(topTile, rightTile);
        int bl=tileMap.getType(bottomTile, leftTile);
        int br=tileMap.getType(bottomTile, rightTile);

        //Controllo se uno di questi 4 è BLOCKED, in tal caso VEDI GIU
        topLeft=tl== Tile.BLOCKED;
        topRight=tr==Tile.BLOCKED;
        bottomLeft=bl==Tile.BLOCKED;
        bottomRight=br==Tile.BLOCKED;
    }
}
