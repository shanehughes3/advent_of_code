import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
    
class Day4 {
    public static void main(String args[]) {
	Passwords pw = new Passwords();
	System.out.println(pw.getCountPart1());
	System.out.println(pw.getCountPart2());
    }
}

class Passwords {
    int countPart1 = 0;
    int countPart2 = 0;
    
    public Passwords() {
	readInput();
    }

    public int getCountPart1() {
	return countPart1;
    }

    public int getCountPart2() {
	return countPart2;
    }

    private void readInput() {
	try (Stream<String> input = Files.lines(Paths.get("input.txt"))) {
	    input.forEach((String line) -> {
		    checkLine(line);
		});
	} catch (IOException err) {
	    System.err.println(err);
	    System.exit(-1);
	}
    }

    private void checkLine(String line) {
	HashMap<String, Boolean> map = new HashMap<String, Boolean>();
	HashMap<String, Boolean> sortedMap = new HashMap<String, Boolean>();
	String[] elements = line.split(" ");
	Boolean isValidPart1 = true;
	Boolean isValidPart2 = true;

	for (String elem : elements) {
	    if (map.containsKey(elem)) {
		isValidPart1 = false;
	    }
	    char[] letters = elem.toCharArray();
	    Arrays.sort(letters);
	    String sorted = new String(letters);
	    if (sortedMap.containsKey(sorted)) {
		isValidPart2 = false;
	    }
	    map.put(elem, new Boolean(true));
	    sortedMap.put(sorted, new Boolean(true));
	}
	if (isValidPart1) {
	    ++countPart1;
	}
	if (isValidPart2) {
	    ++countPart2;
	}
    }
}
