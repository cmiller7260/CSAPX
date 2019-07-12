package reversi_gui;

import javafx.application.Application;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import reversi.ReversiException;
import reversi2.Board;
import reversi2.NetworkClient;

/**
 * This application is the UI for Reversi.
 *
 * @author Chris Miller
 */
public class GUI_Client2 extends Application implements Observer {

    /**
     * Connection to network interface to server
     */
    private NetworkClient serverConn;

    /**
     * an instance of board
     */
    private Board model;

    /**
     * a gridpane
     */
    private GridPane grid;

    /**
     * a label
     */
    private Label lbl;

    /**
     * Where the command line parameters will be stored once the application
     * is launched.
     */
    private Map<String, String> params = null;

    /**
     * Look up a named command line parameter (format "--name=value")
     *
     * @param name the string after the "--"
     * @return the value after the "="
     * @throws ReversiException if name not found on command line
     */
    private String getParamNamed(String name) throws ReversiException {
        if (params == null) {
            params = super.getParameters().getNamed();
        }
        if (!params.containsKey(name)) {
            throw new ReversiException(
                    "Parameter '--" + name + "=xxx' missing."
            );
        } else {
            return params.get(name);
        }
    }

    /**
     * initializes the board and the server connection
     */
    @Override
    public void init() {
        this.model = new Board();
        try {
            this.serverConn = new NetworkClient(getParamNamed("host"), Integer.parseInt(getParamNamed("port")), this.model);
        } catch (ReversiException e) {
            e.printStackTrace();
        }
        this.model.initializeGame();
    }

    /**
     * constructs and displays the main stage, and then runs refresh to correctly display the board.
     *
     * @param mainStage
     */
    public void start(Stage mainStage) {
        BorderPane border = new BorderPane();
        int cols = this.model.getNCols();   // used in methods
        int rows = this.model.getNRows();   // used in methods
        this.lbl = new Label();
        this.grid = new GridPane();
        int gap = 5;
        this.grid.setVgap(gap); // gap between grid cells
        this.grid.setHgap(gap);
        this.setUpGrid(rows, cols);
        border.setBottom(this.lbl);
        border.setCenter(this.grid);
        mainStage.setTitle("Chris Miller Lab8 - Reversi");
        mainStage.setScene(new Scene(border));
        mainStage.show();
        this.model.addObserver(this);
        this.refresh();
    }

