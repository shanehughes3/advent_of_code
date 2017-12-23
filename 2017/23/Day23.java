import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.regex.*;

class Day23 {
    String[][] prog = new String[32][3];
    int currentLine = 0;
    long[] regs = new long[8];
    long lastOut = 0;
    int mulCalled = 0;
    
    public static void main(String[] args) {
	Day23 day = new Day23();
	day.run();
	System.out.printf("Part 1: %d\n", day.getMulCalled());
    }

    public Day23() {
	readInput();
    }

    public void run() {
	currentLine = 0;
	while (currentLine >= 0 && currentLine < 32) {
	    switch (prog[currentLine][0]) {
	    case "set":
		set(currentLine++);
		break;
	    case "sub":
		sub(currentLine++);
		break;
	    case "mul":
		mul(currentLine++);
		break;
	    case "jnz":
		currentLine += jnz(currentLine);
		break;
	    default:
		System.err.printf("Invalid instruction: %s\n",
				  prog[currentLine][0]);
		break;
	    }
	}
    }

    public int getMulCalled() {
	return mulCalled;
    }

    private void readInput() {
	try (Stream<String> lines = Files.lines(Paths.get("input.txt"))) {
	    lines.forEach(this::readLine);
	} catch (IOException err) {
	    System.err.println(err);
	    System.exit(-1);
	}
    }

    private void readLine(String line) {
	Matcher matcher =
	    Pattern
	    .compile("^([a-z]+) ([a-h0-9])(?: (-?\\d+|[a-h]))?$")
	    .matcher(line);
	if (!matcher.find()) {
	    System.err.printf("Illegal line: %s\n", line);
	    System.exit(-2);
	}
	prog[currentLine][0] = matcher.group(1);
	prog[currentLine][1] = matcher.group(2);
	prog[currentLine][2] = matcher.group(3);
	++currentLine;
    }

    private int regValue(String input) {
	return (int)input.charAt(0) - (int)'a';
    }

    private void set(int line) {
	int reg = regValue(prog[line][1]);
	if (prog[line][2].matches("^-?\\d+$")) {
	    regs[reg] = Integer.parseInt(prog[line][2]);
	} else {
	    regs[reg] = regs[regValue(prog[line][2])];
	}	
    }

    private void sub(int line) {
	int reg = regValue(prog[line][1]);
	if (prog[line][2].matches("^-?\\d+$")) {
	    regs[reg] -= Integer.parseInt(prog[line][2]);
	} else {
	    regs[reg] -= regs[regValue(prog[line][2])];
	}
    }

    private void mul(int line) {
	++mulCalled;
	int reg = regValue(prog[line][1]);
	if (prog[line][2].matches("^-?\\d+$")) {
	    regs[reg] *= Integer.parseInt(prog[line][2]);
	} else {
	    regs[reg] *= regs[regValue(prog[line][2])];
	}
    }

    private int jnz(int line) {
	if (prog[line][1].matches("^-?\\d+$")) {
	    if (Integer.parseInt(prog[line][1]) != 0) {
		if (prog[line][2].matches("^-?\\d+$")) {
		    return Integer.parseInt(prog[line][2]);
		} else {
		    return (int)regs[regValue(prog[line][2])];
		}
	    } else {
		return 1;
	    }
	} else {
	    if (regs[regValue(prog[line][1])] != 0) {
		if (prog[line][2].matches("^-?\\d+$")) {
		    return Integer.parseInt(prog[line][2]);
		} else {
		    return (int)regs[regValue(prog[line][2])];
		}
	    } else {
		return 1;
	    }
	}
    }
}
