import java.util.*;

class Day3 {
    public static void main(String[] args) {
	int input = 289326;
	Memcalc memcalc = new Memcalc(input);
	System.out.println(memcalc.calc());
    }
}

class Memcalc {
    private int input;
    
    public Memcalc(int input) {
	this.input = input;
    }

    public int calc() {
	int shell = 1;
	int side = 1;
	int interior = 0;
	while (interior + (2 * side + 2 * (side - 2)) < input) {
	    if (shell == 1) {
		interior = 1;
	    } else {
		interior += 2 * side + 2 * (side - 2);
	    }
	    shell++;
	    side += 2;
	}
	while ((interior += side - 1) < input);
	if (interior - side / 2 + 1 > input) {
	    return shell + (interior - side / 2 + 1) - input;
	} else {
	    return shell + input - (interior - side / 2 + 1);
	}
    }
}
