package reversi.server;
import reversi.ReversiProtocol;

/**
 * the reversi game class.
 * this class takes two players and handles communication between the clients to play the game
 *
 * @author Chris Miller
 */

public class ReversiGame {

    public ReversiPlayer player1, player2;

    /**
     * the constructor for the game
     * @param player1 the first clients player object
     * @param player2 the second clients player object
     */
    public ReversiGame(ReversiPlayer player1,ReversiPlayer player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * the main run method for the revarsi game.
     */
    public void run(){
        Boolean runner = true;   // loop forever
        String move;
        String[] amove;
        while(runner == true){
            /**
             * the main loop for the game. the loop runs untill the game is over.
             * however since this class has no idea when the game is over it loops forever until the end is found
             * the way that I went about finding the end is before any player makes a move the client tests to see
             * if the game is over. if it is, it sends back a MOVE but instead of a row and columen it is the winner
             * of the game. this class then has to check for an end game every turn and then exicute if it is.
             */
            try {
                this.player1.cout.println(ReversiProtocol.MAKE_MOVE);   // prompt player one for a move
                move = this.player1.rin.readLine();   // read that move
                amove = move.split(" ");   // split it on spaces
                // if the MOVE command has one of these tags instead of a number it is the end of the game
                if (amove[1].equals("PLAYER_ONE") || amove[1].equals("PLAYER_TWO") || amove[1].equals("NONE")) {
                    this.endGame(amove);
                    runner = false;
                }
                // otherwise update both boards and continue to player two
                else {
                    this.player1.cout.println(ReversiProtocol.MOVE_MADE + " " + amove[1] + " " + amove[2]);
                    this.player2.cout.println(ReversiProtocol.MOVE_MADE + " " + amove[1] + " " + amove[2]);
                    this.player2.cout.println(ReversiProtocol.MAKE_MOVE);
                    move = this.player2.rin.readLine();
                    amove = move.split(" ");
                    if (amove[1].equals("PLAYER_ONE") || amove[1].equals("PLAYER_TWO") || amove[1].equals("NONE")) {
                        this.endGame(amove);
                        runner = false;
                    }
                    else {
                        this.player1.cout.println(ReversiProtocol.MOVE_MADE + " " + amove[1] + " " + amove[2]);
                        this.player2.cout.println(ReversiProtocol.MOVE_MADE + " " + amove[1] + " " + amove[2]);
                    }}
            }catch (java.io.IOException e){System.out.print("ERROR!");
            runner = false;
            System.exit(0);}
        }
    }

    /**
     * the method that is called when the end of the game is found.
     * sends the winner and looser or tie protocalls to the clients
     * @param amove the command from the client
     */
    public void endGame(String[] amove){
        if (amove[1].equals("PLAYER_ONE")){
            this.player1.cout.println(ReversiProtocol.GAME_WON);
            this.player2.cout.println(ReversiProtocol.GAME_LOST);
        }
        else if (amove[1].equals("PLAYER_TWO")){
            this.player1.cout.println(ReversiProtocol.GAME_LOST);
            this.player2.cout.println(ReversiProtocol.GAME_WON);
        }
        else if (amove[1].equals("NONE")){
            this.player1.cout.println(ReversiProtocol.GAME_TIED);
            this.player2.cout.println(ReversiProtocol.GAME_TIED);
        }
    }

}
