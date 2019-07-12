package reversi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * the class of player that constructs players from a socket and creates a BufferedReader and a PrintWriter
 * for that associated player
 *
 * @author Chris Miller
 */

public class ReversiPlayer {

    public Socket clientSocket;
    public BufferedReader rin;
    public PrintWriter cout;

    /**
     * the player constructor
     * @param clientSocket the socket that the client has connected to.
     * @throws IOException
     */
    public ReversiPlayer(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.rin = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        this.cout = new PrintWriter(clientSocket.getOutputStream(), true);
    }
}

