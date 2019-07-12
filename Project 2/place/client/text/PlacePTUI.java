package place.client.text;

import javafx.stage.Stage;
import place.PlaceBoard;
import place.PlaceColor;
import place.PlaceTile;
import place.client.Model.ClientModel;
import place.client.network.NetworkClient;

import java.io.PrintWriter;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class PlacePTUI extends ConsoleApplication implements Observer {

    private ClientModel model;
    private NetworkClient networkclient;
    private Scanner userIn;
    private PrintWriter userOut;


    @Override
    public void init() {
            List< String > args = super.getArguments();
            String host = args.get( 0 );
            int port = Integer.parseInt( args.get( 1 ) );
            String name = args.get( 2 );
            this.networkclient = new NetworkClient( host, name, port);
    }

    /**
     * This method continues running until the game is over.
     * It is not like {@link javafx.application.Application#start(Stage)}.
     * That method returns as soon as the setup is done.
     * This method waits for a notification from {@link #endGame()},
     * called indirectly from a model update from {@link NetworkClient}.
     *
     * @param userIn what to read to see what user types
     * @param userOut where to send messages so user can see them
     */
    @Override
    public synchronized void go( Scanner userIn, PrintWriter userOut ) {
        this.userIn = userIn;
        this.userOut = userOut;
        this.model = this.networkclient.getModle();
        this.model.addObserver( this );
                while(true) {
                    this.userOut.print("type move as row column color : ");
                    this.userOut.flush();
                    int row = this.userIn.nextInt();
                    int col = this.userIn.nextInt();
                    int color = this.userIn.nextInt();
                    this.networkclient.sendMove(row,col,color);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
    }

    /**
     * GUI is closing, so close the network connection. Server will
     * get the message.
     */
    @Override
    public void stop() {
        this.userIn.close();
        this.userOut.close();
        this.networkclient.close();
    }

    private synchronized void endGame() {
        this.notify();
    }


    /**
     * Launch the JavaFX GUI.
     *
     * @param args not used, here, but named arguments are passed to the GUI.
     *             <code>--host=<i>hostname</i> --port=<i>portnum</i></code>
     */
    public static void main( String[] args ) {
        ConsoleApplication.launch( PlacePTUI.class, args );
    }

    @Override
    public void update(Observable o, Object arg) {

        assert o == this.model: "Update from non-model Observable";
        System.out.println( "update : " + this.networkclient.getBoard());
    }
}

