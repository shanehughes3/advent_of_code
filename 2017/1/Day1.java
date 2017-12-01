import java.util.*;
import java.util.regex.*;
import java.nio.file.*;
import java.io.*;

class Day1 {
	public static void main(String[] args) {
		Captcha captcha = new Captcha();
		System.out.println(captcha.computePart1());
		System.out.println(captcha.computePart2());
	}
}

class Captcha {
	String input;
	public Captcha() {
		readInput();
	}

	public int computePart1() {
		int count = 0;
		Matcher matcher = Pattern.compile("(\\d)(?=\\1)").matcher(input);
		while (matcher.find()) {
			count += Integer.parseInt(matcher.group(0));
		}
		if (input.charAt(0) == input.charAt(input.length() - 2)) {
			count += Character.getNumericValue(input.charAt(0));
		}
		return count;
	}

	public int computePart2() {
		int length = input.length() - 1; // account for newline
		int count = 0;
		for (int i = 0; i < length; ++i) {
			if (input.charAt(i) == input.charAt((i + (length / 2)) % length)) {
				count += Character.getNumericValue(input.charAt(i));
			}
		}
		return count;
	}

	private void readInput() {
		try {
			input = new String(Files.readAllBytes(Paths.get("input.txt")));
		} catch (IOException err) {
			System.out.println(err);
			System.exit(1);
		}
	}
}
