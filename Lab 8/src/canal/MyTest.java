package canal;

import java.util.Arrays;
import java.util.List;

/**
 * the test program required by the instructions.
 * this test creates three locks two levels and five boats and runs them.
 *
 * @author Chris Miller
 *
 *
 * my sample run
 *
0:00: Hawley arriving at Lock 1[len=40' ,ht =30']
0:00: Geddes arriving at Lock 1[len=40' ,ht =30']
0:00: Hawley[len=35] Entering Lock 1[len=40' ,ht =30'] for 9 minutes
0:01: Clinton arriving at Lock 1[len=40' ,ht =30']
0:09: Hawley[len=35] Leaving Lock 1[len=40' ,ht =30']
0:09: Geddes[len=25] Entering Lock 1[len=40' ,ht =30'] for 9 minutes
0:09: Hawley arriving at Level 1-to-2
0:09: Hawley[len=35] Entering Level 1-to-2 for 23 minutes
0:11: Richy arriving at Lock 1[len=40' ,ht =30']
0:13: Reg arriving at Lock 1[len=40' ,ht =30']
0:18: Geddes[len=25] Leaving Lock 1[len=40' ,ht =30']
0:18: Geddes arriving at Level 1-to-2
0:18: Clinton[len=20] Entering Lock 1[len=40' ,ht =30'] for 9 minutes
0:18: Geddes[len=25] Entering Level 1-to-2 for 23 minutes
0:27: Clinton[len=20] Leaving Lock 1[len=40' ,ht =30']
0:27: Clinton arriving at Level 1-to-2
0:27: Richy[len=30] Entering Lock 1[len=40' ,ht =30'] for 9 minutes
0:27: Clinton[len=20] Entering Level 1-to-2 for 23 minutes
0:32: Hawley[len=35] Leaving Level 1-to-2
0:32: Hawley arriving at Lock 2[len=50' ,ht =20']
0:32: Hawley[len=35] Entering Lock 2[len=50' ,ht =20'] for 7 minutes
0:36: Richy[len=30] Leaving Lock 1[len=40' ,ht =30']
0:36: Richy arriving at Level 1-to-2
0:36: Reg[len=15] Entering Lock 1[len=40' ,ht =30'] for 9 minutes
0:36: Richy[len=30] Entering Level 1-to-2 for 23 minutes
0:39: Hawley[len=35] Leaving Lock 2[len=50' ,ht =20']
0:39: Hawley arriving at Level 1-to-2
0:39: Hawley[len=35] Entering Level 1-to-2 for 14 minutes
0:41: Geddes[len=25] Leaving Level 1-to-2
0:41: Geddes arriving at Lock 2[len=50' ,ht =20']
0:41: Geddes[len=25] Entering Lock 2[len=50' ,ht =20'] for 7 minutes
0:45: Reg[len=15] Leaving Lock 1[len=40' ,ht =30']
0:45: Reg arriving at Level 1-to-2
0:45: Reg[len=15] Entering Level 1-to-2 for 23 minutes
0:48: Geddes[len=25] Leaving Lock 2[len=50' ,ht =20']
0:48: Geddes arriving at Level 1-to-2
0:48: Geddes[len=25] Entering Level 1-to-2 for 14 minutes
0:50: Clinton[len=20] Leaving Level 1-to-2
0:50: Clinton arriving at Lock 2[len=50' ,ht =20']
0:50: Clinton[len=20] Entering Lock 2[len=50' ,ht =20'] for 7 minutes
0:53: Hawley[len=35] Leaving Level 1-to-2
0:53: Hawley arriving at Lock 3[len=60' ,ht =100']
0:53: Hawley[len=35] Entering Lock 3[len=60' ,ht =100'] for 27 minutes
0:57: Clinton[len=20] Leaving Lock 2[len=50' ,ht =20']
0:57: Clinton arriving at Level 1-to-2
0:57: Clinton[len=20] Entering Level 1-to-2 for 14 minutes
0:59: Richy[len=30] Leaving Level 1-to-2
0:59: Richy arriving at Lock 2[len=50' ,ht =20']
0:59: Richy[len=30] Entering Lock 2[len=50' ,ht =20'] for 7 minutes
1:02: Geddes[len=25] Leaving Level 1-to-2
1:02: Geddes arriving at Lock 3[len=60' ,ht =100']
1:06: Richy[len=30] Leaving Lock 2[len=50' ,ht =20']
1:06: Richy arriving at Level 1-to-2
1:06: Richy[len=30] Entering Level 1-to-2 for 14 minutes
1:08: Reg[len=15] Leaving Level 1-to-2
1:08: Reg arriving at Lock 2[len=50' ,ht =20']
1:08: Reg[len=15] Entering Lock 2[len=50' ,ht =20'] for 7 minutes
1:11: Clinton[len=20] Leaving Level 1-to-2
1:11: Clinton arriving at Lock 3[len=60' ,ht =100']
1:15: Reg[len=15] Leaving Lock 2[len=50' ,ht =20']
1:15: Reg arriving at Level 1-to-2
1:15: Reg[len=15] Entering Level 1-to-2 for 14 minutes
1:20: Richy[len=30] Leaving Level 1-to-2
1:20: Richy arriving at Lock 3[len=60' ,ht =100']
1:20: Hawley[len=35] Leaving Lock 3[len=60' ,ht =100']
1:20: Hawley has ended its trip.
1:20: Geddes[len=25] Entering Lock 3[len=60' ,ht =100'] for 27 minutes
1:29: Reg[len=15] Leaving Level 1-to-2
1:29: Reg arriving at Lock 3[len=60' ,ht =100']
1:47: Geddes[len=25] Leaving Lock 3[len=60' ,ht =100']
1:47: Geddes has ended its trip.
1:47: Clinton[len=20] Entering Lock 3[len=60' ,ht =100'] for 27 minutes
2:14: Clinton[len=20] Leaving Lock 3[len=60' ,ht =100']
2:14: Clinton has ended its trip.
2:14: Richy[len=30] Entering Lock 3[len=60' ,ht =100'] for 27 minutes
2:41: Richy[len=30] Leaving Lock 3[len=60' ,ht =100']
2:41: Richy has ended its trip.
2:41: Reg[len=15] Entering Lock 3[len=60' ,ht =100'] for 27 minutes
3:08: Reg[len=15] Leaving Lock 3[len=60' ,ht =100']
3:08: Reg has ended its trip.
 */

