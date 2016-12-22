#include <iostream>
#include <list>
#define INPUT 3001330

int exchange() {
  std::list<int> group;
  int size;
  for (int i = 1; i <= INPUT; group.push_back(i++));
  auto current = group.begin();
  
  while ((size = group.size()) > 1) {
    std::cout << size << std::endl;
    int median = size / 2;
    auto taker = current;
    for (int i = 0; i < median; i++) {
      taker++;
      if (taker == group.end()) {
	taker = group.begin();
      }
    }
    group.erase(taker);
    current++;
    if (current == group.end()) {
      current = group.begin();
    }
  }
  return *current;
}

int main() {
  std::cout << exchange() << std::endl;
  return 0;
}
