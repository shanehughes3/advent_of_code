import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.regex.*;

class Day20 {
    ArrayList<Point> points = new ArrayList<Point>();
	static final Pattern linePtrn =
		Pattern.compile("^p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>$");
    
    public static void main(String[] args) {
		Day20 day = new Day20();
		// day.check();
		day.run1();
		System.out.printf("Part 2: %d\n", day.run2());
    }

    public Day20() {
		readInput();
    }

	// public void check() {
	// 	for (int i = 0; i < points.size(); ++i) {
	// 		if (points.get(i).check()) {
	// 			System.out.printf("Possible: %d\n", i);
	// 		}
	// 	}
	// }

	public void run1() {
		int bestTotal = Integer.MAX_VALUE;
		int bestI = 0;
		HashSet<Integer> options = new HashSet<Integer>();
		for (int i = 0; i < points.size(); ++i) {
			int val = points.get(i).getTotalAccel();
			if (val < bestTotal) {
				options.clear();
				options.add(i);
				bestTotal = val;
			} else if (val == bestTotal) {
				options.add(i);
			}
		}
		for (int i : options) {
		    System.out.printf("Part 1 possibility: %d\n", i);
		}
	}

	public int run2() {
		HashSet<Integer> elimThisTick = new HashSet<Integer>();
		HashMap<List<Long>, Integer> positions =
			new HashMap<List<Long>, Integer>();
		for (long t = 0; t < 20000000; ++t) {
			for (int i = 0; i < points.size(); ++i) {
				List<Long> thisPos = points.get(i).getPos(t);
				if (positions.containsKey(thisPos)) {
					elimThisTick.add(positions.get(thisPos));
					elimThisTick.add(i);
				} else {
					positions.put(thisPos, i);
				}
			}
			for (int i : elimThisTick) {
				points.remove(i);
			}
			elimThisTick.clear();
			positions.clear();
		}
		return points.size();
	}

    private void readInput() {
		try (Stream<String> lines = Files.lines(Paths.get("input.txt"))) {
			lines.forEach(this::readLine);
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
		points.add(new Point(new int[] {
					Integer.parseInt(match.group(1)),
						Integer.parseInt(match.group(2)),
						Integer.parseInt(match.group(3)) },
				new int[] { Integer.parseInt(match.group(4)),
						Integer.parseInt(match.group(5)),
						Integer.parseInt(match.group(6)) },
				new int[] { Integer.parseInt(match.group(7)),
						Integer.parseInt(match.group(8)),
						Integer.parseInt(match.group(9)) }
				));
									   
    }
}

class Point {
    int[] pos = new int[3];
	long[] lastPos = new long[3];
    int[] vec = new int[3];
	long[] lastVec = new long[3];
    int[] accel = new int[3];

    public Point(int[] pos, int[] vec, int[] accel) {
		this.pos = pos;
		this.lastPos = Arrays.stream(pos).asLongStream().toArray();
		this.vec = vec;
		this.lastVec = Arrays.stream(vec).asLongStream().toArray();
		this.accel = accel;
    }

	// public long getDistance(long t) {
	// 	long total = 0;
	// 	for (int i = 0; i < 2; ++i) {
	// 		total += Math.abs((accel[i] * (t * t)) + (vec[i] * t) + pos[i]);
	// 	}
	// 	return total;
	// }

	public int getTotalAccel() {
		int total = 0;
		for (int i = 0; i < 3; ++i) {
			total += Math.abs(accel[i]);
		}
		return total;
	}

	// public List<Long> getPos(long t) {
	// 	List<Long> out = new ArrayList<Long>();
	// 	for (int i = 0; i < 3; ++i) {
	// 		out.add((accel[i] * (t * t)) + (vec[i] * t) + pos[i]);
	// 	}
	// 	return out;
	// }

	public List<Long> getPos(long t) {
		List<Long> out = new ArrayList<Long>();
		for (int i = 0; i < 3; ++i) {
		    lastVec[i] += accel[i];
			lastPos[i] += lastVec[i];
			out.add(lastPos[i]);
		}
		return out;
	}

	// public long getTotalDelta(long t) {
	// 	long total = 0;
	// 	for (int i = 0; i < 3; ++i) {
	// 		long here = (accel[i] * (t * t)) + (vec[i] * t) + pos[i];
	// 		long der = (accel[i] * t * 2) + vec[i];
	// 		long der2 = accel[i] * 2;
	// 		if ((here >= 0 && der >= 0) || here < 0 && der < 0) {
	// 			total += Math.abs(der2);
	// 		} else {
	// 			total -= Math.abs(der2);
	// 		}
	// 	}
	// 	return total;
	// }

	// public int getDeltaVal() {
	// 	int total = 0;
	// 	for (int i = 0; i < 3; ++i) {
	// 		total += Math.abs(vec[i]) + accel[i] * accel[i];
	// 	}
	// 	return total;
	// }

	// public boolean check() {
	// 	for (int a : accel) {
	// 		if (Math.abs(a) > 4) {
	// 			return false;
	// 		}
	// 	}

	// 	for (int i = 0; i < 3; ++i) {
	// 		if (vec[i] > 0 && accel[i] > 0 && Math.abs(accel[i]) > 2) {
	// 			return false;
	// 		} else if (vec[i] < 0 && accel[i] < 0 && Math.abs(accel[i]) > 2) {
	// 			return false;
	// 		} else if (accel[i] == 0 && Math.abs(vec[i]) > 5) {
	// 			return false;
	// 		}
	// 	}

	// 	return true;
	// }
}

