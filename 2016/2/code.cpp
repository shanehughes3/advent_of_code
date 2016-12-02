#include <iostream>
#include <fstream>
#include <string>

using namespace std;

int get_next_digit(string line, int last)
{
  int location[2]; // row, col
  location[0] = (last - 1) / 3;
  location[1] = (last - 1) % 3;
  for (int i = 0; i < line.length(); i++) {
    switch (line[i]) {
    case 'U':
      location[0] = (location[0] > 0) ? location[0] - 1 : location[0];
      break;
    case 'D':
      location[0] = (location[0] < 2) ? location[0] + 1 : location[0];
      break;
    case 'L':
      location[1] = (location[1] > 0) ? location[1] - 1 : location[1];
      break;
    case 'R':
      location[1] = (location[1] < 2) ? location[1] + 1 : location[1];
      break;
    }
  }
  return (location[0] * 3) + (location[1] + 1);
}

int get_code(ifstream &file)
{
  string line;
  long long output = 0;
  int last = 5;
  
  while (getline(file, line)) {
    output *= 10;
    last = get_next_digit(line, last);
    output += last;
  }

  return output;
}

int main(int argc, char* argv[])
{
  ifstream file;
  if (argc < 2) {
    return 1;
  }
  file.open(argv[1]);
  if (!file) {
    return 2;
  }

  cout << get_code(file) << endl;
}
