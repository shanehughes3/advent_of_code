package knot;
import java.util.*;

public class Knot {
    private int[] input;
    private int[] list = new int[256];
    private int rounds;

    public Knot() {
	for (int i = 0; i < list.length; list[i] = i++);
    }

    public Knot(int[] rawInput) {
	this();
	input = rawInput;
	rounds = 1;
    }

    public Knot(String rawInput) {
	this();
	input = new int[rawInput.length() + 5];
	int i;
	for (i = 0; i < rawInput.length(); ++i) {
	    input[i] = (int) rawInput.charAt(i);
	}
	input[i++] = 17;
	input[i++] = 31;
	input[i++] = 73;
	input[i++] = 47;
	input[i] = 23;
	rounds = 64;
    }

    public void hash() {
	int pos = 0;
	int skip = 0;
	for (int round = 0; round < rounds; ++round) {
	    for (int len : input) {
		for (int i = 0; i < len / 2; ++i) {
		    int tmp = list[(pos + i) % 256];
		    list [(pos + i) % 256] = list[(pos + len - 1 - i) % 256];
		    list [(pos + len - 1 - i) % 256] = tmp;
		}
		pos = (pos + skip++ + len) % 256;
	    }
	}
    }

    public int getValues() {
	return list[0] * list[1];
    }

    public String getDenseHash() {
	String output = "";
	for (int i = 0; i < 16; ++i) {
	    int roundOut = list[i * 16];
	    for (int j = 1; j < 16; ++j) {
		roundOut = roundOut ^ list[(i * 16) + j];
	    }
	    output += String.format("%02x", roundOut);
	}
	return output;
    }
}
