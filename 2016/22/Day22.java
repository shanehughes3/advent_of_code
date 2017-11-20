import java.util.stream.Stream;
import java.util.*;
import java.nio.file.*;
import java.io.*;

public class Day22 {
    public static void main(String[] args) {
		ServerList servers = new ServerList();
		ServerRoom otherServers = new ServerRoom();
    }
}

class ServerList {
    List<Server> servers;
    int viablePairs = 0; 
    
    public ServerList() {
		servers = Collections.synchronizedList(new ArrayList<Server>());
		readInput();
		countViablePairs();
		System.out.println(viablePairs);
    }

    private void readInput() {
		try(Stream<String> input = Files.lines(Paths.get("input.txt"))) {
			input.forEach(this::pushToServersArray);
		} catch(IOException err) {
			System.out.println(err);
			System.exit(1);
		}
    }

    private void pushToServersArray(String line) {
		String[] lineElements = line.split("[ \t]+");
		if (lineElements[0].charAt(0) == '/') {
			int size = Integer.parseInt(lineElements[1].substring(0, lineElements[1].length() - 1));
			int used = Integer.parseInt(lineElements[2].substring(0, lineElements[2].length() - 1));
			int avail = Integer.parseInt(lineElements[3].substring(0, lineElements[3].length() - 1));
			int percent = Integer.parseInt(lineElements[4].substring(0, lineElements[4].length() - 1));
			Server server = new Server(size, used, avail, percent);
			servers.add(server);
		}			
    }

    private void countViablePairs() {
		for (Server serverA : servers) {
			if (serverA.used > 0) {
				for (Server serverB : servers) {
					if (serverA != serverB && serverA.used <= serverB.avail) {
						++viablePairs;
					}
				}
			}
		}
    }
}

class ServerRoom {
    Server[][] servers = new Server[25][36];
    Location emptyServer;
    Location goodData;
    int depth;
    ServerRoom[] children = new ServerRoom[8];
    boolean hasChildren = false;
    
    public ServerRoom() {
		depth = 0;
		goodData = new Location(24, 0);
		readInput();
		while (true) {
			next();
		}
    }

    public ServerRoom(Server[][] servers, Location emptyServer, Location goodData, int depth) {
		this.servers = servers;
		this.emptyServer = emptyServer;
		this.goodData = goodData;
		this.depth = depth;
    }

    private void readInput() {
		try(Stream<String> input = Files.lines(Paths.get("input.txt"))) {
			input.forEach(this::pushToServersArray);
		} catch(IOException err) {
			System.out.println(err);
			System.exit(1);
		}
    }

    private void pushToServersArray(String line) {
		String[] lineElements = line.split("[ \t]+");
		if (lineElements[0].charAt(0) == '/') {
			int size = Integer.parseInt(lineElements[1].substring(0, lineElements[1].length() - 1));
			int used = Integer.parseInt(lineElements[2].substring(0, lineElements[2].length() - 1));
			int avail = Integer.parseInt(lineElements[3].substring(0, lineElements[3].length() - 1));
			int percent = Integer.parseInt(lineElements[4].substring(0, lineElements[4].length() - 1));
			String[] locationData = lineElements[0].split("-");
			int x = Integer.parseInt(locationData[1].substring(1, locationData[1].length()));
			int y = Integer.parseInt(locationData[2].substring(1, locationData[2].length()));
			servers[y][x] = new Server(size, used, avail, percent);
			if (used == 0) {
				if (emptyServer != null) {
					System.out.println("Duplicate empty servers");
					System.exit(1);
				}
				emptyServer = new Location(x, y);
			}
		}
    }

    private void generateChildren() {
		children[0] = generateChild(-1, -1);
		children[1] = generateChild(-1, 0);
		children[2] = generateChild(-1, 1);
		children[3] = generateChild(0, 1);
		children[4] = generateChild(1, 1);
		children[5] = generateChild(1, 0);
		children[6] = generateChild(1, -1);
		children[7] = generateChild(0, -1);
		hasChildren = true;
    }

    private ServerRoom generateChild(int dX, int dY) {
		if (emptyServer.x + dX < 0 || emptyServer.x + dX > 35 || emptyServer.y + dY < 0 || emptyServer.y + dY > 24) {
			return null;
		}
		Location newEmptyServer = new Location(emptyServer.x + dX, emptyServer.y + dY);
		Location newGoodData = goodData;
		if (newEmptyServer.x == goodData.x && newEmptyServer.y == goodData.y) {
			newGoodData = new Location(emptyServer.x, emptyServer.y);
			if (newGoodData.x == 0 && newGoodData.y == 0) {
				System.out.println(depth + 1);
				System.exit(0);
			}
		}
		Server[][] child = new Server[25][36];
		int i = 0;
		int j = 0;
		for (Server[] serverRow : servers) {
			j = 0;
			for (Server old : serverRow) {
				child[i][j++] = new Server(old.size, old.used, old.avail, old.percent);
			}
			++i;
		}
		child[emptyServer.y + dY][emptyServer.x + dX] = servers[emptyServer.y][emptyServer.x];
		child[emptyServer.y][emptyServer.x] = servers[emptyServer.y + dY][emptyServer.x + dX];
		return new ServerRoom(child, newGoodData, newEmptyServer, depth + 1);
    }

    public void next() {
		if (!hasChildren) {
			generateChildren();
		} else {
			for (ServerRoom child : children) {
				if (child != null) {
					child.next();
				}
			}
		}
    }
}

class Server {
    public int size;
    public int used;
    public int avail;
    public int percent;

    public Server(int _size, int _used, int _avail, int _percent) {
		size = _size;
		used = _used;
		avail = _avail;
		percent = _percent;
    }

    public Server() {
		size = 0;
		used = 0;
		avail = 0;
		percent = 0;
    }
}

class Location {
    public int x;
    public int y;

    public Location(int x, int y) {
		this.x = x;
		this.y = y;
    }
}
