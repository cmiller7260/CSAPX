package place.server;

import place.PlaceBoard;
import place.PlaceTile;
import place.client.Model.ClientModel;
import place.network.PlaceRequest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PlaceClientThread extends Thread {

    protected Socket socket = null;
    protected PlaceServer server = null;
    protected boolean running = true;
    protected ObjectOutputStream oout = null;
    protected ObjectInputStream iin = null;
    protected ClientModel board = null;

    public PlaceClientThread(Socket socket, ClientModel board, PlaceServer server) {
        super("PlaceClientThread");
        this.socket = socket;
        this.server = server;
        this.board = board;
    }

    public synchronized String Login() {
        String name = "did not change";
        try {
            this.oout = new ObjectOutputStream(this.socket.getOutputStream());
            this.iin = new ObjectInputStream(this.socket.getInputStream());
            while (iin.equals(null)) {  // possibly doesnt work
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                PlaceRequest<?> req = (PlaceRequest<?>) this.iin.readObject();
                if (req.getType() == PlaceRequest.RequestType.LOGIN) {
                    name = (String) req.getData();
                    System.out.println("got a login from : " + name);
                    return name;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void SendLog() {
        String string = "You got in...";
        PlaceRequest<String> msg = new PlaceRequest<>(PlaceRequest.RequestType.LOGIN_SUCCESS, string);
        try {
            System.out.println("PreSend Log " + this.getId());
            this.oout.writeObject(msg);
            System.out.println("sending message to " +this +"   : "+ msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void SendError(String string) {
        string = "Error : _____";
        PlaceRequest<String> msg = new PlaceRequest<>(PlaceRequest.RequestType.ERROR, string);
        try {
            System.out.println("PreSend Error " + this.getId());
            this.oout.writeObject(msg);
            System.out.println("sending message to " +this +"   : "+ msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void SendBoard(PlaceBoard board) {
        PlaceRequest<PlaceBoard> boardReq = new PlaceRequest<>(PlaceRequest.RequestType.BOARD, board);
        try {
            System.out.println("PreSend Board " + this.getId());
            this.oout.writeObject(boardReq);
            System.out.println("sending message to " +this +"   : "+ boardReq);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void SendTile(PlaceTile tile){
        PlaceRequest<PlaceTile> msg = new PlaceRequest<>(PlaceRequest.RequestType.TILE_CHANGED, tile);
        try {
            System.out.println("PreSend Tile " + this.getId());
            this.oout.writeObject(msg);
            System.out.println("sending message to " +this +"   : "+ msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void run() {
//        String Username = this.Login();
//        if (Username == null){
//            System.out.println("Error in run from loging == null");
//        }
//        System.out.println("run : " + Username);
//        this.server.addClient(Username, this);
        try {
            while (running) {
//                while (iin.equals(null)) {   // does nothing doesnt work!!!!!!!!!!
//                    System.out.println("null stream");
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                try {
                    PlaceRequest<?> req = (PlaceRequest<?>) this.iin.readObject();

                    switch ( req.getType() ) {
                        case BOARD:
                            System.out.println(req);
                            break;
                        case CHANGE_TILE:
                            this.server.board.setTile( (PlaceTile) req.getData());
                            System.out.println(req.getData());
                            break;
                        case ERROR:
                            System.out.println(req);
                            break;
                        case LOGIN:
                            String name = (String) req.getData();
                            System.out.println("LOOK SHOULDN'T GET A LOGIN HERE!!!!! --- got a login from : " + name);
                            break;
                        case LOGIN_SUCCESS:
                            System.out.println(req);
                            break;
                        case TILE_CHANGED:
                            System.out.println(req);
                            break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            this.oout.close();
            this.iin.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
