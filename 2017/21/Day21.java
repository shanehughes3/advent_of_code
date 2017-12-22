import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.util.stream.*;
import java.util.regex.*;

class Day21 {
    static final Pattern linePtrn =
	Pattern.compile("^([.#]{2,3})/([.#]{2,3})(?:/([.#]{3}))? => ([.#]{3,4})/([.#]{3,4})/([.#]{3,4})(?:/([.#]{4}))?$");
    HashMap<List<String>, List<String>> rules =
	new HashMap<List<String>, List<String>>();
    List<String> image = new ArrayList<String>();
    
    public static void main(String[] args) {
	Day21 dayPt1 = new Day21();
	dayPt1.go(5);
	System.out.printf("Part 1: %d\n", dayPt1.countOn());
	Day21 dayPt2 = new Day21();
	dayPt2.go(18);
	System.out.printf("Part 2: %d\n", dayPt2.countOn());
	
    }

    public Day21() {
	readInput();
	image.add(".#.");
	image.add("..#");
	image.add("###");
    }

    public void go(int num) {
	for (int i = 0; i < num; ++i) {
	    if (image.size() % 2 == 0) {
		goSize2();
	    } else {
		goSize3();
	    }
	}
    }

    public int countOn() {
	int total = 0;
	for (String s : image) {
	    char[] line = s.toCharArray();
	    for (int i = 0; i < line.length; ++i) {
		if (line[i] == '#') {
		    total += 1;
		}
	    }
	}
	return total;
    }

    private void goSize2() {
	List<String> outImage = new ArrayList<String>();
	for (int i = 0; i < image.size(); i += 2) {
	    String str1 = "";
	    String str2 = "";
	    String str3 = "";
	    for (int j = 0; j < image.size(); j += 2) {
		List<String> thisSec = new ArrayList<String>();
		thisSec.add(image.get(i).substring(j, j + 2));
		thisSec.add(image.get(i + 1).substring(j, j + 2));
		List<String> outSec = rules.get(thisSec);
		if (outSec == null) {
		    System.out.println("Error finding rule for image:");
		    for (String s : thisSec) {
			System.out.println(s);
		    }
		    System.exit(-1);
		}
		str1 = str1.concat(outSec.get(0));
		str2 = str2.concat(outSec.get(1));
		str3 = str3.concat(outSec.get(2));
	    }
	    outImage.add(str1);
	    outImage.add(str2);
	    outImage.add(str3);
	}
	image = outImage;
    }

    private void goSize3() {
	List<String> outImage = new ArrayList<String>();
	for (int i = 0; i < image.size(); i += 3) {
	    String str1 = "";
	    String str2 = "";
	    String str3 = "";
	    String str4 = "";
	    for (int j = 0; j < image.size(); j += 3) {
		List<String> thisSec = new ArrayList<String>();
		thisSec.add(image.get(i).substring(j, j + 3));
		thisSec.add(image.get(i + 1).substring(j, j + 3));
		thisSec.add(image.get(i + 2).substring(j, j + 3));
		List<String> outSec = rules.get(thisSec);
		if (outSec == null) {
		    System.out.println("Error finding rule for image:");
		    for (String s : thisSec) {
			System.out.println(s);
		    }
		    System.exit(-1);
		}
		str1 = str1.concat(outSec.get(0));
		str2 = str2.concat(outSec.get(1));
		str3 = str3.concat(outSec.get(2));
		str4 = str4.concat(outSec.get(3));
	    }
	    outImage.add(str1);
	    outImage.add(str2);
	    outImage.add(str3);
	    outImage.add(str4);
	}
	image = outImage;
    }

    private void readInput() {
	try (Stream<String> input = Files.lines(Paths.get("input.txt"))) {
	    input.forEach(this::readLine);
	} catch (IOException err) {
	    System.err.println(err);
	    System.exit(-1);
	}
    }

    private void readLine(String line) {
	Matcher match = linePtrn.matcher(line);
	if (!match.find()) {
	    System.out.printf("Bad line: %s\n", line);
	    System.exit(-1);
	}
	List<String> key1 = new ArrayList<String>();
	List<String> val = new ArrayList<String>();
	for (int i = 1; i < 3; ++i) {
	    key1.add(match.group(i));
	}
	if (match.group(3) != null) {
	    key1.add(match.group(3));
	}
	for (int i = 4; i < 7; ++i) {
	    val.add(match.group(i));
	}
	if (match.group(7) != null) {
	    val.add(match.group(7));
	}
	rules.put(key1, val);

	List<String> key2 = new ArrayList<String>(key1);
	Collections.reverse(key2);
	rules.put(key2, val);

	List<String> key3 = new ArrayList<String>();
	for (String s : key1) {
	    char[] inArr = s.toCharArray();
	    char[] outArr = new char[inArr.length];
	    for (int i = 0; i < inArr.length; ++i) {
		outArr[outArr.length - i - 1] = inArr[i];
	    }
	    key3.add(new String(outArr));
	}
	rules.put(key3, val);

	List<String> key4 = new ArrayList<String>(key3);
	Collections.reverse(key4);
	rules.put(key4, val);

	List<String> key5 = rotateImage(key1);
	rules.put(key5, val);
	List<String> key6 = rotateImage(key2);
	rules.put(key6, val);
	List<String> key7 = rotateImage(key3);
	rules.put(key7, val);
	List<String> key8 = rotateImage(key4);
	rules.put(key8, val);
    }

    private List<String> rotateImage(List<String> inImage) {
	List<String> outImage = new ArrayList<String>();
	for (int i = 0; i < inImage.size(); ++i) {
	    String line = "";
	    for (int j = 0; j < inImage.size(); ++j) {
		line = line.concat(inImage.get(inImage.size() - (j + 1))
				   .substring(i, i + 1));
	    }
	    outImage.add(line);
	}
	return outImage;
    }
}
