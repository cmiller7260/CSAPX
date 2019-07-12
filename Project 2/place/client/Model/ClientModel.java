package place.client.Model;

import place.PlaceBoard;
import place.PlaceTile;

import java.util.Observable;

public class ClientModel extends Observable {

    PlaceBoard board = null;
    Object lock;

    public ClientModel(PlaceBoard board){
        this.board = board;
        this.lock = new Object();
    }
    //this should be a board of "PlaceBoard" that is the observable part of the program...

    public PlaceBoard getBoard (){
        synchronized (this.lock){
            return this.board;
        }
    }
    public void setTile(PlaceTile tile){
        synchronized (this.lock){
            this.board.setTile(tile);
            System.out.println("Placing the tile....");
        }
        this.setChanged();
        this.notifyObservers(tile);
    }
    public int getDIM(){
       return this.board.DIM;
    }
}
