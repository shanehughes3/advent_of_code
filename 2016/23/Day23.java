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
		System.out.println(computer.registers.get("a"));
		System.exit(0);
	}
}

class Computer {
	public Map<String, Integer> registers = new HashMap<String, Integer>();
	List<Instruction> instructions;
	
	public Computer() {
	    String[] regList = {"a", "b", "c", "d"};
		for (String _reg : regList) {
			registers.put(_reg, 0);
		}
		registers.put("a", 7);
		instructions = Collections.synchronizedList(new ArrayList<Instruction>());
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
		boolean shouldWait = false;
		while (currentLine < instructions.size()) {
			// if (currentLine > 17 && registers.get("a") < 0) {
			// 	shouldWait = true;
			// }
			// System.out.println(currentLine);
			// System.out.println(instructions.get(currentLine).command);
			// System.out.println(instructions.get(currentLine).argument1);
			// System.out.println(instructions.get(currentLine).argument2);
			// System.out.println("a: " + registers.get("a").toString());
			// System.out.println("b: " + registers.get("b").toString());
			// System.out.println("c: " + registers.get("c").toString());
			// System.out.println("d: " + registers.get("d").toString());
			// System.out.println(instructions.get(8).command);
			// System.out.println("-----");
			// try {
			// 	if (currentLine == 16) {
			// 		System.in.read();
			// 	}
			// } catch (IOException err) {
			// 	System.out.println("error");
			// }
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
			// System.out.println("i = " + Integer.toString(i));
			// System.out.println(instructions.size());
			if (i < 0 || i >= instructions.size()) {
				return 1;
			}
			// System.out.println(instructions.get(i).command);
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
			// System.out.println(instructions.get(i).command);
			// try {
			// 	System.in.read();
			// } catch (IOException err) {
			// 	System.out.println("error");
			// }
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
		// System.out.println("Changing command from " + command + " to " + newCommand);
		command = newCommand;
	}
}