    /**
     * sets up the gridpane with Tiles (buttons)
     *
     * @param rows how many rows are on the board
     * @param cols how many columns are on the board
     */
    private void setUpGrid(int rows, int cols) {
        for (int row = 1; row <= rows; ++row) {
            for (int col = 1; col <= cols; col++) {
                Tile t = new Tile(row + " " + col);   // make a new tile
                this.grid.add(t, col, row);   // add the tile to the gridpane
                t.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Object source = event.getSource(); // this will return the button that was pressed
                        if (source instanceof Tile) {  //check that the source is really a button
                            int trow = ((Tile) source).getRow();
                            int tcol = ((Tile) source).getCol();  // get row / col
                            serverConn.sendMove((trow - 1), (tcol - 1));   // this.server... did not work so i removed the this. learn more about why that is...
                        }
                    }
                });
            }
        }
    }

    /**
     * notify the end of the game
     */
    private synchronized void endGame() {
        this.notify();
    }

    /**
     * the refresh method that call other methods to update the board
     */
    private void refresh() {
        int cols = this.model.getNCols();
        int rows = this.model.getNRows();   // set the dimentions of the board
        this.updateBoard(rows, cols);   // call the method to update the board and pass the dimetions
        if (model.isMyTurn()) {   // if it is my turn run the my turn method
            this.myTurn(rows, cols);
        } else {   // otherwise run the not my turn method
            this.notMyTurn(rows, cols);
            if (this.model.getMovesLeft() < 1) {   // if the game is over. ie. no moves left run the nomoves method
                this.noMoves();
            }
        }
    }

    /**
     * is called my refresh when there are no more moves to be made, ie the game is over.
     * calls the model and gets the status of the board and then sets the label to the appropriate message
     */
    private void noMoves() {
        Board.Status status = this.model.getStatus();   // get the status of the model and do a switch
        switch (status) {
            case ERROR:
                javafx.application.Platform.runLater(() ->
                        this.lbl.setText(status.toString()));
                this.endGame();
                break;
            case I_WON:
                javafx.application.Platform.runLater(() ->
                        this.lbl.setText("You won. Yay!"));
                this.endGame();
                break;
            case I_LOST:
                javafx.application.Platform.runLater(() ->
                        this.lbl.setText("You lost. Boo!"));
                this.endGame();
                break;
            case TIE:
                javafx.application.Platform.runLater(() ->
                        this.lbl.setText("Tie game. Meh."));
                this.endGame();
                break;
            default:
                javafx.application.Platform.runLater(() ->
                        this.lbl.setText(""));
        }
    }

    /**
     * run by refresh, run when it is not the players turn. disables all buttons
     *
     * @param rows how many rows are on the board
     * @param cols how many columns are on the board
     */
    private void notMyTurn(int rows, int cols) {
        javafx.application.Platform.runLater(() ->   // update the label
                this.lbl.setText("moves left: " + this.model.getMovesLeft() + "\t Wait for turn"));
        for (int row = 0; row <= rows - 1; ++row) {
            for (int col = 0; col <= cols - 1; col++) {
                for (Node node : grid.getChildren()) {
                    if (GridPane.getRowIndex(node) == row + 1 && GridPane.getColumnIndex(node) == col + 1) {
                        Tile b = (Tile) node;
                        b.setDisable(true);   // disable all nodes
                    }
                }
            }
        }
    }

    /**
     * run by refresh when it is the players turn. disables or enables buttons accordingly
     *
     * @param rows how many rows are on the board
     * @param cols how many columns are on the board
     */
    private void myTurn(int rows, int cols) {
        for (int row = 0; row <= rows - 1; ++row) {
            for (int col = 0; col <= cols - 1; col++) {
                for (Node node : grid.getChildren()) {
                    if (GridPane.getRowIndex(node) == row + 1 && GridPane.getColumnIndex(node) == col + 1) {
                        Tile b = (Tile) node;   // set the button status based on if it can be clicked by the player
                        if (this.model.isValidMove(row, col)) {
                            b.setDisable(false);
                        } else {
                            b.setDisable(true);
                        }
                    }
                }
            }
        }
        javafx.application.Platform.runLater(() ->   // set label to prompt user
                this.lbl.setText("moves left: " + this.model.getMovesLeft() + "\t Your turn, make a move.."));
    }

    /**
     * called by refresh to update the board, by changing the buttons immages to the associated player.
     *
     * @param rows how many rows are on the board
     * @param cols how many columns are on the board
     */
    private void updateBoard(int rows, int cols) {
        for (int row = 0; row <= rows - 1; ++row) {
            for (int col = 0; col <= cols - 1; col++) {
                for (Node node : grid.getChildren()) {
                    if (GridPane.getRowIndex(node) == row + 1 && GridPane.getColumnIndex(node) == col + 1) {
                        Tile b = (Tile) node;   // for every node
                        Board.Move s = model.getContents(row, col);   // get the player of the tile and set the image accordingly
                        switch (s) {
                            case PLAYER_ONE:
                                javafx.application.Platform.runLater(() ->
                                        b.setImmage("othelloP1.jpg"));
                                break;
                            case PLAYER_TWO:
                                javafx.application.Platform.runLater(() ->
                                        b.setImmage("othelloP2.jpg"));
                                break;
                            case NONE:
                                javafx.application.Platform.runLater(() ->
                                        b.setImmage("empty.jpg"));
                                break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Launch the JavaFX GUI.
     *
     * @param args not used, here, but named arguments are passed to the GUI.
     *             <code>--host=<i>hostname</i> --port=<i>portnum</i></code>
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * updates the GUI
     *
     * @param o   the Observable
     * @param arg the argument
     */
    @Override
    public void update(Observable o, Object arg) {
        assert o == this.model : "Update from non-model Observable";
        this.refresh();

    }

    /**
     * GUI is closing, so close the network connection. Server will
     * get the message.
     */
    @Override
    public void stop() {
        serverConn.close();
    }
}
