An "exceptional" loop.
======================

Demonstrating looping over an array in java **without bounds checking!**

The program creates 5 sets of random arrays and alternatly performs a linear search for zero with a *normal loop* and a *loop using exception handling to skip bounds checking*.

You can modify testIterations to run longer tests if you want to sqeeze more stability out of the results. I also think random work between tests and timely garbage collection could help stabilize the results further, so there's room for improvment but simply dropping the first few runs gave results that were stable enough for me.

Better yet you could **run the test** as is and **submit the results** so I can post them up here to get a broad range of results (otherwise we'll never get any windows results here).

Results:
========
```
Running 105 tests:*********************************************************************************************************
Results for: OpenJDK 64-Bit Server VM / Oracle Corporation (1.7.0_65) running Linux (3.17.0-rc5-next-20140918ConfiGDucK-dirty) on amd64 with 4 cores.
   Run size          Normal       Exceptional
      10,000:	       6,829	       6,709
     100,000:	      44,452	      55,567
   1,000,000:	     430,473	     586,414
  10,000,000:	   4,080,414	   5,683,992
 100,000,000:	  40,902,996	  56,635,216




