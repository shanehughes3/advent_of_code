import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.util.regex.*;

class Day9 {
    public static void main(String[] args) {
	CharStream strm = new CharStream();
	System.out.printf("Garbage: %d\n", strm.clean());
	System.out.printf("Value: %d\n", strm.parse());
    }
}

class CharStream {
    String input;
    
    public CharStream() {
	try {
	    input = new String(Files.readAllBytes(Paths.get("input.txt")));
	} catch (IOException err) {
	    System.err.println(err);
	    System.exit(-1);
	}
    }

    public int clean() {
	removeCancelledChars();
	return countAndRemoveGarbage();
    }

    private void removeCancelledChars() {
	input = input.replaceAll("\\!.", "");
    }

    private int countAndRemoveGarbage() {
	int total = 0;
	Matcher garbage = Pattern.compile("<([^>]*)>").matcher(input);
	while (garbage.find()) {
	    total += garbage.group(1).length();
	}
	removeGarbage();
	return total;
    }

    private void removeGarbage() {
	input = input.replaceAll("<[^>]*>", "");
    }

    public int parse() {
	int total = 0;
	int depth = 0;
	for (int i = 0; i < input.length(); ++i) {
	    if (input.charAt(i) == '{') {
		++depth;
	    } else if (input.charAt(i) == '}') {
		total += depth;
		--depth;
	    }
	}
	return total;
    }
}
