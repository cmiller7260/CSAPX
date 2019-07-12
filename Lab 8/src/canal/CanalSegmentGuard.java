package canal;

/**
 * The abstraction for the object that controls access to canal segments
 * with limited capacity. In this lab it is used for canal locks, but it
 * could conceivably be used to limit traffic in other segments, or
 * temporarily deal with an emergency where the way is blocked.
 * <em>All</em> segments have guards, but they do nothing where they
 * are really not needed.
 * <br>
 * Although other design approaches are possible, the expectation is that
 * the actual state of the canal segment (availabilty, number of boats,
 * whatever) is stored in the {@link CanalSegment} rather than here, but
 * that that state is normally only accessed via the guard so that
 * multi-thread contention issues are kept under control.
 *
 * @author James Heliotis
 */
public interface CanalSegmentGuard {

    /**
     * Despite its name, the main purpose of this constant is to provide
     * a starting value for the IDs that a guard gives out. It is expected
     * that the very first ID provided in
     * {@link CanalSegmentGuard#requestEntryToSegment} is 1 plus this value.
     * Do not use this value as an ID in case some implementer uses it as
     * a sentinel or illegal value.
     */
    public static final int NO_BOAT = 0;

    /**
     * A boat tells this guard that it wants to enter the guard's canal
     * segment. This method returns immediately but that return is not
     * interpreted as granting access. This method is comparable to getting
     * a number at a delicatessen counter or motor vehicle bureau office.
     * <br>
     * For canal segments without any occupation restrictions, the convention
     * is to return {@link CanalSegmentGuard#NO_BOAT}+1.
     *
     * @return an ID &quot;ticket&quot; that will be used to wait for
     *         permission to enter the segment.
     * @see CanalSegmentGuard#waitForTurn(int, String)
     */
    public int requestEntryToSegment();

    /**
     * Suspend the boat thread here until this guard has determined, using
     * the boatID, that it is that boat's turn to enter the segment. Once
     * entered, the provided message is printed.
     *
     * @param boatID the ID of the calling boat given to it by an earlier
     *               call to {@link CanalSegmentGuard#requestEntryToSegment()}
     * @param goingInMsg the message to print (via {@link Utilities#log(String)}
     *                   once the boat has been allowed in to this guard's
     *                   segment
     */
    public void waitForTurn( int boatID, String goingInMsg );

    /**
     * The boat calls this method when it has &quot;slept&quot; for the
     * proper amount of time in this guard's canal segment.
     *
     * @param goingOutMsg the
     *                   message to print (via {@link Utilities#log(String)}
     *                   once the boat has been allowed in to this guard's
     *                   segment
     */
    public void leavingSegment( String goingOutMsg );
}
