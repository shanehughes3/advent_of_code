import java.util.*;

class Day3 {
    public static void main(String[] args) {
	int input = 289326;
	Memory memory = new Memory(input);
	System.out.printf("Part 1: %d\n", memory.part1());
	System.out.printf("Part 2: %d\n", memory.part2());
    }
}

class Memory {
    private int input;
    
    public Memory(int input) {
	this.input = input;
    }

    public int part1() {
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

    public int part2() {
	int[] chain = new int[200];
	int current_pos = 0;
	int shell, side_len, side_pos, side_num;
	int next_val, index;

	// initialize to a shell with solid interior so I don't
	// have to handle all these cases
	chain[0] = 1;
	chain[++current_pos] = 1;
	chain[++current_pos] = 2;
	chain[++current_pos] = 4;
	chain[++current_pos] = 5;
	chain[++current_pos] = 10;
	chain[++current_pos] = 11;
	chain[++current_pos] = 23;
	chain[++current_pos] = 25;
	chain[++current_pos] = 26;
	shell = 3;
	side_num = 1;
	side_pos = 1;
	side_len = 5;

	do {
	    ++current_pos;

	    // handle movement along side
	    if (side_pos == side_len - 1) {
		side_pos = 1;
		++side_num;
		if (side_num > 4) {
		    ++shell;
		    side_len += 2;
		    side_num = 1;
		}
	    } else {
		++side_pos;
	    }

	    // add last element
	    next_val = chain[current_pos - 1];

	    if ((side_pos == 1 && side_num > 1) ||
		(side_pos == 2 && side_num == 1)) {
		// add diagonal across corner
		next_val += chain[current_pos - 2];
	    } else if (side_pos == 1 && side_num == 1) {
		// add back diagnonal for lead of shell
		index = current_pos - ((side_len - 3) * 4);
		if (index >= 0) {
		    next_val += chain[index];
		}
	    } else {
		// add back diagonal
		index = current_pos -
		    (side_num * (side_len - 1)) -
		    ((4 - side_num) * (side_len - 3));
		if (index >= 0) {
		    next_val += chain[index];
		}
	    }

	    if ((side_num < 4 && side_pos < side_len - 1 &&
		 !(side_num == 1 && side_pos == 1)) ||
		(side_num == 4 && side_pos < side_len)) {
		// add adjacent
		index = current_pos -
		    (side_num * (side_len - 1)) -
		    ((4 - side_num) * (side_len - 3)) + 1;
		if (index >= 0) {
		    next_val += chain[index];
		}
	    }
	    
	    if ((side_num < 4 && side_pos < side_len - 2 &&
		 !(side_num == 1 && side_pos == 1)) ||
		(side_num == 4 && side_pos < side_len - 1)) {
		// add forward diagonal
		index = current_pos -
		    (side_num * (side_len - 1)) -
		    ((4 - side_num) * (side_len - 3)) + 2;
		if (index >= 0) {
		    next_val += chain[index];
		}
	    }

	    chain[current_pos] = next_val;
	} while (chain[current_pos] < input);

	return chain[current_pos];
    }
}
