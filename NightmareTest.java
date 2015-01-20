import java.util.*;

public class NightmareTest {
  private static int incrementStart = 100; //smallest list
  private static int loopIncrement = 10; // small*inc each test
  private static int incrementCount = 6; // number of tests
  private static Random rng = new Random();


  public static void main(String[] args) throws Exception {
    NightmareTest test = new NightmareTest();
    int[] input = {1,2,3};
    long time = System.currentTimeMillis();
    ArrayList<int[]> testSets = new ArrayList<int[]>();
    int[][] testArrays = new int[incrementCount][incrementStart^loopIncrement];

    ArrayList<Long> normalTiming = new ArrayList<Long>();
    ArrayList<Long> nightmareTiming = new ArrayList<Long>();

    int runSize = incrementStart;
    for(int i = 0; i < incrementCount; i++) {
      ArrayList<Integer> thisSet = new ArrayList<Integer>();
      for(int j = 0; j < runSize; j++) {
        testArrays[i][j] = rng.nextInt();
      }
      runSize *= loopIncrement;
    }                                                            //Setup tests

    System.out.println("Starting tests:");
    boolean foundA, foundB;
    for (int[] testRun : testArrays) {  //need to use same kind of 'run' loop as above
//      time = System.currentTimeMillis();
      time = System.nanoTime();
      foundA = test.normal(testRun);
      time = System.nanoTime()-time;
      //System.out.print(found + "\b\b\b\b\b");
      normalTiming.add(new Long(time));                             //normal loop

      time = System.nanoTime();
      foundB = test.nightmare(testRun);
      time = System.nanoTime()-time;
      //System.out.print(found + "\b\b\b\b\b");
      nightmareTiming.add(new Long(time));                       //nightmare loop

      if(foundA != foundB) {
        System.out.print("Anomilous result found.");
      }
    }

    System.out.println("Test size \t   normal \t nightmare: ");
    runSize = incrementStart;
    for(int i = 0; i < incrementCount; i++)
    {
      runSize *= loopIncrement;
      System.out.println("Test " + runSize + ":    \t" + normalTiming.get(i)
                       + "             " + nightmareTiming.get(i) );
    }                                                          //print results

  }//main


//linear search for 0 in list.
  private boolean normal(int[] input) {
    for (int i = 0; i < input.length; i++) {
      if(input[i++] == 0) {
        return true;
      }
    }
    return false;
  }
//linear search for 0 in list, the bad way
  private boolean nightmare(int[] input) {
    try {
      int i = 0;
      while (true) {
        if(input[i++] == 0) {
          return true;
        }
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      // we are done
      return false;
    }
  }



}


