import java.util.LinkedList;
import java.util.List;

/**
 * Design a solution to the 2D rectangular trunk-packing problem that fits into the backtracking framework described in class.
 *
 * @author Chris Miller
 */

public class Trunk implements Configuration {

    protected int length = 0;
    protected int width = 0;
    protected List< Suitcase > toadd = null;
    protected char[][] map = null;
    protected boolean instfail = false;

    /**
     * Initial Trunk constructor with nothing in it, but a bunch of suitcases we hope to add.
     * @param x - dimension across, i.e. x, of trunk
     * @param y - dimension down, i.e., y, of trunk
     * @param list - a bunch of suitcases that we'll try to put in here
     */
    public Trunk (int x, int y, List<Suitcase> list){
        this.length = x;
        this.width = y;
        this.toadd = new LinkedList<>(list);
        this.map = new char[x][y];

        /**
         * the base test on the constructed trunk, it will check for instant fail cases,
         for example if the total number of spaces occupied by every suitcase is larger then the number
         of spaces in the trunk. the other check is if any suitcases length or width is longer then the trunk dimentions.
         */
        int total = 0;
        for(Suitcase s: this.toadd){
            total += (s.getLength() * s.getWidth());
            if (((s.getWidth() > this.length) && (s.getWidth() > this.width)) || ((s.getLength() > this.length) && (s.getLength() > this.width))){
                this.instfail = true;
            }
        }
        if (total > (this.length*this.width)){
            this.instfail = true;
        }
    }

    /**
     * Copy constructor. A copy of the contents of the argument Trunk is made
     * @param previous - the configuration off of which this one is based
     */
    public Trunk( Trunk previous ) {
        this(previous.length, previous.width, new LinkedList<>( previous.toadd )
        );
    }

    /**
     * get the length
     * @return length
     */
    private int getLength(){
        return this.length;
    }

    /**
     * get the width
     * @return width
     */
    private int getWidth(){
        return this.width;
    }

    /**
     * Return a list of Trunks with the next suitcase from the toAdd list added in all possible locations.
     * The only criterion is that the suitcase fit in the empty trunk.
     * @return all the Trunks
     */
    @Override
    public Iterable<Configuration> getSuccessors() {

            LinkedList< Trunk > lists = new LinkedList<>();
            List< Suitcase > list = new LinkedList<>(this.toadd);
            Suitcase next = list.get(0);
            list.remove(0);

            if (this.instfail == true){ // there is an instant fail condition so you already know it fails
                // so make a trunk and pass the list so that the isvalid is run and prints fail with little time.
                Trunk trunk = new Trunk(this.length, this.width, list);
                trunk.setFail(this.instfail);
                lists.add(trunk);
                // this is a dummy trunk to allow proper print process, and not crash the program..
            }
            else { // run through every possible sopt on the map and see if you can place the trunk.
                for (int x = 0; x < this.length; x++) {
                    for (int y = 0; y < this.width; y++) {
                        Coordinate spot = new Coordinate(x, y);
                        if (CanPlace(next, spot, this.map)) {
                            char[][] tempmap = copymap(this);
                            tempmap = place(tempmap, next, spot);
                            Trunk ntrunk = new Trunk(this.length, this.width, list);
                            ntrunk.setFail(this.instfail);
                            ntrunk.setMap(tempmap);
                            lists.add(ntrunk);
                        }
                        if (!next.isSquare()) { // if it is not a square then rotate it and check again.
                            Suitcase turned = next.turned();
                            if (CanPlace(turned, spot, this.map)) {
                                char[][] tempmap2 = copymap(this);
                                tempmap2 = place(tempmap2, turned, spot);
                                Trunk ntrunk2 = new Trunk(this.length, this.width, list);
                                ntrunk2.setFail(this.instfail);
                                ntrunk2.setMap(tempmap2);
                                lists.add(ntrunk2);

                            }
                        }
                    }
                }
            }
        Iterable it = lists;
        return it;
    }

    /**
     * set the instant fail for the next trunk
     * @param state the state to set it to.
     */
    private void setFail(boolean state){
        this.instfail = state;
    }

    /**
     * the can place method, checks if a suitcase at a position can be added to the trunk
     * @param next the suitcase to be checkd
     * @param spot the coordinate
     * @param map the map
     * @return if it can be placed or not
     */
    private boolean CanPlace(Suitcase next,Coordinate spot, char[][] map){
        int slen = next.getLength();
        int swid = next.getWidth();
        int x = spot.getX();
        int y = spot.getY();
        for(int xx = 0; xx < slen; xx++){
            for(int yy = 0; yy < swid; yy++) {
                if (((x+xx) > (this.length-1)) || ((y+yy) > (this.width-1))){
                    return false;
                }
                if (map[x + xx][y + yy] != '\u0000'){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * the place method, places a suitcase in the trunk
     * @param tempmap the map of the suitcase
     * @param next the suitcase being added
     * @param spot the coordinate that the suticase id being added
     * @return the map after the suitcase is placed
     */
    private char[][] place(char[][] tempmap, Suitcase next, Coordinate spot){
        int slen = next.getLength();
        int swid = next.getWidth();
        int x = spot.getX();
        int y = spot.getY();
        char tag = next.getName();
        for(int xx = 0; xx < slen; xx++){
            for(int yy = 0; yy < swid; yy++) {
                tempmap[x+xx][y+yy] = tag;
                }
            }
        return tempmap;
    }

    /**
     * sets the map of a trunk to the specified map, used in the getsuccessors when deep copying the trunk.
     * @param map the map that you want to update the map to.
     */
    private void setMap(char[][] map){
        int xl = this.length;
        int yl = this.width;
        for(int x = 0; x < xl; x++){
            for(int y = 0; y < yl; y++){
                this.map[x][y] = map[x][y];
            }
        }
    }

    /**
     * copies the map from a trunk and returns it
     * @param trunk the trunk that you want to copy the map
     * @return the map
     */
    private char[][] copymap(Trunk trunk){
        int xl = this.length;
        int yl = this.width;
        char[][] nmap = new char[xl][yl];
        for(int x = 0; x < xl; x++){
            for(int y = 0; y < yl; y++){
                nmap[x][y] = this.map[x][y];
            }
        }
        return (nmap);
    }

    /**
     * gets the toadd list
     * @return toadd
     */
    public List<Suitcase> getToadd() {
        return this.toadd;
    }

    /**
     * check if the trunk is an instant fail case, did validity checks before addition
     * @return
     */
    @Override
    public boolean isValid() {
        if (this.instfail == true){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Have we successfully added all the suitcases?
     * @return true iff there are no more suitcases to add.
     */
    @Override
    public boolean isGoal() {
        if (this.toadd.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Show the contents of the trunk and the remaining suitcases. horribly inefficient but IIABDFI
     */
    @Override
    public void display() {
//        System.out.println("PRINTING..........");
        for(int y = 0; y < this.width; y++) {
        for(int x = 0; x < this.length; x++){
            System.out.print(this.map[x][y]);
            }
            System.out.print('\n');
            }
    }
}
