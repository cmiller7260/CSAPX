package canal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Queue;
import java.util.*;

/**
 * A {@link CanalSegmentGuard} for a {@link Lock}.
 * This guard only allows one boat into its lock at a time.
 * Note that locks are unidirectional, as is the entire canal
 * system at this time.
 *
 * @author Chris Miller
 */
public class LockMaster implements CanalSegmentGuard {
    private Lock canalLock;
    private Queue<Integer> numbers;
    private Queue<Integer> bque;

    /**
     * Create a LockMaster. Admission ID system is initialized.
     * @param canalLock the lock to which this LockMaster is assigned
     */
    public LockMaster( Lock canalLock ) {
        this.canalLock = canalLock;
        // I over complicated the unique number assignment but it still works, just extra randomness
        // create an array of numbers between 1 and 99 and then shuffle them and convert the arraylist to a queue
        ArrayList<Integer> num = new ArrayList<Integer>();
        for (int i=1; i<99; i++) {
            num.add(new Integer(i));
        }
        Collections.shuffle(num);
        this.numbers = new LinkedList<Integer>(num);
        this.bque = new LinkedList<Integer>();
    }

    /**
     * A {@link Boat} calls this to announce its desire to enter the lock.
     * The LockMaster picks a unique ID and puts it in its internal
     * queue of IDs of boats that are waiting. The ID is returned so that
     * it can be referenced by the Boat when calling
     * {@link LockMaster#waitForTurn(int, String)}. No access permission
     * is granted at this point.
     * <br>
     * Implementation Note<br>
     * This design was chosen over enqueueing the boats directly because
     * it can get confusing when threads, which cause contention for
     * resources, are the very things stored in the shared resource.
     *
     * @return some unique value greater than {@link CanalSegmentGuard#NO_BOAT}
     */
    @Override
    public synchronized int requestEntryToSegment() {
        int id = this.numbers.poll();   // get the next unique "random" number
        this.bque.add(id);   // add the boat id to the queue
        return id;   // return the boat id to the boat.
    }

    /**
     * The boat calls this method to indicate its willingness to be blocked
     * until the LockMaster's lock is available. The method returns when
     * the lock is empty and the boatID has been in the LockMaster's queue
     * more than any other (FIFO behavior). For an implementation possibility,
     * see {@link Queue#peek()}.
     *
     * @param boatID the ID of the calling boat given to it by an earlier
     *               call to {@link CanalSegmentGuard#requestEntryToSegment()}
     * @param goingInMsg the message to print (via {@link Utilities#log(String)}
     *                   once the boat has been allowed in to this
     *                   LockMaster's canal
     */
    @Override
    public synchronized void waitForTurn( int boatID, String goingInMsg ) {
        // while its not my turn wait...
        while ((this.canalLock.isAvailable() != true) || (this.bque.peek() != boatID))
        {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        if(this.bque.peek() == boatID){   // don't need, over checking...
            this.canalLock.enter();  // if this runs then it is my turn
            Utilities.log( goingInMsg );
//        }
    }

    /**
     * The boat announces that its time in this LockMaster's lock is over and
     * it has left.
     *
     * @rit.pre It is this boat that was most recently allowed to go in via
     *          {@link #waitForTurn(int, String)}.
     * @rit.post Either the LockMaster's lock is empty or another boat has
     *           just entered.
     * @param goingOutMsg the
     *                   message to print (via {@link Utilities#log(String)}
     *                   once the boat has been allowed in to this guard's
     */
    @Override
    public synchronized void leavingSegment( String goingOutMsg ) {
        this.canalLock.leave();   // let the lock know that I left
        this.bque.poll();   // pop the last boat from the queue
        this.notifyAll();   // wake all other boats up to check if its their turn to enter the lock
        Utilities.log( goingOutMsg );
    }

}
