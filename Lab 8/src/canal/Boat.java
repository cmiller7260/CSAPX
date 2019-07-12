package canal;
import java.lang.*;
/**
 * A simulation of a vessel that travels along a canal.
 * Boats are <i>active</i> in that they are the ones that request
 * entry in to the canal's segments and execute sleep methods to
 * simulate the passage of time as the boat travels through a segment.
 *
 * @see canal.CanalSegment
 * @see canal.Utilities
 *
 * @author Chris Miller
 */
public class Boat extends Thread {

    private String name;
    private int length;
    private Iterable< CanalSegment > route;

    /**
     * Initiate the travel of a boat down all or part of a canal.
     * @param name the boat's name
     * @param length the boat's length in feet
     * @param route the sequence of {@link CanalSegment}s through
     *              which it must pass
     */
    public Boat( String name, int length, Iterable< CanalSegment > route ) {
        this.name = name;
        this.length = Math.abs(length);   // no neg len
        this.route = route;
    }

    /**
     * Return identifying information about this boat.
     * @return a String in the format
     *         <code><i>name</i>[len=<i>length</i>']</code>.
     */
    @Override
    public String toString() {
        return  this.name + "[len=" + this.length + "]";
    }

    /**
     * Using the route passed in to this boat upon construction,
     * simulate the passage of the boat through the route's segments.
     * The boat has to get a {@link CanalSegmentGuard}
     * and then request entry to the segment, wait for
     * the guard to permit entry, and inform the guard when this boat
     * leaves the segment.
     * <br>
     * The messages displayed on standard output are handled differently.
     * In order to coordinate better in a multithreaded
     * environment, this method creates the message strings, but the guard
     * will output them at the right time.
     * <br>
     * The boat must also ask the canal segment to compute
     * the time it will take for this boat to pass through, display a
     * message stating that time, and pause execution using
     * {@link Utilities#sleep(long)}.
     * <br>
     * When the boat has left its last segment, it should print, via
     * {@link Utilities#sleep(long)},
     * &quot;<i>name</i> has ended its trip.&quot;
     * <br>
     * <em>Important</em> - For the simulation to work this method has to
     * run in a separate Thread, using {@link Thread#start()}.
     */
    public void run() {
        for (CanalSegment s : route)   // iterate over the route
        {
            int id = s.getGuard().requestEntryToSegment();
            Utilities.log(this.name + " arriving at " + s.toString());
            Float t = new Float (Math.round(s.computeTime(this.length)));   // get the time to pass through the canal
            // segment before entering so that you can add that time to the entering msg.
            long time = t.longValue();
            s.getGuard().waitForTurn(id,this.toString() + " entering " + s.toString() + " for " + time + " minutes");
            // make the boat wait its turn.
            Utilities.sleep(time);
            // after it has entered it sleeps the thread.
            s.getGuard().leavingSegment(this.toString() + " leaving " + s.toString());
            // boat leaves and goes to the next canal segment.
        }
        Utilities.log( this.name + " has ended its trip." );
    }
}
