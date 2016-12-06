#include <iostream>
#include <sstream>
#include "md5.h"

using namespace std;

char check_hash(string hash) {
  if (hash[0] == '0' && hash[1] == '0' && hash[2] == '0' &&
      hash[3] == '0' && hash[4] == '0') {
    return hash[5];
  } else {
    return 'z';
  }
}

string get_code(string input)
{
  int index = 0;
  ostringstream stream;
  string output = "";
  string hash;
  char next;
  while (output.size() < 8) {
    stream.str("");
    stream.clear();
    stream << input << index++;
    hash = md5(stream.str());
    next = check_hash(hash);
    if (next != 'z') {
      output.push_back(next);
      cout << output << endl;
    }
  }
  return output;
}

int main(int argc, char* argv[])
{
  cout << get_code(argv[1]) << endl;
  return 0;
}
