import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.regex.*;

class Day13 {
    public static void main(String[] args) {
	Firewall fwl = new Firewall();
	System.out.printf("Part 1: %d\n", fwl.getCost(0));
	System.out.printf("Part 2: %d\n", fwl.getUncaught());
    }
}

class Firewall {
    int[] layers = new int[99];
    private static final Pattern linePtrn = Pattern.compile("(\\d+): (\\d+)");

    public Firewall() {
	readInput();
    }

    public int getUncaught() {
	int lastCost = Integer.MAX_VALUE;
	int start = 0;
	while (lastCost > 0) {
	    ++start;
	    if (start % (layers[0] + (layers[0] - 2)) == 0) {
		continue;
	    }
	    lastCost = getCost(start);
	}
	return start;
    }

    public int getCost(int start) {
	int cost = 0;
	int time = start;
	for (int currentLayer = 0; currentLayer < 99; ++currentLayer, ++time) {
	    if (layers[currentLayer] == 0) {
		continue;
	    }
	    if (time % (layers[currentLayer] +
			(layers[currentLayer] - 2)) == 0) {
		cost += currentLayer * layers[currentLayer];
	    }
	}
	return cost;
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
	Matcher matcher = linePtrn.matcher(line);
	matcher.find();
	layers[Integer.parseInt(matcher.group(1))] =
	    Integer.parseInt(matcher.group(2));
    }
}

