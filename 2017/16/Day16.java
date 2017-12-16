import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

class Day16 {
    String input;
    String progs = "abcdefghijklmnop";
    HashMap<String, Long> history = new HashMap<String, Long>();
    
    public static void main(String[] args) {
	Day16 line = new Day16();
	line.dance();
	System.out.printf("Part 1: %s\n", line.getProgs());
	line.danceABillion();
	System.out.printf("Part 2: %s\n", line.getProgs());
    }

    public Day16() {
	input = readInput();
    }

    public String getProgs() {
	return progs;
    }

    public void danceABillion() {
	long repeat;
	for (long i = 1; i < 1000000000; ++i) {
	    dance();
	    if (i < 500000000) {
		if (history.containsKey(progs)) {
		    repeat = i - history.get(progs);
		    while (i + repeat < 1000000000) {
			i += repeat;
		    }
		} else {
		    history.put(progs, new Long(i));
		}
	    }
	}
    }

    public void dance() {
	Matcher match = Pattern
	    .compile("([sxp])([a-p0-9]+)(?:/([a-p0-9]+))?")
	    .matcher(input);
	while (match.find()) {
	    switch (match.group(1)) {
	    case "s":
		spin(match.group(2));
		break;
	    case "x":
		exchange(match.group(2), match.group(3));
		break;
	    case "p":
		partner(match.group(2), match.group(3));
		break;
	    default:
		System.out.printf("Bad op: %s\n", match.group(1));
	    }
	}
	
    }

    private void spin(String spec) {
	int num = progs.length() - Integer.parseInt(spec);
	progs = progs.substring(num).concat(progs.substring(0, num));
    }

    private void exchange(String a, String b) {
	int posA = Integer.parseInt(a);
	int posB = Integer.parseInt(b);
	char[] tmpArr = progs.toCharArray();
	char tmp = tmpArr[posA];
	tmpArr[posA] = tmpArr[posB];
	tmpArr[posB] = tmp;
	progs = new String(tmpArr);
    }

    private void partner(String a, String b) {
	char charA = a.charAt(0);
	char charB = b.charAt(0);
	char[] tmpArr = progs.toCharArray();
	for (int i = 0; i < tmpArr.length; ++i) {
	    if (tmpArr[i] == charA) {
		tmpArr[i] = charB;
	    } else if (tmpArr[i] == charB) {
		tmpArr[i] = charA;
	    }
	}
	progs = new String(tmpArr);
    }

    private String readInput() {
	try {
	    return new String(Files.readAllBytes(Paths.get("input.txt")));
	} catch (IOException err) {
	    System.err.println(err);
	    System.exit(-1);
	}
	return "";
    }
}
