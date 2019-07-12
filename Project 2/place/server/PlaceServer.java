package place.server;

import place.PlaceBoard;
import place.PlaceTile;
import place.client.Model.ClientModel;
import place.client.network.NetworkClient;
import place.network.PlaceRequest;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class PlaceServer implements Observer {

    private ServerSocket ss;
    protected ClientModel board;
    protected boolean going = true;
    protected Map<String, PlaceClientThread> connections = new HashMap<String, PlaceClientThread>() {};
    private boolean debug = true;
    private int Port;
    private int DIM;

    private void debugPrint( String msg) {
        if ( this.debug ) {
            System.out.println("debug : " + msg );
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java PlaceServer port, DIM");
            System.exit(1);
        }
        int DIM = Integer.parseInt(args[1]);
        int Port = Integer.parseInt(args[0]);
        if (DIM < 1) {
            System.out.println("DIM must be >= 1");
            System.exit(1);
        }
        new PlaceServer(DIM, Port);
//        System.out.println(server);
//        server.go();
    }

    public PlaceServer(int DIM, int Port){
        this.DIM = DIM;
        this.Port = Port;
        this.board = new ClientModel(new PlaceBoard(DIM));  // this should be the modle... aka clientmodel
        this.board.addObserver(this);
        this.connections = new HashMap<String, PlaceClientThread>();
        this.go();

    }

    private void go() {
        PlaceClientThread Con;
        try {
            this.ss = new ServerSocket(this.Port);
        while (going) {
            try {
//                ss = new ServerSocket(this.Port);
                Con = new PlaceClientThread(ss.accept(), this.board, this);
                String Username = Con.Login();
                if (Username == null){
                    System.out.println("Error in run from loging == null");
                }
                System.out.println("run : " + Username);
                this.addClient(Username, Con);
                Con.start();
//                Con.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void addClient (String name, PlaceClientThread client){
        debugPrint(name);
        // test to see if in list of connections
        if (!this.connections.containsKey(name)) {
            client.SendLog();
            client.SendBoard(this.board.getBoard());
            this.connections.put(name, client);

        } else {
            System.out.println("login error, username already used");
            client.SendError("Error : Username already exists");
        }
        debugPrint(this.connections.toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        debugPrint("in the update" + this.connections.size());
        debugPrint("connections values : " + this.connections.values());
        assert o == this.board;
//        for(int x =0; x<this.connections.size(); x++){
//            debugPrint("x : " + x);
//            PlaceClientThread temp = this.connections.g;
//            debugPrint("temp to string : " + temp.toString());
//            temp.SendTile((PlaceTile) arg);
//        }
//        for (Plaeelendt User in this.connections.)
        for (PlaceClientThread s: this.connections.values()){
            debugPrint("temp to string : " + s.toString()+ " id : " +s.getId());
            s.SendTile((PlaceTile) arg);
            debugPrint(s.toString());
            debugPrint("sending to : " + s);
            debugPrint(this.board.getBoard().toString());
        }
    }
}
