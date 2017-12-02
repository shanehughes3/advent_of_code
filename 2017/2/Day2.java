import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

class Day2 {
    public static void main(String[] args) {
	Spreadsheet sheet = new Spreadsheet();
	System.out.println(sheet.hash(1));
	System.out.println(sheet.hash(2));
    }
}

class Spreadsheet {
    int[][] sheet = new int[16][16];
    private int sheetInputLine = 0;

    public Spreadsheet() {
	readInput();
    }

    public int hash(int day) {
	int count = 0;
	for (int i = 0; i < sheet.length; ++i) {
	    if (day == 1) {
		count += getLineHash(sheet[i]);
	    } else {
		count += getDivisionHash(sheet[i]);
	    }
	}
	return count;
    }

    private void readInput() {
	try (Stream<String> input = Files.lines(Paths.get("input.txt"))) {
	    input.forEach((String line) -> {
		    pushToSheet(line);
		});
	} catch (IOException err) {
	    System.err.println(err);
	    System.exit(-1);
	}
    }

    private void pushToSheet(String line) {
	String pieces[] = line.split("[ \\t]+");
	for (int j = 0; j < pieces.length; ++j) {
	    sheet[sheetInputLine][j] = Integer.parseInt(pieces[j]);
	}
	Arrays.sort(sheet[sheetInputLine]);
	++sheetInputLine;
    }

    private int getLineHash(int line[]) {
	return line[line.length - 1] - line[0];
    }

    private int getDivisionHash(int line[]) {
	for (int i = line.length - 1; i > 0; --i) {
	    for (int j = i - 1; j >= 0; --j) {
		if (line[i] % line[j] == 0) {
		    return line[i] / line[j];
		}
	    }
	}
	return 0;
    }
}
