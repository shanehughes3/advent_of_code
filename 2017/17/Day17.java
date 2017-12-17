import java.util.*;

class Day17 {
    static final int input = 348;
    ArrayList<Integer> buffer = new ArrayList<Integer>();
    
    public static void main(String[] args) {
	Day17 buff1 = new Day17();
	System.out.printf("Part 1: %d\n", buff1.go(2017));
	System.out.printf("Part 2: %d\n", buff1.part2());
    }

    public Day17() {
	buffer.add(0);
    }

    public int go(int end) {
	int current = 0;
	for (int step = 1; step <= end; ++step) {
	    current = ((current + input) % buffer.size()) + 1;
	    buffer.add(current, step);
	}
	return buffer.get(current + 1);
    }

    public int part2() {
	int current = 0;
	int size = 1;
	int lastZero = 0;
	for (int i = 1; i <= 50000000; ++i) {
	    current = ((current + input) % size++) + 1;
	    if (current == 1) {
		lastZero = i;
	    }
	}
	return lastZero;
    }
    
}
