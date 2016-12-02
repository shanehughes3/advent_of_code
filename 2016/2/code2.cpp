#include <iostream>
#include <fstream>
#include <string>

using namespace std;

char get_next_digit(string line, int last[2])
{
  int next[2];
  string pad[5];
  for (int i = 0; i < 5; i++) {
    pad[i] = "00000";
  }
  pad[0][2] = '1';
  pad[1][1] = '2';
  pad[1][2] = '3';
  pad[1][3] = '4';
  pad[2][0] = '5';
  pad[2][1] = '6';
  pad[2][2] = '7';
  pad[2][3] = '8';
  pad[2][4] = '9';
  pad[3][1] = 'A';
  pad[3][2] = 'B';
  pad[3][3] = 'C';
  pad[4][2] = 'D';

  for (int i = 0; i < line.length(); i++) {
    next[0] = last[0];
    next[1] = last[1];
    switch (line[i]) {
    case 'U':
      next[0] = (last[0] > 0) ? last[0] - 1 : last[0];
      break;
    case 'D':
      next[0] = (last[0] < 4) ? last[0] + 1 : last[0];
      break;
    case 'L':
      next[1] = (last[1] > 0) ? last[1] - 1 : last[1];
      break;
    case 'R':
      next[1] = (last[1] < 4) ? last[1] + 1 : last[1];
      break;
    }
    if (pad[next[0]][next[1]] != '0') {
      last[0] = next[0];
      last[1] = next[1];
    }
  }

  return pad[last[0]][last[1]];
}

string get_code(ifstream &file)
{
  string line;
  string output;
  int last[] = {2, 2};
  
  while (getline(file, line)) {
    output.push_back(get_next_digit(line, last));
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

  return 0;
}
