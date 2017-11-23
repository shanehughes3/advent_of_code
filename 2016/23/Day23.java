import java.util.stream.Stream;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.util.Map;

public class Day23 {
	public static void main(String[] args) {
		Computer computer = new Computer();
		computer.readInput();
		computer.run();
		System.out.println(computer.getRegisterA());
		System.exit(0);
	}
}

class Computer {
	// switch to 12 for part 2 and wait...
	private static final int registerAStart = 7;
	private Map<String, Integer> registers = new HashMap<String, Integer>();
	private List<Instruction> instructions;
	
	public Computer() {
	    String[] regList = {"a", "b", "c", "d"};
		for (String _reg : regList) {
			registers.put(_reg, 0);
		}
		registers.put("a", registerAStart);
		instructions = Collections.synchronizedList(new ArrayList<Instruction>());
	}

	public int getRegisterA() {
		return registers.get("a");
	}

	public void readInput() {
		try {
			Stream<String> input = Files.lines(Paths.get("input.txt"));
			input.forEach(this::addInstructionLine);
		} catch (IOException err) {
			System.out.println(err);
			System.exit(1);
		}
	}

	private void addInstructionLine(String line) {
		String[] lineElements = line.split("[ \t]+");
		Instruction _instruction = new Instruction(lineElements);
		instructions.add(_instruction);
	}

	public void run() {
		int currentLine = 0;
		while (currentLine < instructions.size()) {
			currentLine += executeInstruction(currentLine);
		}
	}

	private int executeInstruction(int lineNumber) {
		String argument1 = instructions.get(lineNumber).argument1;
		String argument2 = instructions.get(lineNumber).argument2;
		switch(instructions.get(lineNumber).command) {
		case "cpy":
			if (isInteger(argument2)) {
				return 1;
			} else if (isInteger(argument1)) {
				registers.put(argument2, Integer.parseInt(argument1));
			} else {
				registers.put(argument2, registers.get(argument1));
			}
			return 1;
		case "inc":
			if (isInteger(argument1)) {
				return 1;
			}
			registers.put(argument1, registers.get(argument1) + 1);
			return 1;
		case "dec":
			if (isInteger(argument1)) {
				return 1;
			}
			registers.put(argument1, registers.get(argument1) - 1);
			return 1;
		case "jnz":
			if ((!isInteger(argument1) &&
				 registers.get(argument1) != 0) ||
				(isInteger(argument1) &&
				 argument1 != "0")) {
				if (isInteger(argument2)) {
					return Integer.parseInt(argument2);
				} else {
					return registers.get(argument2);
				}
			} else {
				return 1;
			}
		case "tgl":
			int i;
			if (isInteger(argument1)) {
				i = lineNumber + Integer.parseInt(argument1);
			} else {
				i = lineNumber + registers.get(argument1);
			}
			if (i < 0 || i >= instructions.size()) {
				return 1;
			}
			if (instructions.get(i).argument2 == null) {
				if (instructions.get(i).command.equals("inc")) {
					instructions.get(i).setCommand("dec");
				} else {
					instructions.get(i).setCommand("inc");
				}
			} else {
				if (instructions.get(i).command.equals("jnz")) {
					instructions.get(i).setCommand("cpy");
				} else {
					instructions.get(i).setCommand("jnz");
				}
			}
			return 1;
		default:
			throw new Error("Bad command");
		}
	}

	private boolean isInteger(String input) {
		return input.matches("^-?[0-9]+$");
	}
	
}

class Instruction {
	public String command;
	public String argument1;
	public String argument2;

	public Instruction(String[] line) {
		command = line[0];
		argument1 = line[1];
		if (line.length > 2) {
			argument2 = line[2];
		}
	}

	public void setCommand(String newCommand) {
		command = newCommand;
	}
}
