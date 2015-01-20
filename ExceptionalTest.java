import java.util.*;

public class ExceptionalTest { ///of ExceptionalLoop
  private static int incrementStart = 10000; //smallest list
  private static int loopIncrement = 10; // small*inc each test
  private static int incrementCount = 5; // number of test lengths
  private static int testIterations = 15;
  private static int warmup = 3; // tests dropped, warming up VM
  private static Random rng = new Random();
  private static long time;
  private static ArrayList<int[]> testArrays = new ArrayList<int[]>();
  private static ArrayList<Long> normalTiming = new ArrayList<Long>();
  private static ArrayList<Long> exceptionalTiming = new ArrayList<Long>();

  public static void main(String[] args) throws Exception {
    ExceptionalTest test = new ExceptionalTest();
    test.setup();
    System.out.print("Starting tests:");

    for(int i = 0; i < testIterations; i++) {
      boolean foundA, foundB;
      System.out.print("*");
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
    long[] normalAverage = new long[incrementCount];
    long[] exceptionalAverage = new long[incrementCount];
    for(int i = 0; i < incrementCount; i++) {
      normalAverage[i] = 0l;
      exceptionalAverage[i] = 0l;
    }

    System.out.println("\nTest size \t   Normal \t Exceptional: ");
//    int runSize = incrementStart;
    for(int i = 0; i < (testIterations)*incrementCount; i++) {
      if(i >= (incrementCount*warmup)) {
        normalAverage[i%incrementCount] += normalTiming.get(i);
        exceptionalAverage[i%incrementCount] += exceptionalTiming.get(i);

//        System.out.println("Test " + testArrays.get(i%incrementCount).length + ":    \t" + normalTiming.get(i)
//                       + "             " + exceptionalTiming.get(i) );

      }

//      runSize *= loopIncrement;
    }

    System.out.println("\nDIV:"+(testIterations-warmup));
    for(int i = 0; i < incrementCount; i++) {
      normalAverage[i] = normalAverage[i]/(testIterations-warmup);
      exceptionalAverage[i] = exceptionalAverage[i]/(testIterations-warmup);
      System.out.println("Avg: "+normalAverage[i]+" vs "+exceptionalAverage[i]);
    }


  }//print results

}//class


