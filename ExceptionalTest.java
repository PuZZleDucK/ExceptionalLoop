import java.util.*;

public class ExceptionalTest { ///of ExceptionalLoop
  private static int incrementStart = 100; //smallest list
  private static int loopIncrement = 10; // small*inc each test
  private static int incrementCount = 7; // number of tests
  private static Random rng = new Random();
  private static long time;
  private static ArrayList<int[]> testArrays = new ArrayList<int[]>();
  private static ArrayList<Long> normalTiming = new ArrayList<Long>();
  private static ArrayList<Long> exceptionalTiming = new ArrayList<Long>();

  public static void main(String[] args) throws Exception {
    ExceptionalTest test = new ExceptionalTest();
    test.setup();
    System.out.print("Starting tests:");

    boolean foundA, foundB;
    for (int[] testRun : testArrays) {  //need to use same kind of 'run' loop as above
      System.out.print("*");
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

    test.displayResults();
  }//main

  private boolean normal(int[] input) { //linear search for 0 in list.
    for (int i = 0; i < input.length; i++) {
      if(input[i++] == 0) {
        return true;
      }
    }
    return false;
  }

  private boolean exceptional(int[] input) { //linear search for 0 in list, the bad way
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

  private void setup() {
    int runSize = incrementStart;
    for(int i = 0; i < incrementCount; i++) {
      int[] thisSet = new int[runSize];
      for(int j = 0; j < runSize; j++) {
        thisSet[j] = rng.nextInt();
      }
      testArrays.add(thisSet);
      runSize *= loopIncrement;
    }
  }//Setup

  private void displayResults() {
    System.out.println("\nTest size \t   Normal \t Exceptional: ");
    int runSize = incrementStart;
    for(int i = 0; i < incrementCount; i++)
    {
      runSize *= loopIncrement;
      System.out.println("Test " + runSize + ":    \t" + normalTiming.get(i)
                       + "             " + exceptionalTiming.get(i) );
    }
  }//print results

}//class


