import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.regex.*;

class Day24 {
    ArrayList<ArrayList<Integer>> components =
	new ArrayList<ArrayList<Integer>>();
    
    public static void main(String args[]) {
	Day24 day = new Day24();
	System.out.printf("Part 1: %d\n", day.run1());
	System.out.printf("Part 2: %d\n", day.run2()[1]);
    }

    public Day24() {
	readInput();
    }

    public int run1() {
	return buildNextBridge1(0, 0, components);
    }

    public int[] run2() {
	return buildNextBridge2(0, 0, 0, components);
    }

    private int buildNextBridge1(int total, int openEnd,
				ArrayList<ArrayList<Integer>> remaining) {
	int best = 0;
	for (int i = 0; i < remaining.size(); ++i) {
	    if (remaining.get(i).get(0).equals(openEnd)) {
		ArrayList<ArrayList<Integer>> nextList =
		    new ArrayList<ArrayList<Integer>>(remaining);
		nextList.remove(i);
		int poss =
		    buildNextBridge1(total + remaining.get(i).get(1) + openEnd,
				    remaining.get(i).get(1),
				    nextList);
		best = (poss > best) ? poss : best;
	    } else if (remaining.get(i).get(1).equals(openEnd)) {
		ArrayList<ArrayList<Integer>> nextList =
		    new ArrayList<ArrayList<Integer>>(remaining);
		nextList.remove(i);
		int poss =
		    buildNextBridge1(total + remaining.get(i).get(0) + openEnd,
				    remaining.get(i).get(0),
				    nextList);
		best = (poss > best) ? poss : best;
	    }
	}
	best = (total > best) ? total : best;
	return best;
    }

    private int[] buildNextBridge2(int length, int total, int openEnd,
				ArrayList<ArrayList<Integer>> remaining) {
	int best = 0;
	int bestLength = 0;
	for (int i = 0; i < remaining.size(); ++i) {
	    if (remaining.get(i).get(0).equals(openEnd)) {
		ArrayList<ArrayList<Integer>> nextList =
		    new ArrayList<ArrayList<Integer>>(remaining);
		nextList.remove(i);
		int[] poss =
		    buildNextBridge2(length + 1,
				     total + remaining.get(i).get(1) + openEnd,
				     remaining.get(i).get(1),
				     nextList);
		if (poss[0] > bestLength) {
		    bestLength = poss[0];
		    best = poss[1];
		} else if (poss[0] == bestLength) {
		    best = (poss[1] > best) ? poss[1] : best;
		}
	    } else if (remaining.get(i).get(1).equals(openEnd)) {
		ArrayList<ArrayList<Integer>> nextList =
		    new ArrayList<ArrayList<Integer>>(remaining);
		nextList.remove(i);
		int[] poss =
		    buildNextBridge2(length + 1,
				     total + remaining.get(i).get(0) + openEnd,
				     remaining.get(i).get(0),
				     nextList);
		if (poss[0] > bestLength) {
		    bestLength = poss[0];
		    best = poss[1];
		} else if (poss[0] == bestLength) {
		    best = (poss[1] > best) ? poss[1] : best;
		}
	    }
	}
	if (length > bestLength) {
	    bestLength = length;
	    best = total;
	} else if (length == best) {
	    best = (total > best) ? total : best;
	}
	return new int[] {bestLength, best};
    }

    private void readInput() {
	try (Stream<String> input = Files.lines(Paths.get("input.txt"))) {
	    input.forEach(this::readLine);
	} catch (IOException err) {
	    System.err.println(err);
	    System.exit(-1);
	}
    }

    private void readLine(String line) {
	Matcher match = Pattern.compile("^(\\d+)/(\\d+)$").matcher(line);
	if (!match.find()) {
	    System.err.printf("Bad line: %s\n", line);
	    System.exit(-1);
	}
	
	components
	    .add(new ArrayList<Integer>
		 (Arrays.asList(Integer.parseInt(match.group(1)),
				Integer.parseInt(match.group(2)))));
    }
}
