package canal;

/**
 * A level stretch of water in a canal that has no obstructions or limits
 * on how many boats may be in it
 *
 * @author James Heliotis
 */
public class Level implements CanalSegment {

    /**
     * The trivial guard for a canal segment that needs no regulation
     */
    static class LevelGuard implements CanalSegmentGuard {

        /**
         * Return the same ID every time because it doesn't matter.
         * @return {@link CanalSegmentGuard#NO_BOAT}+1
         */
        @Override
        public int requestEntryToSegment() {
            return CanalSegmentGuard.NO_BOAT + 1; // doesn't matter here
        }

        /**
         * Just print the message, since boats get in immediately.
         * @param boatID the ID of the calling boat
         * @param goingInMsg the message to print
         */
        @Override
        public void waitForTurn( int boatID, String goingInMsg ) {
            Utilities.log( goingInMsg );
        }

        /**
         * Just print the message, since boats go out immediately.
         * @param goingOutMsg the message to print
         */
        @Override
        public void leavingSegment( String goingOutMsg ) {
            Utilities.log( goingOutMsg );
        }
    }

    /**
     * The name of this stretch of canal
     */
    private String name;

    /**
     * The length of this stretch of canal, in feet
     */
    private int length;

    /**
     * Initialize a level section of canal
     * @param name the name of this stretch of canal
     * @param length the length of this stretch of canal, in feet
     */
    public Level( String name, int length ) {
        this.name = name;
        this.length = length;
    }

    /**
     * Provide a printable description of this segment.
     * @return &quot;Level&quot; followed by the name
     */
    @Override
    public String toString() {
        return "Level " + name;
    }

    /**
     * Get a guard that basically does nothing.
     * @return the do-nothing guard
     */
    @Override
    public CanalSegmentGuard getGuard() {
        return new Level.LevelGuard();
    }

    /**
     * The time to go through a level is the time that the stern enters
     * the level until the bow arrives at the end of the level.
     * @param boatLength length of the boat in feet
     * @return (level-length - boat-length) / boatspeed
     */
    @Override
    public float computeTime( int boatLength ) {
        int totalLength = this.length - boatLength;
        return totalLength / Utilities.BOAT_SPEED;
    }

}
