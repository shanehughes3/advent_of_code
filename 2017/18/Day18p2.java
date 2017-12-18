import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.regex.*;

class Day18p2 {
	public enum State {
		STOPPED,
		WAITING,
		RUNNING
	}

    String[][] prog = new String[41][3];
	int currentProg = 0;
    int[] currentLine = new int[2];
    long[][] regs = new long[2][16];
	ArrayList<Long>[] to = new ArrayList[2];
	State[] state = new State[2];
	int timesSent = 0;
    
    public static void main(String[] args) {
		Day18p2 day = new Day18p2();
		day.run();
    }

    public Day18p2() {
		readInput();
		regs[0][15] = 0;
		regs[1][15] = 1;
		state[0] = State.RUNNING;
		state[1] = State.RUNNING;
		to[0] = new ArrayList<Long>();
		to[1] = new ArrayList<Long>();
    }

    public void run() {
		currentLine[0] = 0;
		while (true) {
			if (state[currentProg] != State.RUNNING) {
				state[currentProg] = State.RUNNING;
			}
			if (currentLine[currentProg] < 0 || currentLine[currentProg] > 41) {
				if (state[(currentProg + 1) % 2] == State.STOPPED) {
					System.out.printf("Out of bounds, stopped: %d\n", timesSent);
					System.exit(0);
				}
				state[currentProg] = State.STOPPED;
				currentProg = (currentProg + 1) % 2;
				continue;
			}
			switch (prog[currentLine[currentProg]][0]) {
			case "snd":
				snd(currentLine[currentProg]++);
				break;
			case "set":
				set(currentLine[currentProg]++);
				break;
			case "add":
				add(currentLine[currentProg]++);
				break;
			case "mul":
				mul(currentLine[currentProg]++);
				break;
			case "mod":
				mod(currentLine[currentProg]++);
				break;
			case "rcv":
				rcv(currentLine[currentProg]);
				break;
			case "jgz":
				currentLine[currentProg] += jgz(currentLine[currentProg]);
				break;
			default:
				System.err.printf("Invalid instruction: %s\n",
								  prog[currentLine[currentProg]][0]);
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
		prog[currentLine[0]][0] = matcher.group(1);
		prog[currentLine[0]][1] = matcher.group(2);
		prog[currentLine[0]][2] = matcher.group(3);
		++currentLine[0];
    }

    private int regValue(String input) {
		return (int)input.charAt(0) - (int)'a';
    }

    private void snd(int line) {
		if (currentProg == 1) {
			++timesSent;
		}
		if (prog[line][1].matches("^-?\\d+$")) {
			to[(currentProg + 1) % 2].add(new Long(prog[line][1]));
		} else {
			to[(currentProg + 1) % 2]
				.add(regs[currentProg][regValue(prog[line][1])]);
		}
    }

    private void set(int line) {
		int reg = regValue(prog[line][1]);
		if (prog[line][2].matches("^-?\\d+$")) {
			regs[currentProg][reg] = Integer.parseInt(prog[line][2]);
		} else {
			regs[currentProg][reg] = regs[currentProg][regValue(prog[line][2])];
		}	
    }

    private void add(int line) {
		int reg = regValue(prog[line][1]);
		if (prog[line][2].matches("^-?\\d+$")) {
			regs[currentProg][reg] += Integer.parseInt(prog[line][2]);
		} else {
			regs[currentProg][reg] += regs[currentProg][regValue(prog[line][2])];
		}
    }

    private void mul(int line) {
		int reg = regValue(prog[line][1]);
		if (prog[line][2].matches("^-?\\d+$")) {
			regs[currentProg][reg] *= Integer.parseInt(prog[line][2]);
		} else {
			regs[currentProg][reg] *= regs[currentProg][regValue(prog[line][2])];
		}
    }

    private void mod(int line) {
		int reg = regValue(prog[line][1]);
		if (prog[line][2].matches("^-?\\d+$")) {
			regs[currentProg][reg] %= Integer.parseInt(prog[line][2]);
		} else {
			regs[currentProg][reg] %= regs[currentProg][regValue(prog[line][2])];
		}
    }

    private void rcv(int line) {
		if (to[currentProg].size() < 1) {
			if (state[(currentProg + 1) % 2] == State.STOPPED ||
				(state[(currentProg + 1) % 2] == State.WAITING &&
				 to[(currentProg + 1) % 2].size() < 1)) {
				System.out.printf("Stopped: %d\n", timesSent);
				System.exit(0);
			}
			state[currentProg] = State.WAITING;
			currentProg = (currentProg + 1) % 2;
			return;
		}
		int reg = regValue(prog[line][1]);
		regs[currentProg][reg] = to[currentProg].remove(0);
		++currentLine[currentProg];
    }

    private int jgz(int line) {
		if (prog[line][1].matches("^-?\\d+$")) {
			if (Integer.parseInt(prog[line][1]) > 0) {
				if (prog[line][2].matches("^-?\\d+$")) {
					return Integer.parseInt(prog[line][2]);
				} else {
					return (int)regs[currentProg][regValue(prog[line][2])];
				}
			} else {
				return 1;
			}
		} else {
			if (regs[currentProg][regValue(prog[line][1])] > 0) {
				if (prog[line][2].matches("^-?\\d+$")) {
					return Integer.parseInt(prog[line][2]);
				} else {
					return (int)regs[currentProg][regValue(prog[line][2])];
				}
			} else {
				return 1;
			}
		}
    }
}
