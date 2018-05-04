---
title: Dynamic Programming - String Similarity
author: Kenny Cason
tags: dynamic programming, java, string similarity
---

Java example using Dynamic Programming for measuring String Similarity:
For more information regarding Dynamic Programming check <a href="http://en.wikipedia.org/wiki/Dynamic_programming" title="here" target="blank">here</a>.

DynamicStringSimularity.java

```java
package lib.algo.matching;

public class DynamicStringSimularity {

    private int scoreMatch = 1;

    private int scoreMismatch = -1;

    private int scoreGap = -1;

    public boolean print = false;

    public DynamicStringSimularity() {}

    public DynamicStringSimularity(int scoreMatch, int scoreMismatch, int scoreGap) {
        this.scoreMatch = scoreMatch;
        this.scoreMismatch = scoreMismatch;
        this.scoreGap = scoreGap;
    }

    /**
     * Dynamically compare two strings
     * @param seq1
     * @param seq2
     * @return 0.0 to 1.0,
     *         0.0 = 100% dissimilar
     *         1.0 = 100% similar
     */
    public double compare(String seq1, String seq2) {
        seq1 = seq1.toLowerCase();
        seq2 = seq2.toLowerCase();
        double score = Double.MIN_VALUE;

        // initialize
        int[][] alignmentMatrix = new int[seq2.length() + 1][seq1.length() + 1];
        // fill in initial values
        for(int i = 0; i <= seq1.length(); i++) {
            alignmentMatrix[0][i] = 0;
        }
        for(int i = 0; i <= seq2.length(); i++) {
            alignmentMatrix[i][0] = 0;
        }
        // calculate values
        for(int j = 1; j <= seq2.length(); j++) {
            for(int i = 1; i <= seq1.length(); i++) {
                // base score
                int matchScore = alignmentMatrix[j - 1][i - 1];
                int gapScore1 = alignmentMatrix[j - 1][i];
                int gapScore2 = alignmentMatrix[j][i - 1];
                // is there a match? diagonals
                if(seq1.charAt(i - 1) == seq2.charAt(j - 1)) {
                    matchScore += scoreMatch;
                } else {
                    matchScore += scoreMismatch;
                }
                // get gap score
                gapScore1 += scoreGap;
                gapScore2 += scoreGap;
                alignmentMatrix[j][i] = max(matchScore, gapScore1, gapScore2, 0);
                if(print) {
                    System.out.print(alignmentMatrix[j][i] + "\t");
                }
            }
            if(print) {
                System.out.println();
            }
        }        
        score = getMaxValue(alignmentMatrix);
        // normalize score
        score = score / max(seq1.length(), seq2.length());
        return score;
    }

    private int getMaxValue(int[][] alignmentMatrix) {
        int maxValue = Integer.MIN_VALUE;
        for(int j = 0; j < alignmentMatrix.length; j++) {
            for(int i = 0; i < alignmentMatrix[0].length; i++) {
                if(alignmentMatrix[j][i] > maxValue) {
                    maxValue = alignmentMatrix[j][i];
                }
            }
        }
        return maxValue;
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private int max(int a, int b, int c, int d) {
        return max(max(max(a, b), c), d);
    }

}

```

DynamicStringSimularityTest.java

```java
package lib.algo.matching;

import org.junit.Test;

public class DynamicStringSimularityTest {

    @Test
    public void test() {
        runTest("kenny", "kenny");
        runTest("kenny", "abcde");
        runTest("aaaaa", "bbbbb");
        runTest("aaabbb", "bbbaaa");
        runTest("aaabbb", "ababab");
        runTest("Hi my name is kenny, and this is a test", "Hi my name is benny, and this is a test");
    }

    private void runTest(String s1, String s2) {
        System.out.println("s1: " + s1 + ", s2: " + s2);
        DynamicStringSimularity dss = new DynamicStringSimularity();
        dss.print = true;
        System.out.println("score: " +  dss.compare(s1, s2));
    }

}


```
