import java.util.*;
import java.io.*;
import java.util.stream.*;
import java.nio.file.*;

class Day22pt2 {
    List<ArrayList<Integer>> map = new ArrayList<ArrayList<Integer>>();
    int changes = 0;
    int direction = 0;
    int currentLine = 0;
    int currentPos = 0;
    
    public static void main(String[] args) {
	Day22pt2 day = new Day22pt2();
	day.go();
	System.out.printf("Part 2: %d\n", day.getInfections());
    }

    public Day22pt2() {
	readInput();
	currentLine = map.size() / 2;
	currentPos = map.get(0).size() / 2;
    }

    public void go() {
	for (int i = 0; i < 10000000; ++i) {
	    nextTick();
	}
    }

    public int getInfections() {
	return changes;
    }

    public void nextTick() {
	switch(map.get(currentLine).get(currentPos)) {
	case 0: 
	    direction = (direction + 3) % 4;
	    map.get(currentLine).set(currentPos, 1);
	    move();
	    break;
	case 1:
	    map.get(currentLine).set(currentPos, 2);
	    ++changes;
	    move();
	    break;
	case 2:
	    direction = (direction + 1) % 4;
	    map.get(currentLine).set(currentPos, 3);
	    move();
	    break;
	case 3:
	    direction = (direction + 2) % 4;
	    map.get(currentLine).set(currentPos, 0);
	    move();
	    break;
	default:
	    System.err.printf("Node got to %d\n", map.get(currentLine)
			      .get(currentPos));
	    
	}
    }

    private void move() {
	switch (direction) {
	case 0:
	    currentLine -= 1;
	    if (currentLine < 0) {
		ArrayList<Integer> newLine = new ArrayList<Integer>();
		for (int i = 0; i < map.get(0).size(); ++i) {
		    newLine.add(0);
		}
		map.add(0, newLine);
		currentLine = 0;
	    }
	    break;
	case 1:
	    currentPos += 1;
	    if (currentPos >= map.get(0).size()) {
		map.forEach((ArrayList<Integer> line) -> {
			line.add(0);
		    });
	    }
	    break;
	case 2:
	    currentLine += 1;
	    if (currentLine >= map.size()) {
		ArrayList<Integer> newLine = new ArrayList<Integer>();
		for (int i = 0; i < map.get(0).size(); ++i) {
		    newLine.add(0);
		}
		map.add(newLine);
	    }
	    break;
	case 3:
	    currentPos -= 1;
	    if (currentPos < 0) {
		map.forEach((ArrayList<Integer> line) -> {
			line.add(0, 0);
		    });
		currentPos = 0;
	    }
	    break;
	default:
	    System.err.printf("Somehow, direction got to %d\n", direction);
	    System.exit(-1);
	}
    }

    private void readInput() {
	try (Stream<String> input = Files.lines(Paths.get("input.txt"))) {
	    input.forEach(this::readLine);
	} catch (IOException err) {
	    System.out.println(err);
	    System.exit(-1);
	}
    }

    private void readLine(String line) {
	char[] nodes = line.toCharArray();
	ArrayList<Integer> outLine = new ArrayList<Integer>();
	for (char c : nodes) {
	    if (c == '#') {
		outLine.add(2);
	    } else if (c == '.') {
		outLine.add(0);
	    } else {
		System.err.printf("Bad char input: %c\n", c);
		System.exit(-1);
	    }
	}
	map.add(outLine);
    }
}
