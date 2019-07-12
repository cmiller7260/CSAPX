package reversi.client;

import reversi.Reversi;
import reversi.ReversiProtocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * the Reversi Client class, this class talks to a server and receves commmands and runs a game of reversi againsed
 * another player.
 *
 * @author Chris Miller
 */

public class ReversiClient extends Reversi {

    private String hostName;
    private int portNumber;

    /**
     * the main method of the client, prints an error if there are incorrect command line arguments
     * @param args system arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        ReversiClient client = new ReversiClient(hostName, portNumber);
        client.run();
    }

    /**
     * creates a new server object
     * @param hostName the name of the host to connect to
     * @param portNumber the port to connect to
     */
    public ReversiClient(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    /**
     * the main run method, didnt know how to split this up into methods without creating another class for the client.
     * because with the try I could not get the in and out to work over methods becasue they were outside the try...
     * so I used a main run method to create and connect to the server, and then receve commands.
     */
    public void run(){
        System.out.println("$ java ReversiClient " + hostName + " " + portNumber);
        try (
                // connect to the server and create the in an out to communicate
                Socket ReversiSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(ReversiSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(ReversiSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in));
        ) {
            String userInput;
            Reversi Game =new Reversi();
            userInput = in.readLine(); // get the inital startup connect protocall along with the dimentions of the board
            String[] arrInput = userInput.split(" ");
            if (arrInput[0].equals("CONNECT")) {
                int rows = Integer.parseInt(arrInput[1]);
                int cols = Integer.parseInt(arrInput[2]);
                Game = new Reversi(rows, cols);  // create a new reversi game
                System.out.println(Game.toString()); // print the board
            }

            while ((userInput = in.readLine()) != null) { // the main loop, constantly reading for messages from the server
                arrInput = userInput.split(" ");
                try {
                    /**
                     * the main switch for the loop, gets the protocall as a header to detemin what to do or send back.
                     * the only weird part is after a MAKE_MOVE command is seen the client checks weather or not the
                     * game is over, if it is not then the client is prompted for a move, if it is over then the client
                     * sends back a move command with the winner instead of the move command. that is how I got around
                     * ending the game in the ReversiGame class.
                     */
                    if (arrInput[0].equals("MAKE_MOVE")) {
                        if (Game.gameOver() == true) {
                            out.println(ReversiProtocol.MOVE + " " + Game.getWinner());
                        }
                        else {
                            System.out.print("Your turn! Enter row column: ");
                            userInput = stdIn.readLine();
                            out.println(ReversiProtocol.MOVE + " " + userInput);
                        }
                    } else if (arrInput[0].equals("MOVE_MADE")) {
                        int rows = Integer.parseInt(arrInput[1]);
                        int cols = Integer.parseInt(arrInput[2]);
                        Game.makeMove(rows, cols);
                        System.out.println("A move has been made in row "+ rows + " column " + cols);
                        System.out.println(Game.toString());
                    }
                    else if (arrInput[0].equals("GAME_WON")){
                        System.out.println("You won! Yay!");
                    }
                    else if (arrInput[0].equals("GAME_LOST")){
                        System.out.println("You lost! Boo!");
                    }
                    else if (arrInput[0].equals("GAME_WON")){
                        System.out.println("You tied! Meh!");
                    }
                    else{
                        System.out.println("Error: exiting");
                        System.exit(1);
                    }
                }
                catch (reversi.ReversiException e){System.out.print(e);
                System.exit(0);}
            }
            System.exit(0);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
