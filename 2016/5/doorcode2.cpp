#include <iostream>
#include <sstream>
#include "md5.h"

using namespace std;

int check_hash(string hash) {
  if (hash[0] == '0' && hash[1] == '0' && hash[2] == '0' &&
      hash[3] == '0' && hash[4] == '0' && hash[5] >= '0' &&
      hash[5] <= '7') {
    return hash[5] - '0';
  } else {
    return -1;
  }
}

string get_code(string input)
{
  int index = 0;
  ostringstream stream;
  string output = "________";
  string hash;
  int next;
  cout << output << endl;
  
  while (output.find("_", 0) != string::npos) {
    stream.str("");
    stream.clear();
    stream << input << index;
    hash = md5(stream.str());
    next = check_hash(hash);
    if (next > -1 && output[next] == '_') {
      output[next] = hash[6];
      cout << output << endl;
    }
    index++;
  }
  
  return output;
}

int main(int argc, char* argv[])
{
  cout << get_code(argv[1]) << endl;
  return 0;
}
