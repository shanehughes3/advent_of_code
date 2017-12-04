import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
    
class Day4 {
    public static void main(String args[]) {
	Passwords pw = new Passwords();
    }
}

class Passwords {
    int count = 0;
    
    public Passwords() {
	
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
	String[] elements = line.split(" ");
	Boolean isValid = true;

	elements.forEach((String elem) -> {

	    });
    }
}
