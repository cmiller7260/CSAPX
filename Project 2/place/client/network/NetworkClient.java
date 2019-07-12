package place.client.network;

import place.PlaceBoard;
import place.PlaceColor;
import place.PlaceTile;
import place.client.Model.ClientModel;
import place.network.PlaceRequest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkClient {

    private Socket socket;
    private ObjectOutputStream oout = null;
    private ObjectInputStream iin = null;
    private ClientModel board = null;
    private String name;
    private boolean go;

    public NetworkClient( String hostname, String name, int port) {
        try {
            this.socket = new Socket(hostname, port);
            this.oout = new ObjectOutputStream(this.socket.getOutputStream());
            this.iin = new ObjectInputStream(this.socket.getInputStream());
            this.name = name;
            this.go = true;
            this.Login();
            Thread netThread = new Thread( () -> this.run() );
//            netThread.run();
            netThread.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void Login (){
        PlaceRequest<String> lmsg = new PlaceRequest<>(PlaceRequest.RequestType.LOGIN, this.name);
        System.out.println("Login : " + lmsg);
        try {
            oout.writeObject(lmsg);
            oout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PlaceRequest request = this.getnm();
            boolean notin = true;
            while(notin) {
                if (request.getType() == PlaceRequest.RequestType.LOGIN_SUCCESS) {
                    request = this.getnm();
                    if (request.getType() == PlaceRequest.RequestType.BOARD) {
                        this.board = new ClientModel((PlaceBoard) request.getData());
                        notin = false;
                    }
                } else if (request.getType() == PlaceRequest.RequestType.ERROR) {
                    System.out.println("Error in Login!!");
                }
                System.out.println("Login : " + request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PlaceRequest getnm () throws IOException, ClassNotFoundException {
        PlaceRequest<?> req = (PlaceRequest<?>) this.iin.readObject();
        System.out.println("getnm : " + req);
        return req;
    }

    public void writeO(PlaceRequest request){
        try {
            this.oout.writeObject(request);
            this.oout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized PlaceBoard getBoard(){
        return this.board.getBoard();
    }

    public synchronized ClientModel getModle(){
        return this.board;
    }

    public synchronized String getName(){
        return this.name;
    }

    public synchronized void sendMove(int row, int col, int color){
        PlaceColor pcolor = null;
        for(PlaceColor s: PlaceColor.values()){
            if (s.getNumber() == color){
                pcolor = s;
            }
        }
        if (pcolor == null){
            System.out.println("ERROR!!!");
        }
        else{
            PlaceTile tile = new PlaceTile(row, col, this.name, pcolor);
            if(this.board.getBoard().isValid(tile)){
                this.writeO(new PlaceRequest(PlaceRequest.RequestType.CHANGE_TILE, tile));
            }
        }
    }

    public void close() {
        try {
            this.socket.close();
        }
        catch( IOException ioe ) {
        }
    }

    private void run(){
        while (this.go) {
            try {
                PlaceRequest<?> req = (PlaceRequest<?>) this.iin.readObject();
                switch ( req.getType() ) {
                    case BOARD:
                        System.out.println("Run : " + req);
                        break;
                    case CHANGE_TILE:
                        System.out.println("Run : " + req);
                        break;
                    case ERROR:
                        System.out.println("Run : " + req);
                        break;
                    case LOGIN:
                        System.out.println("Run : " + req);
                        break;
                    case LOGIN_SUCCESS:
                        System.out.println("Run : " + req);
                        break;
                    case TILE_CHANGED:
                        this.board.setTile((PlaceTile) req.getData());
                        System.out.println("Run : " + req);
                        break;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
