import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.regex.*;

class Day8 {
	public static void main(String[] args) {
		Instructions instr = new Instructions();
		instr.compute();
		System.out.printf("Highest val at end: %s\n", instr.getLargestRegister());
		System.out.printf("Highest val encountered: %s:\n", instr.getMaxVal());
	}
}

class Instructions {
	List<String> instr = new ArrayList<String>();
	Map<String, Integer> registers = new HashMap<String, Integer>();
	private static final Pattern linePtrn =
		Pattern.compile("^([a-z]+) (inc|dec) (-?\\d+) if ([a-z]+) ([><=!]+) (-?\\d+)");
	int largest = 0;
	int maxVal = 0;
	
	public Instructions() {
		readInput();
	}

	public void compute() {
		for (String line : instr) {
			Matcher matcher = linePtrn.matcher(line);
			matcher.find();
			int start = (registers.containsKey(matcher.group(1))) ?
				registers.get(matcher.group(1)) : 0;
			int compReg = (registers.containsKey(matcher.group(4))) ?
				registers.get(matcher.group(4)) : 0;
			int delta = Integer.parseInt(matcher.group(3));
			int compVal = Integer.parseInt(matcher.group(6));
			boolean result = false;
			switch (matcher.group(5)) {
			case "==":
				result = compReg == compVal;
				break;
			case ">=":
				result = compReg >= compVal;
				break;
			case "<=":
				result = compReg <= compVal;
				break;
			case "<":
				result = compReg < compVal;
				break;
			case ">":
				result = compReg > compVal;
				break;
			case "!=":
				result = compReg != compVal;
				break;
			}
			if (result) {
				if (matcher.group(2).equals("inc")) {
					registers.put(matcher.group(1), start + delta);
					if (start + delta > maxVal) {
						maxVal = start + delta;
					}
				} else {
					registers.put(matcher.group(1), start - delta);
					if (start - delta > maxVal) {
						maxVal = start - delta;
					}
				}
			}
		}
	}

	public int getLargestRegister() {
		registers.forEach((String key, Integer val) -> {
				if (val.compareTo(largest) > 0 ) {
					largest = val;
				}
			});
		return largest;
	}

	public int getMaxVal() {
		return maxVal;
	}

	private void readInput() {
		try (Stream<String> input = Files.lines(Paths.get("input.txt"))) {
			input.forEach((String line) -> {
					instr.add(line);
				});
		} catch (IOException err) {
			System.err.println(err);
			System.exit(-1);
		}
	}
}
