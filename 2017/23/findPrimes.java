import java.util.*;

class FindPrimes {
    public static void main(String[] args) {
	int count = 0;
	int current = 106700;

	while (current <= 123700) {
	    for (int i = 2; i < Math.sqrt(current); ++i) {
		if (current % i == 0) {
		    ++count;
		    break;
		}
	    }
	    current += 17;
	}
	System.out.println(count);
    }
}
