import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.regex.*;

class Day20 {
    ArrayList<String> input = new ArrayList<String>();
    HashMap<Integer, Point> points = new HashMap<Integer, Point>();
    
    
    public static void main(String[] args) {
	
    }

    public Day20() {
	readInput();
    }

    private void readInput() {
	try (Stream<String> lines = Files.lines(Paths.get("input.txt"))) {
	    lines.forEach(this::readLine);
	} catch (IOException err) {
	    System.err.println(err);
	    System.exit(-1);
	}
    }

    private void readLine(String line) {
	
    }
}

class Point {
    int[] pos = new int[3];
    int[] vec = new int[3];
    int[] accel = new int[3];

    public Point(int[] pos, int[] vec, int[] accel) {
	this.pos = pos;
	this.vec = vec;
	this.accel = accel;
    }
}
