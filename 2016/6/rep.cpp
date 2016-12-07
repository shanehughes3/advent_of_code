#include <iostream>
#include <fstream>
#include <array>
#include <vector>
#include <algorithm>

std::vector < std::array < int, 26 > > initialize(int length)
{
  std::vector < std::array < int, 26 > > letters;
  for (int i = 0; i < length; i++) {
    std::array < int, 26 > empty = { 0 };
    letters.push_back(empty);
  }
  return letters;
}

void add_letters(std::string line,
		 std::vector < std::array < int, 26 > > &letters)
{
  for (int i = 0; i < line.length(); i++) {
    letters[i][line[i] - 'a'] += 1;
  }
}

std::string parse_letters(std::vector < std::array < int, 26 > > letters)
{
  std::string output1 = "";
  std::string output2 = "";
  for (int i = 0; i < letters.size(); i++) {
    int index1 = std::distance(letters[i].begin(),
			       std::max_element(letters[i].begin(),
						letters[i].end()));
    output1.push_back('a' + index1);
    int index2 = std::distance(letters[i].begin(),
			       std::min_element(letters[i].begin(),
						letters[i].end()));
    output2.push_back('a' + index2);
  }
  return output1 + "\t" + output2;
}

std::string get_message(std::ifstream &infile)
{
  std::string line;
  getline(infile, line);
  std::vector < std::array < int, 26 > > letters = initialize(line.length());
  add_letters(line, letters);
  while(getline(infile, line)) {
    add_letters(line, letters);
  }
  return parse_letters(letters);
}

int main(int argc, char* argv[])
{
  std::ifstream file;
  file.open(argv[1]);
  std::cout << get_message(file) << std::endl;
  return 0;
}
