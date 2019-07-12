package place.client.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import place.PlaceBoard;
import place.PlaceColor;
import place.client.Model.ClientModel;
import place.client.network.NetworkClient;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class PlaceGUI extends Application implements Observer {

    private GridPane grid;
    private Label lbl;
    private ClientModel modle;
    private NetworkClient serverConn;
    private Map<String, String> params = null;
    private int DIM;
    private int color;

    public static void main(String[] args) { Application.launch(args); }

    @Override
    public void init() {
//        try {
            this.getParamNamed("host");
            this.serverConn = new NetworkClient(getParamNamed("host"),getParamNamed("name"),Integer.parseInt(getParamNamed("port")));
            this.modle = this.serverConn.getModle();
//        }
    }

        private String getParamNamed(String name){
            if (params == null) {
                params = super.getParameters().getNamed();
            }
            if (!params.containsKey(name)) {
            } else {
                return params.get(name);
            }
            return null;
        }


    public void start(Stage mainStage) {
        BorderPane border = new BorderPane();
        this.DIM = this.modle.getDIM();
//        int cols = this.model.getNCols();   // used in methods
//        int rows = this.model.getNRows();   // used in methods
        this.lbl = new Label();
        this.grid = new GridPane();
        int gap = 5;
        this.grid.setVgap(gap); // gap between grid cells
        this.grid.setHgap(gap);
        this.setUpGrid(this.DIM);


        // This all should be in a Collection/ ArrayList - so that it can be itereated.. but ran out of time.
        ToggleButton tb1 = new ToggleButton("0");
        tb1.setBackground((new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb2 = new ToggleButton("1");
        tb2.setBackground((new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb3 = new ToggleButton("2");
        tb3.setBackground((new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb4 = new ToggleButton("3");
        tb4.setBackground((new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb5 = new ToggleButton("4");
        tb5.setBackground((new Background(new BackgroundFill(Color.MAROON, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb6 = new ToggleButton("5");
        tb6.setBackground((new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb7 = new ToggleButton("6");
        tb7.setBackground((new Background(new BackgroundFill(Color.OLIVE, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb8 = new ToggleButton("7");
        tb8.setBackground((new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb9 = new ToggleButton("8");
        tb9.setBackground((new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb10 = new ToggleButton("9");
        tb10.setBackground((new Background(new BackgroundFill(Color.LIME, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb11 = new ToggleButton("A");
        tb11.setBackground((new Background(new BackgroundFill(Color.TEAL, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb12 = new ToggleButton("B");
        tb12.setBackground((new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb13 = new ToggleButton("C");
        tb13.setBackground((new Background(new BackgroundFill(Color.NAVY, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb14 = new ToggleButton("D");
        tb14.setBackground((new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb15 = new ToggleButton("E");
        tb15.setBackground((new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleButton tb16 = new ToggleButton("F");
        tb16.setBackground((new Background(new BackgroundFill(Color.FUCHSIA, CornerRadii.EMPTY, Insets.EMPTY))));
        ToggleGroup group = new ToggleGroup();
        tb1.setToggleGroup(group);
        tb2.setToggleGroup(group);
        tb3.setToggleGroup(group);
        tb4.setToggleGroup(group);
        tb5.setToggleGroup(group);
        tb6.setToggleGroup(group);
        tb7.setToggleGroup(group);
        tb8.setToggleGroup(group);
        tb9.setToggleGroup(group);
        tb10.setToggleGroup(group);
        tb11.setToggleGroup(group);
        tb12.setToggleGroup(group);
        tb13.setToggleGroup(group);
        tb14.setToggleGroup(group);
        tb15.setToggleGroup(group);
        tb16.setToggleGroup(group);
        GridPane colorgrid = new GridPane();
        colorgrid.add(tb1,0,0);
        colorgrid.add(tb2,1,0);
        colorgrid.add(tb3,2,0);
        colorgrid.add(tb4,3,0);
        colorgrid.add(tb5,4,0);
        colorgrid.add(tb6,5,0);
        colorgrid.add(tb7,6,0);
        colorgrid.add(tb8,7,0);
        colorgrid.add(tb9,8,0);
        colorgrid.add(tb10,9,0);
        colorgrid.add(tb11,10,0);
        colorgrid.add(tb12,11,0);
        colorgrid.add(tb13,12,0);
        colorgrid.add(tb14,13,0);
        colorgrid.add(tb15,14,0);
        colorgrid.add(tb16,15,0);
        tb1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(0);
                }}});
        tb2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(1);
                }}});
        tb3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(2);
                }}});
        tb4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(3);
                }}});
        tb5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(4);
                }}});
        tb6.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(5);
                }}});
        tb7.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(6);
                }}});
        tb8.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(7);
                }}});
        tb9.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(8);
                }}});
        tb10.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(9);
                }}});
        tb11.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(10);
                }}});
        tb12.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(11);
                }}});
        tb13.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(12);
                }}});
        tb14.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(13);
                }}});
        tb15.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(14);
                }}});
        tb16.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource(); // this will return the button that was pressed
                if (source instanceof ToggleButton) {  //check that the source is really a button
                    System.out.println("button clicked!");
                    setColor(15);
                }}});

        border.setBottom(colorgrid);
        border.setCenter(this.grid);
        mainStage.setTitle(this.serverConn.getName());
        mainStage.setScene(new Scene(border));
        mainStage.show();
        this.modle.addObserver(this);
        this.refresh();
    }

    private void setColor(int color){
            this.color = color;
    }
    private int getcolor(){
        return this.color;
    }
    private void setUpGrid(int DIM) {
        for (int row=0; row<DIM; ++row) {
            for (int col=0; col<DIM; ++col) {
                pixle t = new pixle(row + " " + col);   // make a new tile
                this.grid.add(t, col, row);   // add the tile to the gridpane
                t.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event) {
                        Object source = event.getSource(); // this will return the button that was pressed
                        if (source instanceof pixle) {  //check that the source is really a button
                            System.out.println("button clicked!");
                            int trow = ((pixle) source).getRow();
                            int tcol = ((pixle) source).getCol();  // get row / col
                            serverConn.sendMove((trow), (tcol), getcolor());   // this.server... did not work so i removed the this. learn more about why that is...
                        }
                    }
                });
            }
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        assert o == this.modle : "Update from non-model Observable";
        this.refresh();
    }

    private void refresh(){
        for (int row = 0; row <= this.DIM - 1; ++row) {
            for (int col = 0; col <= this.DIM - 1; col++) {
                for (Node node : grid.getChildren()) {
                    if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                        pixle b = (pixle) node;   // for every node
                        PlaceColor s = modle.getBoard().getTile(row, col).getColor();   // get the player of the tile and set the image accordingly
                        switch (s) {
                            case BLACK:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.BLACK));
                                break;
                            case GRAY:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.GRAY));
                                break;
                            case SILVER:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.SILVER));
                                break;
                            case WHITE:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.WHITE));
                                break;
                            case MAROON:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.MAROON));
                                break;
                            case RED:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.RED));
                                break;
                            case OLIVE:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.OLIVE));
                                break;
                            case YELLOW:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.YELLOW));
                                break;
                            case GREEN:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.GREEN));
                                break;
                            case LIME:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.LIME));
                                break;
                            case TEAL:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.TEAL));
                                break;
                            case AQUA:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.AQUA));
                                break;
                            case NAVY:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.NAVY));
                                break;
                            case BLUE:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.BLUE));
                                break;
                            case PURPLE:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.PURPLE));
                                break;
                            case FUCHSIA:
                                javafx.application.Platform.runLater(() ->
                                        b.setColor(Color.FUCHSIA));
                                break;
                        }
                    }
                }
            }
        }
    }


    @Override
    public void stop() {
        serverConn.close();
    }


}
