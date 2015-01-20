import java.util.*;

public class ExceptionalTest {
  private static int testIterations = 105; // number of test repetitions
  private static int incrementCount = 5; // number of test lengths, heap limited
  private static ArrayList<int[]> testArrays = new ArrayList<int[]>();
  private static ArrayList<Long> normalTiming = new ArrayList<Long>();
  private static ArrayList<Long> exceptionalTiming = new ArrayList<Long>();

  public static void main(String[] args) throws Exception {
    ExceptionalTest test = new ExceptionalTest();
    test.setup();
    long time;
    boolean foundA, foundB;
    System.out.print("Running "+testIterations+" tests:");
    for(int i = 0; i < testIterations; i++) {
      System.out.print("*");
      for (int[] testRun : testArrays) {
        time = System.nanoTime();
        foundA = test.normal(testRun);//normal loop
        time = System.nanoTime()-time;
        normalTiming.add(new Long(time));

        time = System.nanoTime();
        foundB = test.exceptional(testRun);//exceptional loop
        time = System.nanoTime()-time;
        exceptionalTiming.add(new Long(time));

        if(foundA != foundB) { //use values so VM can't optomize away
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
    Random rng = new Random();
    int runSize = 10000; //smallest list
    for(int i = 0; i < incrementCount; i++) {
      int[] thisSet = new int[runSize];
      for(int j = 0; j < runSize; j++) {
        thisSet[j] = rng.nextInt();
      }
      testArrays.add(thisSet);
      runSize *= 10; //next list length * 10;
    }
  }//Setup

  private void displayResults() {
    int warmup = 5; // tests dropped, warming up VM
    long[] normalAverage = new long[incrementCount];
    long[] exceptionalAverage = new long[incrementCount];
    for(int i = 0; i < incrementCount; i++) {
      normalAverage[i] = 0l;
      exceptionalAverage[i] = 0l;
    }
    for(int i = 0; i < (testIterations)*incrementCount; i++) {
      if(i >= (incrementCount*warmup)) {
        normalAverage[i%incrementCount] += normalTiming.get(i);
        exceptionalAverage[i%incrementCount] += exceptionalTiming.get(i);
      }
    }

    System.out.println("\nResults for: "
      +System.getProperty("java.vm.name")+" / "
      +System.getProperty("java.vendor")+" ("
      +System.getProperty("java.version")+") running "
      +System.getProperty("os.name")+" ("
      +System.getProperty("os.version")+") on "
      +System.getProperty("os.arch")+" with "
      +Runtime.getRuntime().availableProcessors()+" cores.");
    System.out.println("   Run size          Normal       Exceptional");
    for(int i = 0; i < incrementCount; i++) {
      normalAverage[i] = normalAverage[i]/(testIterations-warmup);
      exceptionalAverage[i] = exceptionalAverage[i]/(testIterations-warmup);
      System.out.println(String.format("%1$,12d", testArrays.get(i).length)+":\t"
        +String.format("%1$,12d", normalAverage[i])+"\t"
        +String.format("%1$,12d", exceptionalAverage[i]));
    }
  }//print results

}//class

