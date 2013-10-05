---
title: Dynamic Programming Local Alignment
author: Kenny Cason
tags: dynamic programming, java, local alignment, algorithm
---

Java example using Dynamic Programming for locally aligning string sequences with each other:
For more information regarding Dynamic Programming check <a href="http://www.biorecipes.com/DynProgBasic/code.html" title="here" target="_blank">here</a> and <a href="http://en.wikipedia.org/wiki/Dynamic_programming" title="here" target="_blank">here</a>

DynamicLocalAlignment.java

```{.java .numberLines startFrom="1"}
package lib.algo.matching;

import java.util.LinkedList;


public class DynamicLocalAlignment {
	
	/**
	 * determine the optimal local alignment of seq2 to seq1
	 * @param seq1
	 * @param seq2
	 * @param numDeleted
	 * @param scoreMatch
	 * @param scoreMismatch
	 * @param scoreGap
	 * @return
	 */
	public int[][] getAlignmentMatrix(Character[] seq1, Character[] seq2,
			int scoreMatch, int scoreMismatch, int scoreGap) {
		// initialize
		int[][] alignmentMatrix = new int[seq2.length + 1][seq1.length + 1];
		// fill in initial values
		for(int i = 0; i <= seq1.length; i++) {
			alignmentMatrix[0][i] = 0;
		}
		for(int i = 0; i <= seq2.length; i++) {
			alignmentMatrix[i][0] = 0;
		}
		// calculate values
		for(int j = 1; j <= seq2.length; j++) {
			for(int i = 1; i <= seq1.length; i++) {
				// base score
				int matchScore = alignmentMatrix[j-1][i-1];
				int gapScore1 = alignmentMatrix[j-1][i];
				int gapScore2 = alignmentMatrix[j][i-1];
				// is there a match? diagonals
				if(seq1[i-1].equals(seq2[j-1])) {
					matchScore += scoreMatch; 
					//alignmentMatrix[j][i] = matchScore;
				} else {
					matchScore += scoreMismatch;
				}
				// get gap score
				gapScore1 += scoreGap;
				gapScore2 += scoreGap;
				alignmentMatrix[j][i] = max(matchScore, gapScore1, gapScore2, 0);
				
			}
		}		
		return alignmentMatrix;
	}
	
	public Character[][] buildOneSequenceFromAlignmentMatrix(Character[] seq1, Character[] seq2, int[][] alignmentMatrix) {
		LinkedList<Character[][]> sequences = new LinkedList<Character[][]>();
		int maxJ = 0;
		int maxI = 0;

		int currentMax = Integer.MIN_VALUE;
		for (int j = alignmentMatrix.length - 1; j > 0; j--) {
			for (int i = alignmentMatrix[0].length - 1; i > 0; i--) {
				if (alignmentMatrix[j][i] > currentMax) {
					currentMax = alignmentMatrix[j][i];
					maxJ = j;
					maxI = i;
				}
			}
		}
		return buildSequencesFromAlignmentMatrixHelper(sequences, seq1, seq2, alignmentMatrix, maxJ, maxI);
	}
	
	
	private Character[][] buildSequencesFromAlignmentMatrixHelper(LinkedList<Character[][]> sequences,
			Character[] seq1, Character[] seq2, int[][] alignmentMatrix, int j, int i) {

		LinkedList<Character> localseq1 = new LinkedList<Character>();
		LinkedList<Character> localseq2 = new LinkedList<Character>();

		int k;
		for(k = alignmentMatrix[0].length - 2; k >= i; k--) {
			localseq1.addFirst(seq1[k]);
			localseq2.addFirst(new Character('-'));
		}
		
		while(j > 0 && i > 0) {
			 int max = max(alignmentMatrix[j][i - 1], alignmentMatrix[j - 1][i], alignmentMatrix[j - 1][i - 1]);
			 if(max == alignmentMatrix[j - 1][i - 1]) { // match or mismatch
				 localseq1.addFirst(seq1[i - 1]);
				 localseq2.addFirst(seq2[j - 1]);
				 j--;
				 i--;
			 } else {
				 
				 if(max == alignmentMatrix[j - 1][i]) {
					 localseq1.addFirst('-'); 	// blank
					 localseq2.addFirst(seq2[j - 1]);

					 j--;
				 } else if(max == alignmentMatrix[j][i - 1]) {
					 localseq1.addFirst(seq1[i - 1]);
					 localseq2.addFirst('-'); 	// blank
					 i--;
				 }
			 }
		}
		while(j > 0) {
			 localseq1.addFirst('-');	// blank
			 localseq2.addFirst(seq2[j - 1]);
			 j--;
		}
		while(i > 0) {
			 localseq1.addFirst(seq1[i - 1]);
			 localseq2.addFirst('-');	// blank
			i--;
		}
		Character[][] localAlignments = new Character[2][];
		localAlignments[0] = new Character[localseq1.size()];
		localAlignments[1] = new Character[localseq2.size()];
		i = 0;
		for(Character n  : localseq1) {
			localAlignments[0][i] = n; 
			i++;
		}
		i = 0;
		for(Character n  : localseq2) {
			localAlignments[1][i] = n; 
			i++;
		}

		return localAlignments;
	}
	
	public LinkedList<Character[][]> buildManySequencesFromAlignmentMatrix(Character[] seq1, Character[] seq2, int[][] alignmentMatrix, int maxSequences) {
		LinkedList<Character[][]> sequences = new LinkedList<Character[][]>();
		int maxJ = 0;
		int maxI = 0;

		for(int k = 0; k < maxSequences; k++) {
			int currentMax = Integer.MIN_VALUE;
			for(int j = alignmentMatrix.length - 1; j > 0; j--) {
				for(int i = alignmentMatrix[0].length - 1; i > 0; i--) {
					if(alignmentMatrix[j][i] > currentMax) {
						currentMax = alignmentMatrix[j][i];
						maxJ = j; maxI = i;
					}
				}
			}
			sequences.add(buildSequencesFromAlignmentMatrixHelper(sequences, seq1, seq2, alignmentMatrix, maxJ, maxI));
			alignmentMatrix[maxJ][maxI] = 0;
		}
		return sequences;
	}
	
	/**
	 * 
	 * @param seq1
	 * @param seq2
	 * @param alignmentMatrix
	 */
	public void printAlignmentMatrix(Character[] seq1, Character[] seq2, int[][] alignmentMatrix) {
		System.out.print("  |\t\t");
		for(int i = 0; i < seq1.length; i++) {
			System.out.print(seq1[i]+"\t");
		}
		System.out.println();
		for(int i = 0; i <= seq1.length; i++) {
			System.out.print("---------");
		}
		System.out.println();
		
		for(int j = 0; j < seq2.length + 1; j++) {
			if(j > 0) {
				System.out.print(seq2[j-1]+" |\t");
			} else {
				System.out.print("  |\t");
			}
			for(int i = 0; i < alignmentMatrix[0].length; i++) {
				System.out.print(alignmentMatrix[j][i] +"\t");
			}
			System.out.println();
		}
	}

	public int min(int a, int b) {
		return (a < b) ? a : b;
	}
	
	public int min(int a, int b, int c) {
		return min(min(a, b), c);
	}
	
	public int max(int a, int b) {
		return (a > b) ? a : b;
	}
	
	public int max(int a, int b, int c) {
		return max(max(a, b), c);
	}
	
	public int max(int a, int b, int c, int d) {
		return max(max(max(a, b), c), d);
	}

}

```

DynamicLocalAlignmentTest.java

```{.java .numberLines startFrom="1"}
package lib.algo.matching;

import java.util.List;

import junit.framework.TestCase;

public class DynamicLocalAlignmentTest extends TestCase {

	public void test() { 
		Character[] seq1 = new Character[]{'C', 'A', 'C', 'G', 'T', 'A', 'T'};
		Character[] seq2 = new Character[]{'C', 'G', 'C', 'A'};

		DynamicLocalAlignment dla = new DynamicLocalAlignment();
		int[][] alignmentMatrix = dla.getAlignmentMatrix(seq1, seq2, 1, 0, -1);
		System.out.println("AlignmentMatrix: ");
		dla.printAlignmentMatrix(seq1, seq2, alignmentMatrix);
		System.out.println("Matches: ");
		List<Character[][]> nodeList = dla.buildManySequencesFromAlignmentMatrix(seq1, seq2, alignmentMatrix, 5);
		int i = 1;
		for(Character[][] nl : nodeList) {
			System.out.println("Match #" + i);
			i++;
			for(Character n : nl[0]) {
				System.out.print(n);
			}
			System.out.println();
			for(Character n : nl[1]) {
				System.out.print(n);
			}
			System.out.println();
		}
	}
	
}

```
