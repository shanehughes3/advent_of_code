#include <iostream>
#include <vector>
#include <cmath>
#include <array>
#include <algorithm>
#include <map>
#define INPUT 1358

// uses A*, finds part 1 in 316 iterations

struct space {
  int x;
  int y;
  int distance;
  int steps;
  space* parent;
};

std::array<std::array<int, 2>, 4> next = {{
    {{0, 1}},
    {{0, -1}},
    {{1, 0}},
    {{-1, 0}}
  }};

int get_distance(int x, int y) {
  return std::abs(31 - x) + std::abs(39 - y);
}

bool check_space(int x, int y) { // returns true if space, false if wall
  if (x < 0 || y < 0) {
    return false;
  }
  int sum = (x * x) + (3 * x) + (2 * x * y) + y + (y * y) + INPUT;
  int count = 0;
  while (sum > 0) {
    count += sum % 2;
    sum >>= 1;
  }
  return !(count % 2);
}

bool compare(space a, space b) {
  return (a.distance + a.steps) > (b.distance + b.steps);
}

int main() {
  
  std::vector<space> queue;
  std::map<std::array<int, 2>, int> visited;
  space start = { 1, 1, get_distance(1, 1), 0, 0};
  space current = start;
  int loops = 0;

  while (current.distance > 0) {
    loops++;
    visited[{{current.x, current.y}}] = current.steps;
    for (auto i = next.begin(); i != next.end(); i++) {
      int nextX = current.x + (*i)[0];
      int nextY = current.y + (*i)[1];

      if (check_space(nextX, nextY) &&
	  (visited[{{nextX, nextY}}] == 0 ||
	   current.steps < visited[{{nextX, nextY}}])) {
	space new_space = {nextX, nextY, get_distance(nextX, nextY),
			   current.steps + 1, &current};
	queue.push_back(new_space);
      }
    }
    std::sort(queue.begin(), queue.end(), compare);
    current = *(--queue.end());
    queue.pop_back();
  }
  
  std::cout << current.steps << ' ' << loops << std::endl;
  
  return 0;
}
