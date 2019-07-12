package place.test;

import place.PlaceColor;
import place.PlaceTile;
import place.client.network.NetworkClient;
import place.network.PlaceRequest;

import java.io.IOException;

public class Client {

    public static void main( String[] args ){
        PlaceTile pt;
        PlaceRequest<PlaceTile> msg;
        PlaceRequest<String> lmsg;
        String server = "localhost";
        int port = 5000;
        String name = "Test2";
        NetworkClient nc = new NetworkClient(server, name ,port);


        pt = new PlaceTile(2,1,name,PlaceColor.BLACK);
        msg = new PlaceRequest<>(PlaceRequest.RequestType.CHANGE_TILE, pt);
        nc.writeO(msg);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        pt = new PlaceTile(3,2,name,PlaceColor.RED);
        msg = new PlaceRequest<>(PlaceRequest.RequestType.CHANGE_TILE, pt);
        nc.writeO(msg);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            System.out.println(nc.getnm());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
