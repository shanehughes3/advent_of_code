import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

class Day11 {
    public static void main(String args[]) {
	HexGrid grid = new HexGrid();
	grid.go();
	System.out.printf("End distance: %d\n", grid.getDistance());
	System.out.printf("Max distance: %d\n", grid.getMax());
    }
}

class HexGrid {
    String input;
    int x = 0;
    int y = 0;
    int z = 0;
    int max = 0;
    
    public HexGrid() {
	try {
	    input = new String(Files.readAllBytes(Paths.get("input.txt")));
	} catch (IOException err) {
	    System.out.println(err);
	    System.exit(-1);
	}
    }

    public void go() {
	Matcher direction = Pattern.compile("([a-z]+),?").matcher(input);
	while (direction.find()) {
	    switch (direction.group(1)) {
	    case "n":
		++y;
		--z;
		break;
	    case "s":
		--y;
		++z;
		break;
	    case "ne":
		++x;
		--z;
		break;
	    case "sw":
		--x;
		++z;
		break;
	    case "nw":
		--x;
		++y;
		break;
	    case "se":
		++x;
		--y;
		break;
	    default:
		System.out.printf("Bad input: %s\n", direction.group(1));
		break;
	    }
	    if (getDistance() > max) {
		max = getDistance();
	    }
	}
    }

    public int getDistance() {
	return Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.abs(z));
    }

    public int getMax() {
	return max;
    }
}
