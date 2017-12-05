import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

class Day5 {
	public static void main(String args[]) {
		Jumps j = new Jumps();
		System.out.printf("Part 1: %d\n", j.calculatePart1());
		System.out.printf("Part 2: %d\n", j.calculatePart2());
	}
}

class Jumps {
	ArrayList<Integer> inputList = new ArrayList<Integer>();
	int[] input;
	
	public Jumps() {
		readInput();
	}

	public int calculatePart1() {
		input = inputList.stream().mapToInt(i -> i).toArray();
		int size = input.length;
		int current = 0;
		int steps = 0;

		while (current >= 0 && current < size) {
			++steps;
			current += input[current]++;
		}
		return steps;
	}

	public int calculatePart2() {
		input = inputList.stream().mapToInt(i -> i).toArray();
		int size = input.length;
		int current = 0;
		int steps = 0;

		while (current >= 0 && current < size) {
			++steps;
			if (input[current] >= 3) {
			    current += input[current]--;
			} else {
				current += input[current]++;
			}
		}
		return steps;
	}

	private void readInput() {
		try (Stream<String> infile = Files.lines(Paths.get("input.txt"))) {
			infile.forEach((String line) -> {
					inputList.add(Integer.parseInt(line));
				});
		} catch (IOException err) {
			System.err.println(err);
			System.exit(-1);
		}
	}
}
