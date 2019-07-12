package reversi.server;

import reversi.Reversi;
import reversi.ReversiProtocol;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * the server class for the Reversi game. allows two clients to connect and play a game of reversi
 *
 * @author Chris Miller
 */

public class ReversiServer extends Reversi {

    public String[] arg;

    /**
     * main method, checks the system arguments and creates a new server with those arguments
     *
     * @param args the system arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Usage: java ReversiServer #_rows #_cols port");
            System.exit(1);
        }
        // create a new server and run it
        ReversiServer server = new ReversiServer(args);
        server.run();

    }

    /**
     * creates a server object
     * @param arg system arguments
     */
    public ReversiServer (String[] arg){
        this.arg = arg;
    }

    /**
     * the main run method of the server starts the server,
     * listens for both players to connect.
     * creats a player object with the reader and wrighter to communicate with the client.
     * sends both players the connect message along with the bord dimensions.
     * then create a new game instance and run the game.
     */
    public void run(){
        int rows = Integer.parseInt(arg[0]);
        int cols = Integer.parseInt(arg[1]);
        int portNumber = Integer.parseInt(arg[2]);
        System.out.println("$ javac ReversiServer " + portNumber);
        try (
                // create the server socket
                ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("waiting for player 1...");
            try (
                    // accept player one client socket
                    Socket clientSocket1 = serverSocket.accept();
            ) {
                // create a new player object withe the socket
                ReversiPlayer player1 = new ReversiPlayer(clientSocket1);
                System.out.println("Player one connected! " + clientSocket1);
                player1.cout.println(ReversiProtocol.CONNECT + " " + rows + " " + cols);
                System.out.println("waiting for player 2...");
                try (
                        // accept player two client socket
                        Socket clientSocket2 = serverSocket.accept();
                ) {
                    // create a new player object withe the socket
                    ReversiPlayer player2 = new ReversiPlayer(clientSocket2);
                    System.out.println("Player two connected! " + clientSocket2);
                    player2.cout.println(ReversiProtocol.CONNECT + " " + rows + " " + cols);
                    System.out.println("Starting game...");
                    // create a new game instance and run it
                    ReversiGame game = new ReversiGame(player1,player2);
                    game.run();

                    clientSocket1.close();
                    clientSocket2.close();
                    serverSocket.close();
                    System.exit(0);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
