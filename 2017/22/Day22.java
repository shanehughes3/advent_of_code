import java.util.*;
import java.io.*;
import java.util.stream.*;
import java.nio.file.*;

class Day22 {
    List<ArrayList<Boolean>> map = new ArrayList<ArrayList<Boolean>>();
    int changes = 0;
    int direction = 0;
    int currentLine = 0;
    int currentPos = 0;
    
    public static void main(String[] args) {
	Day22 day = new Day22();
	day.go();
	System.out.printf("Part 1: %d\n", day.getInfections());
    }

    public Day22() {
	readInput();
	currentLine = map.size() / 2;
	currentPos = map.get(0).size() / 2;
    }

    public void go() {
	for (int i = 0; i < 10000; ++i) {
	    nextTick();
	}
    }

    public int getInfections() {
	return changes;
    }

    public void nextTick() {
	if (map.get(currentLine).get(currentPos)) {
	    direction = (direction + 1) % 4;
	    map.get(currentLine).set(currentPos, false);
	    move();
	} else {
	    direction = (direction + 3) % 4;
	    map.get(currentLine).set(currentPos, true);
	    ++changes;
	    move();
	}
    }

    private void move() {
	switch (direction) {
	case 0:
	    currentLine -= 1;
	    if (currentLine < 0) {
		ArrayList<Boolean> newLine = new ArrayList<Boolean>();
		for (int i = 0; i < map.get(0).size(); ++i) {
		    newLine.add(false);
		}
		map.add(0, newLine);
		currentLine = 0;
	    }
	    break;
	case 1:
	    currentPos += 1;
	    if (currentPos >= map.get(0).size()) {
		map.forEach((ArrayList<Boolean> line) -> {
			line.add(false);
		    });
	    }
	    break;
	case 2:
	    currentLine += 1;
	    if (currentLine >= map.size()) {
		ArrayList<Boolean> newLine = new ArrayList<Boolean>();
		for (int i = 0; i < map.get(0).size(); ++i) {
		    newLine.add(false);
		}
		map.add(newLine);
	    }
	    break;
	case 3:
	    currentPos -= 1;
	    if (currentPos < 0) {
		map.forEach((ArrayList<Boolean> line) -> {
			line.add(0, false);
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
	ArrayList<Boolean> outLine = new ArrayList<Boolean>();
	for (char c : nodes) {
	    if (c == '#') {
		outLine.add(true);
	    } else if (c == '.') {
		outLine.add(false);
	    } else {
		System.err.printf("Bad char input: %c\n", c);
		System.exit(-1);
	    }
	}
	map.add(outLine);
    }
}
