package canal;

/**
 * A piece of a canal in a canal system simulation.
 * The two most obvious implementers would be a lock and the stretch between
 * two locks, sometimes called a <i>level</i>.
 * Any contention for a segment is largely handled by the
 * {@link canal.CanalSegmentGuard}. However, state of the segment, e.g.
 * how many boats are in it, could be stored here or in the guard.
 * Currently a CanalSegment only handles one-way traffic.
 *
 * @author James Heliotis
 */
public interface CanalSegment {

    /**
     * Fetch the guard for this segment. The guard is
     * responsible for limiting the entry of boats into the segment.
     * @return this segment's guard
     */
    CanalSegmentGuard getGuard();

    /**
     * Compute the time, in simulated minutes, that a boat will take to
     * pass through. In a segment with a guard, this would be the time
     * from entering to leaving, which are two other methods in this
     * interface.
     *
     * @param boatLength length of the boat in feet
     * @return the time it will take for the boat to pass through this segment
     */
    float computeTime( int boatLength );

}