public class MyTest {

    /**
     * the man program, runs test
     * @param args not used
     */
    public static void main( String[] args ) {
        test();
    }

    /**
     * the test, makes the locks, levels and boats and starts all the threads and waits for them to finish.
     */
    private static void test() {
        CanalSegment lock1 = new Lock( 1, 40, 30 );
        CanalSegment level1_2 = new Level( "1-to-2", 10000 );
        CanalSegment lock2 = new Lock( 2, 50, 20 );
        CanalSegment level2_3 = new Level( "1-to-2", 6000 );
        CanalSegment lock3 = new Lock( 3, 60, 100 );


        List< CanalSegment > fullCanalRoute =
                Arrays.asList( lock1, level1_2, lock2, level2_3, lock3 );

        Boat b1 = new Boat( "Geddes", 25, fullCanalRoute );
        Boat b2 = new Boat( "Hawley", 35, fullCanalRoute );
        Boat b3 = new Boat( "Clinton", 20, fullCanalRoute );
        Boat b4 = new Boat( "Richy", 30, fullCanalRoute );
        Boat b5 = new Boat( "Reg", 15, fullCanalRoute );

        b1.start();
        b2.start();
        Utilities.sleep( 1 );
        b3.start();
        Utilities.sleep( 10 );
        b4.start();
        Utilities.sleep( 2 );
        b5.start();

        try {
            b1.join();
            b2.join();
            b3.join();
            b4.join();
            b5.join();
        }
        catch( InterruptedException ie ) {
            assert false: "Thread interrupted.";
        }
    }
}
