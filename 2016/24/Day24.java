import java.util.*;
import java.io.*;
import java.util.stream.*;
import java.nio.file.*;

public class Day24 {
	public static void main(String[] args) {
		Map map = new Map();
		System.out.println(map.start());
	}
}

class Map {
	public char[][] map = new char[43][180];
	int startX;
	int startY;
	int currentX;
	int currentY;
	int i = 0;
	List<Location> lastRun;
	List<Location> thisRun = new ArrayList<Location>();
	boolean foundThisRun;
	
	public Map() {
		populate();
	}

	public int start() {
		foundThisRun = false;
		int runs = 0;
		thisRun.add(new Location(startX, startY, Location.startFound));
		while (foundThisRun == false) {
			++runs;
			lastRun = new ArrayList<Location>(thisRun);
			thisRun.clear();
			for (Location loc : lastRun) {
				generateChildren(loc);
			}
			System.out.println(runs);
			lastRun.clear();
		}
		return runs;
	}

	private void populate() {
		try (Stream<String> input = Files.lines(Paths.get("input.txt"))) {
			int i = 0;
			input.forEach((String line) -> {
					pushToMap(line);
				});
		} catch(IOException err) {
			System.out.println(err);
			System.exit(1);
		}
	}

	private void pushToMap(String line) {
		map[i] = line.toCharArray();
		for (int j = 0; j < map[i].length; ++j) {
			if (map[i][j] == '0') {
				startX = i;
				startY = j;
			}
		}
		++i;
	}

	private void generateChildren(Location loc) {
		if (map[loc.x - 1][loc.y] != '#') {
			generateChild(loc, loc.x - 1, loc.y);
		}
		if (map[loc.x + 1][loc.y] != '#') {
			generateChild(loc, loc.x + 1, loc.y);
		}
		if (map[loc.x][loc.y - 1] != '#') {
			generateChild(loc, loc.x, loc.y - 1);
		}
		if (map[loc.x][loc.y + 1] != '#') {
			generateChild(loc, loc.x, loc.y + 1);
		}
	}

	private void generateChild(Location loc, int x, int y) {
		char space = map[x][y];
		if (space == '.' || space == '0') {
			thisRun.add(new Location(x, y, loc.found.clone()));
		} else {
			boolean[] newFound = loc.found.clone();
			newFound[Character.getNumericValue(space)] = true;
			boolean allFound = true;
			for (boolean target : newFound) {
				if (!target) {
					allFound = false;
					break;
				}
			}
			if (allFound) {
				foundThisRun = true;
			}
			thisRun.add(new Location(x, y, newFound));
		}
	}
}

class Location {
	public int x;
	public int y;
	public boolean[] found;
	public static final boolean[] startFound =
	{false, false, false, false, false, false, false};

	public Location(int x, int y, boolean[] found) {
		this.x = x;
		this.y = y;
		this.found = found;
	}
}
