import java.util.*;
import knot.*;

class Day10 {
    public static void main(String args[]) {
	Knot knot1 = new Knot(new int[]
	    {129,154,49,198,200,133,97,254,41,6,2,1,255,0,191,108});
	knot1.hash();
	System.out.printf("Part 1: %d\n", knot1.getValues());
	Knot knot2 =
	    new Knot("129,154,49,198,200,133,97,254,41,6,2,1,255,0,191,108");
	knot2.hash();
	System.out.printf("Part 2: %s\n", knot2.getDenseHash());
    }
}

