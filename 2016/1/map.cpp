#include <iostream>
#include <fstream>
#include <string>
#include <cctype>
#include <cmath>
#include <cstddef>

using namespace std;

ifstream open(string filename)
{
  ifstream file;
  file.open(filename);
  return file;
}

int turn_facing(int facing, char turn)
{
  if (turn == 'R') {
    facing++;
  } else {
    facing--;
  }

  if (facing < 0) {
    facing += 4;
  } else if (facing > 3) {
    facing -= 4;
  }

  return facing;
}

void calc_found(int row, int col)
{
  int distance = abs(row - 500) + abs(col - 500);
  cout << "Intersect: " << distance << endl;
}

int follow_map(ifstream &file)
{
  int traveled[4] = { }; // north, east, south, west
  bool map[1000][1000];
  int facing_index = 0;
  string input;
  int distance;
  int row = 500,
    col = 500;
  map[500][500] = {0};
  bool found = false;
  
  while (file >> input) {
    facing_index = turn_facing(facing_index, input[0]);
    distance = atoi(input.substr(1, input.size() + 1).c_str());
    traveled[facing_index] += distance;

    if (!found) {
      if (facing_index == 0) { // north
	for (int i = 1; i < distance + 1; i++) {
	  if (map[row - i][col]) {
	    calc_found(row - i, col);
	    found = true;
	  }
	  map[row - i][col] = true;
	}
	row -= distance;
      } else if (facing_index == 1) { // east
	for (int i = 1; i < distance + 1; i++) {
	  if (map[row][col + i]) {
	    calc_found(row, col + i);
	    found = true;
	  }
	  map[row][col + i] = true;
	}
	col += distance;
      } else if (facing_index == 2) { // south
	for (int i = 1; i < distance + 1; i++) {
	  if (map[row + i][col]) {
	    calc_found(row + i, col);
	    found = true;
	  }
	  map[row + i][col] = true;
	}
	row += distance;
      } else if (facing_index == 3) { // west
	for (int i = 1; i < distance + 1; i++) {
	  if (map[row][col - i]) {
	    calc_found(row, col - i);
	    found = true;
	  }
	  map[row][col - i] = true;
	}
	col -= distance;
      }
    }
    
  }

  int north_south = traveled[0] - traveled[2];
  int east_west = traveled[1] - traveled[3];

  return abs(north_south) + abs(east_west);
}

int main(int argc, char* argv[])
{
  if (argc < 2) {
    return 1;
  }

  ifstream file = open(argv[1]);
  if (file) {
    cout << follow_map(file) << endl;
    return 0;
  } else {
    return 2;
  }
}
