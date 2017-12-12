import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.regex.*;

class Day12 {
    public static void main(String[] args) {
	Village village = new Village();
	System.out.printf("Part 1: %d\n", village.countBase0());
	System.out.printf("Part 2: %d\n", village.countGroups());
    }
}

class Village {
    HashMap<Integer, Integer[]> input = new HashMap<Integer, Integer[]>();
    HashSet<Integer> base0Connections = new HashSet<Integer>();
    HashSet<Integer> counted = new HashSet<Integer>();
    private static final Pattern lineParser =
	Pattern.compile("\\d+");
    
    public Village() {
	readInput();
    }

    private void readInput() {
	try (Stream<String> input = Files.lines(Paths.get("input.txt"))) {
	    input.forEach(this::readLine);
	} catch (IOException err) {
	    System.err.println("Cannot read input.txt");
	    System.exit(-1);
	}
    }

    private void readLine(String line) {
	Matcher match = lineParser.matcher(line);
	match.find();
	Integer base = new Integer(match.group());
	ArrayList<Integer> connections = new ArrayList<Integer>();
	while (match.find()) {
	    connections.add(new Integer(match.group()));
	}
	input.put(base, connections.toArray(new Integer[0]));
    }

    public int countBase0() {
	return countConnections(0);
    }

    private int countConnections(int start) {
	int count = 1;
	base0Connections.add(start);
	for (Integer conn : input.get(start)) {
	    if (!base0Connections.contains(conn)) {
		count += countConnections(conn);
	    }
	}
	return count;
    }

    public int countGroups() {
	int groups = 0;
	for (int i = 0; i < 2000; ++i) {
	    if (!counted.contains(i)) {
		++groups;
		countGroup(i);
	    }
	}
	return groups;
    }

    private void countGroup(int i) {
	counted.add(i);
	for (Integer conn : input.get(i)) {
	    if (!counted.contains(conn)) {
		countGroup(conn);
	    }
	}
    }
}
