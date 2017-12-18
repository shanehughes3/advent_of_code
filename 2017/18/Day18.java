import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.regex.*;

class Day18 {
    String[][] prog = new String[41][3];
    int currentLine = 0;
    int[] regs = new int[16];
    int lastOut = 0;
    
    public static void main(String[] args) {
	Day18 day = new Day18();
	day.run();
    }

    public Day18() {
	readInput();
	for (int i = 0; i < 16; ++i) {
	    regs[i] = 0;
	}
    }

    public void run() {
	currentLine = 0;
	while (currentLine >= 0 && currentLine < 42) {
	    switch (prog[currentLine][0]) {
	    case "snd":
		snd(currentLine++);
		break;
	    case "set":
		set(currentLine++);
		break;
	    case "add":
		add(currentLine++);
		break;
	    case "mul":
		mul(currentLine++);
		break;
	    case "mod":
		mod(currentLine++);
		break;
	    case "rcv":
		rcv(currentLine++);
		break;
	    case "jgz":
		currentLine += jgz(currentLine);
		break;
	    default:
		System.err.printf("Invalid instruction: %s\n",
				  prog[currentLine][0]);
		break;
	    }
	}
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
	    .compile("^([a-z]+) ([a-p0-9])(?: (-?\\d+|[a-z]+))?$")
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

    private void snd(int line) {
	if (prog[line][1].matches("^-?\\d+$")) {
	    lastOut = Integer.parseInt(prog[line][1]);
	} else {
	    lastOut = regs[regValue(prog[line][1])];
	}
    }

    private void set(int line) {
	int reg = regValue(prog[line][1]);
	if (prog[line][2].matches("^-?\\d+$")) {
	    regs[reg] = Integer.parseInt(prog[line][2]);
	} else {
	    regs[reg] = regs[regValue(prog[line][2])];
	}	
    }

    private void add(int line) {
	int reg = regValue(prog[line][1]);
	if (prog[line][2].matches("^-?\\d+$")) {
	    regs[reg] += Integer.parseInt(prog[line][2]);
	} else {
	    regs[reg] += regs[regValue(prog[line][2])];
	}
    }

    private void mul(int line) {
	int reg = regValue(prog[line][1]);
	if (prog[line][2].matches("^-?\\d+$")) {
	    regs[reg] *= Integer.parseInt(prog[line][2]);
	} else {
	    regs[reg] *= regs[regValue(prog[line][2])];
	}
    }

    private void mod(int line) {
	int reg = regValue(prog[line][1]);
	if (prog[line][2].matches("^-?\\d+$")) {
	    regs[reg] %= Integer.parseInt(prog[line][2]);
	} else {
	    regs[reg] %= regs[regValue(prog[line][2])];
	}
    }

    private void rcv(int line) {
	if (prog[line][1].matches("^-?\\d+$")) {
	    if (Integer.parseInt(prog[line][1]) != 0) {
		System.out.printf("rcv: %d\n", lastOut);
		System.exit(0);
	    }
	} else {
	    if (regs[regValue(prog[line][1])] != 0) {
		System.out.printf("rcv: %d\n", lastOut);
		System.exit(0);
	    }
	}
    }

    private int jgz(int line) {
	if (prog[line][1].matches("^-?\\d+$")) {
	    if (Integer.parseInt(prog[line][1]) > 0) {
		if (prog[line][2].matches("^-?\\d+$")) {
		    return Integer.parseInt(prog[line][2]);
		} else {
		    return regs[regValue(prog[line][2])];
		}
	    } else {
		return 1;
	    }
	} else {
	    if (regs[regValue(prog[line][1])] > 0) {
		if (prog[line][2].matches("^-?\\d+$")) {
		    return Integer.parseInt(prog[line][2]);
		} else {
		    return regs[regValue(prog[line][2])];
		}
	    } else {
		return 1;
	    }
	}
    }
}
