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
	int nextLine = 0;
	int nextPos = 0;
    Direction currentDir = Direction.DOWN;
	int steps = 1;
    
    public static void main(String[] args) {
		Day19 day = new Day19();
		day.go();
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
			switch (currentDir) {
			case DOWN:
				nextPos = currentPos;
				nextLine = currentLine + 1;
				break;
			case UP:
				nextPos = currentPos;
				nextLine = currentLine - 1;
				break;
			case LEFT:
				nextPos = currentPos - 1;
				nextLine = currentLine;
				break;
			case RIGHT:
				nextPos = currentPos + 1;
				nextLine = currentLine;
				break;
			}

			switch(input.get(nextLine).charAt(nextPos)) {
			case '|':
			case '-':
				break;
			case ' ':
				System.out.printf("Part 1: ");
				for (char c : output) {
					System.out.printf("%c", c);
				}
				System.out.printf("\n");
				System.out.printf("Part 2: %d\n", steps);
				System.exit(0);
				break;
			case '+':
				if (currentDir == Direction.UP || currentDir == Direction.DOWN) {
					if (nextPos > 0 && input.get(nextLine).charAt(nextPos - 1) == '-') {
						currentDir = Direction.LEFT;
					} else {
						currentDir = Direction.RIGHT;
					}
				} else {
					if (nextLine > 0 && input.get(nextLine - 1).charAt(nextPos) == '|') {
						currentDir = Direction.UP;
					} else {
						currentDir = Direction.DOWN;
					}
				}
				break;
			default:
				output.add(input.get(nextLine).charAt(nextPos));
				
			}
			currentLine = nextLine;
			currentPos = nextPos;
			++steps;
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
