package reversi_gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This application is the custom button used in the UI for Reversi.
 *
 * @author Chris Miller
 */
public class Tile extends Button{
    private int row;   // the row the button is in.
    private int col;   // the column the button is in.

    /**
     * the initalization of the custom buttom.
     * and set the immage to be empty.
     *
     * @param name the string containing the location of the button.
     */
    public Tile(String name){
        super(); // name
        Image imageDecline = new Image(this.getClass().getResourceAsStream("empty.jpg"));
        this.setGraphic(new ImageView(imageDecline));
        this.setDisable(true);
        String[] temp = name.split(" ");
        this.row = Integer.parseInt(temp[0]);
        this.col = Integer.parseInt(temp[1]);
    }

    /**
     * allows changing of the immage of a button.
     *
     * @param name the name of the immage to update to.
     */
    public void setImmage(String name){
        Image imageDecline = new Image(this.getClass().getResourceAsStream(name));
        this.setGraphic(new ImageView(imageDecline));
    }

    /**
     * get the row of the button
     * @return the row
     */
    public int getRow(){
        return this.row;
    }

    /**
     * get the column of the button
     * @return the column
     */
    public int getCol(){
        return this.col;
    }

}