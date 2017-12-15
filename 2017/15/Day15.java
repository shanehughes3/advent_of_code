import java.util.*;

class Day15 {
    public static void main(String[] args) {
	Generators gen1 = new Generators();
	System.out.printf("Part 1: %d\n", gen1.countTicks(40000000, false));
	Generators gen2 = new Generators();
	System.out.printf("Part 2: %d\n", gen2.countTicks(5000000, true));
    }
}

class Generators {
    long genA;
    long genB;
    long startA = 116;
    long startB = 299;
    long factorA = 16807;
    long factorB = 48271;
    long div = 2147483647;

    public Generators() {
	genA = startA;
	genB = startB;
    }

    public int countTicks(int ticks, boolean isPicky) {
	long mask = 0xffff;
	int count = 0;
	for (int i = 0; i < ticks; ++i) {
	    getNextTick(isPicky);
	    if ((genA & mask) == (genB & mask)) {
		++count;
	    }
	}
	return count;
    }

    private void getNextTick(boolean isPicky) {
	if (!isPicky) {
	    genA = (genA * factorA) % div;
	    genB = (genB * factorB) % div;
	} else {
	    do {
		genA = (genA * factorA) % div;
	    } while (genA % 4 != 0);
	    do {
		genB = (genB * factorB) % div;
	    } while (genB % 8 != 0);
	}
    }
}
