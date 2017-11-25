import java.util.*;
import java.io.*;
import java.util.stream.*;
import java.nio.file.*;
import java.lang.Math.*;

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
	Target[] targets = new Target[8];
	
	public Map() {
		populate();
	}

	public int start() {
		calculateDistances();
		return calculateBest();
	}

	private void populate() {
		try (Stream<String> input = Files.lines(Paths.get("input.txt"))) {
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
			if (map[i][j] >= '0' && map[i][j] <= '7') {
				targets[Character.getNumericValue(map[i][j])] = new Target(i, j);
			}
		}
		++i;
	}

	private void calculateDistances() {
		for (int sourceNum = 0; sourceNum < 8; ++sourceNum) {
			// calculate all distances
			for (int targetNum = 0; targetNum < 8; ++targetNum) {
				if (sourceNum == targetNum) {
					continue;
				}
				targets[sourceNum].distances[targetNum] =
					findShortestRoute(sourceNum, targetNum);
			}
		}		
	}

	private int findShortestRoute(int source, int target) {
		int startX = targets[source].x;
		int startY = targets[source].y;
		int endX = targets[target].x;
		int endY = targets[target].y;
		int newX, newY;
		boolean[][] closed = new boolean[43][180];
		ArrayList<Point> open = new ArrayList<Point>();
		int finalDistance = 0;
		open.add(new Point(startX, startY, endX, endY, 0));
		
		while (finalDistance == 0) {
			Point current = open.remove(open.size() - 1);
			for (int i = 0; i < 4; ++i) {
				if (i % 2 == 0) {
					newX = current.x - i + 1;
					newY = current.y;
				} else {
					newX = current.x;
					newY = current.y - i + 2;
				}
				if (map[newX][newY] == '#' || closed[newX][newY] == true) {
					continue;
				}
				if (newX == endX && newY == endY) {
					finalDistance = current.distance + 1;
				}
				closed[newX][newY] = true;
				open.add(new Point(newX, newY, endX, endY, current.distance + 1));
			}
			Collections.sort(open, (a, b) -> {
					if (b.score - a.score == 0) {
						return 0;
					}
					return (b.score - a.score) > 0 ? 1 : -1;
				});
		}
		return finalDistance;
	}

	private int calculateBest() {
		int best = Integer.MAX_VALUE;
		boolean[] used = new boolean[8];
		used[0] = true;
		for (int i = 1; i < 8; ++i) {
			boolean[] thisUsed = used.clone();
			thisUsed[i] = true;
			int option = addNextLeg(thisUsed, 1, targets[0].distances[i], i);
			best = (option < best) ? option : best;
		}
		return best;
	}

	private int addNextLeg(boolean[] used, int depth, int current, int thisTarget) {
		if (depth == 7) {
			// for part 1:
			// return current;
			// for part 2:
			return current + targets[thisTarget].distances[0];
		}
		int best = Integer.MAX_VALUE;
		for (int i = 1; i < 8; ++i) {
			if (used[i] == true) {
				continue;
			}
			boolean[] thisUsed = used.clone();
			thisUsed[i] = true;
			int option = addNextLeg(thisUsed, depth + 1, current +
									targets[thisTarget].distances[i], i);
			best = (option < best) ? option : best;
		}
		return best;
	}
}

class Target {
	int x;
	int y;
	int[] distances = new int[8];

	public Target(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Point {
	int x;
	int y;
	int distance;
	int score;

	public Point(int x, int y, int endX, int endY, int distance) {
		this.x = x;
		this.y = y;
		this.distance = distance;
		this.score = heuristic(endX, endY);
	}
	
	private int heuristic(int endX, int endY) {
		return (this.distance * 2) +
			Math.abs(this.x - endX) + Math.abs(this.y - endY);
	}
}
