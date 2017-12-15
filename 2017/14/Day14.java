import java.util.*;
import knot.Knot; // run with -cp .:../10

class Day14 {
    public static void main(String[] args) {
	Defrag dfr = new Defrag("hfdlxzhv");
	System.out.printf("Part 1: %d\n", dfr.countUsed());
	dfr.drawTable();
	System.out.printf("Part 2: %d\n", dfr.countGroups());
    }
}

class Defrag {
    String[] hashes = new String[128];
    String input;
    int[][] table = new int[128][128];

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

    public void drawTable() {
	for (int line = 0; line < 128; ++line) {
	    int linePos = 127;
	    for (int charPos = 31; charPos >= 0; --charPos) {
		int nextChunk =
		    Integer.parseInt(hashes[line]
				     .substring(charPos, charPos + 1), 16);
		for (int i = 0; i < 4; ++i) {
		    table[line][linePos--] = (nextChunk & 1) - 1;
		    nextChunk >>>= 1;
		}
	    }
	}
    }

    public int countGroups() {
	int groups = 0;
	for (int line = 0; line < 128; ++line) {
	    for (int pos = 0; pos < 128; ++pos) {
		if (table[line][pos] == 0) {
		    recordGroup(line, pos, ++groups);
		}
	    }
	}
	return groups;
    }

    private void recordGroup(int line, int pos, int group) {
	table[line][pos] = group;
	if (line > 0 && table[line - 1][pos] == 0) {
	    recordGroup(line - 1, pos, group);
	}
	if (line < 127 && table[line + 1][pos] == 0) {
	    recordGroup(line + 1, pos, group);
	}
	if (pos > 0 && table[line][pos - 1] == 0) {
	    recordGroup(line, pos - 1, group);
	}
	if (pos < 127 && table[line][pos + 1] == 0) {
	    recordGroup(line, pos + 1, group);
	}
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
