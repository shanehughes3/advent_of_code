import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.regex.*;

class Day7 {
    public static void main(String args[]) {
	Part1Tree part1 = new Part1Tree();
	part1.find();
	Part2Tree part2 = new Part2Tree();
	part2.find();
    }
}

class Part1Tree {
    HashMap<String, Integer> table = new HashMap<String, Integer>();
    private static final Pattern pattern = Pattern.compile("[a-z]{3,}");    
    public Part1Tree() {
	readInput();
    }

    public void find() {
	table.forEach((String key, Integer num) -> {
		if (num == 1) {
		    System.out.printf("Base program: %s\n", key);
		}
	    });
    }

    private void readInput() {
	try (Stream<String> input = Files.lines(Paths.get("input.txt"))) {
	    input.forEach(this::readLine);
	} catch (IOException err) {
	    System.err.println(err);
	    System.exit(-1);
	}
    }

    private void readLine(String line) {
	Matcher matcher = pattern.matcher(line);
	while(matcher.find()) {
	    table.compute(matcher.group(), (String key, Integer num) -> 
			(num == null) ? new Integer(1) : num + 1
			);
	}
    }
}

class Part2Tree {
    ArrayList<String> input = new ArrayList<String>();
    HashMap<String, Integer> initialWeights = new HashMap<String, Integer>();
    HashMap<String, Integer> treeWeights = new HashMap<String, Integer>();
    private static final Pattern progPtrn = Pattern.compile("[a-z]{3,}");
    private static final Pattern weightPtrn =
	Pattern.compile("^[a-z]{3,} \\((\\d+)\\)");
    
    public Part2Tree() {
	readInput();
	parseInitialWeights();
    }

    public void find() {
	populateTrees();
	findUnbalanced();
    }

    private void populateTrees() {
	boolean shouldContinue = true;
	while (shouldContinue) {
	    shouldContinue = false;
	    for (String line : input) {
		Integer totalWeight = new Integer(0);
		Matcher matcher = progPtrn.matcher(line);
		matcher.find();
		String base = matcher.group();
		totalWeight += initialWeights.get(base);
		if (!matcher.find()) {
		    treeWeights.put(base, initialWeights.get(base));
		    return;
		}
		do {
		    String next = matcher.group();
		    if (!treeWeights.containsKey(next)) {
			shouldContinue = true;
			return;
		    }
		    totalWeight += treeWeights.get(next);
		} while(matcher.find());
		treeWeights.put(base, totalWeight);
	    }
	}
    }

    private void findUnbalanced() {
	input.forEach((String line) -> {
		Matcher matcher = progPtrn.matcher(line);
		matcher.find();
		String base = matcher.group();
		ArrayList<String> progsList = new ArrayList<String>();
		while (matcher.find()) {
		    progsList.add(matcher.group());
		}
		String[] progs = progsList.toArray(new String[0]);
		Arrays.sort(progs, (String a, String b) -> {
			return treeWeights.get(a) - treeWeights.get(b);
		    });
		if (treeWeights.get(progs[0]) !=
		    treeWeights.get(progs[progs.length - 1])) {
		    printUnbalanced(progs, base);
		} else {
		    for (int i = 0; i < progs.length; ++i) {
			if (treeWeights.get(progs[0]) !=
			    treeWeights.get(progs[i])) {
				printUnbalanced(progs, base);
			    }
		    }
		}
	    });
    }

    private void printUnbalanced(String[] progs, String base) {
	System.out.printf("Base: %s: %d\n", base, initialWeights.get(base));
	for (String prog : progs) {
	    System.out.printf("%d: %d\n", initialWeights.get(prog), treeWeights.get(prog));
	}
    }

    private void readInput() {
	try (Stream<String> input = Files.lines(Paths.get("input.txt"))) {
	    input.forEach((String line) -> {
		    this.input.add(line);
		});
	} catch (IOException err) {
	    System.err.println(err);
	    System.exit(-1);
	}
    }

    private void parseInitialWeights() {
	input.forEach((String line) -> {
		Matcher matcher = weightPtrn.matcher(line);
		matcher.find();
		initialWeights.put(matcher.group(),
				   new Integer(matcher.group(1)));
	    });
    }
}
