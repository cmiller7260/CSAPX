package canal;

import java.time.Duration;
import java.time.Instant;

/**
 * Some functionality handy for the Canal simulation
 *
 * @author James Heliotis
 */
public class Utilities {

    private static final long NSEC_PER_MSEC = 1000000L;

    /** How many real-time msec go by for each simulated minute */
    public static final long SCALED_MINUTE_MSEC = 100;

    /**
     * How quickly the water in the {@link Lock} chambers rises or falls,
     * ft/min
     */
    public static final float UP_DOWN_SPEED = 8.0F;

    /**
     * How quickly a boat travels in or out of a {@link Lock},
     * ft/min
     */
    public static final float BOAT_LOCK_SPEED = 40.0F;

    /**
     * How quickly a boat travels in the open water of a {@link Level},
     * ft/min
     */
    public static final float BOAT_SPEED = 440.0F;

    /**
     * Pause the current thread for a period of time.
     * {@link java.lang.InterruptedException}
     * is handled internally with an assertion check.
     * @param minutes the number of simulated minutes to pause
     */
    public static void sleep( long minutes ) {
        try {
            Thread.sleep( minutes * SCALED_MINUTE_MSEC );
        }
        catch( InterruptedException ie ) {
            assert false: "Thread interrupted";
        }
    }

    /**
     * Return a string representing a number of minutes as HH:MM.
     * @param minutes the number of minutes to be converted
     * @return the string containing the whole number of hours, a colon,
     *         and the 2-digit representation of the leftover minutes.
     */
    private static String hrColonMin( long minutes ) {
        long extraMinutes = minutes % 60;
        String colon = extraMinutes < 10 ? ":0" : ":";
        return ( minutes / 60 ) + colon + ( minutes % 60 );
    }

    /**
     * Saves the time that the program using this class was started.
     * Technically, the time when this class was loaded and initialized.
     */
    private static Instant start = Instant.now();

    /**
     * @return Nanoseconds since {@link Utilities#start} time
     */
    private static long getRelativeTime() {
        return Duration.between( Utilities.start, Instant.now() ).toNanos();
    }

    /**
     * Print a message that is prefixed with the current simulated time in
     * hours and minutes. It is recommended that this method only be called
     * from within a region of mutual exclusion so that the messages from
     * different threads come out in the right order.
     *
     * @param message the message the caller wants printed
     */
    public static void log( String message ) {
        long timeStamp = getRelativeTime() /
                ( Utilities.NSEC_PER_MSEC * Utilities.SCALED_MINUTE_MSEC );
        System.out.printf(
                "%7s: %s\n", Utilities.hrColonMin( timeStamp ), message
        );
    }
}
