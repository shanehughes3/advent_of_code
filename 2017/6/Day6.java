import java.util.*;

class Day6 {
    public static void main(String[] args) {
	final int[] input = new int[]
	    { 11, 11, 13, 7, 0, 15, 5, 5, 4, 4, 1, 1, 7, 1, 15, 11 };
	Memory mem = new Memory(input);
	System.out.println(mem.calc());
    }
}

class Memory {
    int[] mem = new int[16];
    HashMap<Integer, Integer> history = new HashMap<Integer, Integer>();
    int iterations = 0;
    
    public Memory(int[] input) {
	this.mem = input.clone();
    }

    public int calc() {
	do {
	    distribute(findGreatest());
	    ++iterations;
	} while (findOrAdd() == false);

	return iterations;
    }

    private int findGreatest() {
	int greatest = 0;
	for (int i = 0; i < mem.length; ++i) {
	    if (mem[i] > mem[greatest]) {
		greatest = i;
	    }
	}
	return greatest;
    }

    private void distribute(int index) {
	int value = mem[index];
	mem[index++] = 0;
	while (value > 0) {
	    if (index == mem.length) {
		index = 0;
	    }
	    ++mem[index++];
	    --value;
	}
    }

    private boolean findOrAdd() {
	Integer hash = new Integer(Arrays.hashCode(mem));
	if (history.containsKey(hash)) {
	    System.out.printf("Loop: %d\n", iterations - history.get(hash));
	    return true;
	}
	history.put(hash, new Integer(iterations));
	return false;
    }
}
