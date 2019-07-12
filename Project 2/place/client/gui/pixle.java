package place.client.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class pixle extends Rectangle {
    private int row;   // the row the button is in.
    private int col;   // the column the button is in.

    public pixle(String name){
        super(); // name
        this.setDisable(false);
        this.setWidth(80);
        this.setHeight(80);
        this.setFill(Color.WHITE);
        String[] temp = name.split(" ");
        this.row = Integer.parseInt(temp[0]);
        this.col = Integer.parseInt(temp[1]);
    }

    public void setColor(Color color){
        this.setFill(color);
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

}
