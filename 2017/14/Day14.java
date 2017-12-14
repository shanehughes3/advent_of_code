import java.util.*;
import knot.Knot; // run with -cp .:../10

class Day14 {
    public static void main(String[] args) {
	Defrag dfr = new Defrag("hfdlxzhv");
	System.out.printf("Part 1: %d\n", dfr.countUsed());
    }
}

class Defrag {
    String[] hashes = new String[128];
    String input;

    public Defrag(String input) {
	this.input = input;
	getHashesFromInput();
    }

    public int countUsed() {
	int count = 0;
	for (int i = 0; i < 128; ++i) {
	    for (int sec = 0; sec < 4; ++sec) {
		long line =
		    Long.parseLong(hashes[i]
				   .substring(sec * 8,
					      (sec + 1) * 8
					      ),
				   16);
		while (line > 0) {
		    if ((line & 1) == 1) {
			++count;
		    }
		    line >>>= 1;
		}
	    }
	}
	return count;
    }

    private void getHashesFromInput() {
	for (int i = 0; i < 128; ++i) {
	    hashes[i] = generateLineHash(i);
	}
    }

    private String generateLineHash(int line) {
	String lineInput = input + "-" + Integer.toString(line);
	Knot lineKnot = new Knot(lineInput);
	lineKnot.hash();
	return lineKnot.getDenseHash();
    }
}
