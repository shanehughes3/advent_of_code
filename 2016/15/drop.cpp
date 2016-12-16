#include <map>
#include <iostream>
#include <array>

void calculate(std::map<int, std::array<int, 2>> &input) {
  int time = 0;
  bool free = false;

  do {
    time++;
    free = true;
    for (int i = 1; i <= input.size(); i++) {
      if ((input[i][1] + time + i) % input[i][0] != 0) {
	free = false;
      }
    }
  } while (free == false);

  std::cout << time << std::endl;
}

int main() {
  std::map<int, std::array<int, 2>> input;
  input[1] = {5, 2};
  input[2] = {13, 7};
  input[3] = {17, 10};
  input[4] = {3, 2};
  input[5] = {19, 9};
  input[6] = {7, 0};
  calculate(input);

  input[7] = {11, 0};
  calculate(input);
  return 0;
}
