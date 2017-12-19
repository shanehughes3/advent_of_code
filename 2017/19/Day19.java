import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

class Day19 {
    public enum Direction {
	DOWN,
	UP,
	LEFT,
	RIGHT
    }
    ArrayList<String> input = new ArrayList<String>();
    ArrayList<Character> output = new ArrayList<Character>();
    int currentLine = 0;
    int currentPos = 0;
    Direction currentDir = Direction.DOWN;
    
    public static void main(String[] args) {
	
    }

    public Day19() {
	readInput();
    }

    public void go() {
	currentPos = findStart(input.get(0));
	if (currentPos < 0) {
	    System.err.println("Can't find start");
	    System.exit(-1);
	}

	while (true) {

	}
    }

    private int findStart(String line) {
	char[] chars = line.toCharArray();
	for (int i = 0; i < chars.length; ++i) {
	    if (chars[i] == '|') {
		return i;
	    }
	}
	return -1;
    }
    
    private void readInput() {
	try {
	    Stream<String> lines = Files.lines(Paths.get("input.txt"));
	    lines.forEach((String line) -> {
		    input.add(line);
		});
	} catch (IOException err) {
	    System.err.println(err);
	    System.exit(-1);
	}

    }
}
