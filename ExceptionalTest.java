import java.util.*;

public class ExceptionalTest { ///of ExceptionalLoop
  private static int incrementStart = 100; //smallest list
  private static int loopIncrement = 10; // small*inc each test
  private static int incrementCount = 6; // number of tests
  private static Random rng = new Random();


  public static void main(String[] args) throws Exception {
    ExceptionalTest test = new ExceptionalTest();
    int[] input = {1,2,3};
    long time = System.currentTimeMillis();
    ArrayList<int[]> testSets = new ArrayList<int[]>();
    ArrayList<int[]> testArrays = new ArrayList<int[]>();

    ArrayList<Long> normalTiming = new ArrayList<Long>();
    ArrayList<Long> exceptionalTiming = new ArrayList<Long>();

    int runSize = incrementStart;
    for(int i = 0; i < incrementCount; i++) {
      int[] thisSet = new int[runSize];
      for(int j = 0; j < runSize; j++) {
        thisSet[j] = rng.nextInt();
      }
      testArrays.add(thisSet);
      runSize *= loopIncrement;
    }                                                            //Setup tests

    System.out.println("Starting tests:");
    boolean foundA, foundB;
    for (int[] testRun : testArrays) {  //need to use same kind of 'run' loop as above
      time = System.nanoTime();
      foundA = test.normal(testRun);
      time = System.nanoTime()-time;
      normalTiming.add(new Long(time));                             //normal loop

      time = System.nanoTime();
      foundB = test.exceptional(testRun);
      time = System.nanoTime()-time;
      exceptionalTiming.add(new Long(time));                       //nightmare loop

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
                       + "             " + exceptionalTiming.get(i) );
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
  private boolean exceptional(int[] input) {
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


