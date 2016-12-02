/* * * * * * * *
 * machine.c
 *
 * generates a code given a row and column
 * * * * * * * */

#include <stdio.h>
#include <stdlib.h>

long long find_iteration(int row, int column);
long long find_code(long long iteration);

int main(int argc, char* argv[])
{
	long long iteration;

	if (argc != 3)
	{
		printf("Usage: ./machine <row> <column>\n");
		return 1;
	}

	iteration = find_iteration(atoi(argv[1]), atoi(argv[2]));
	printf("%llu\n", find_code(iteration));
	
	return 0;
}

long long find_iteration(int row, int column)
{
	long long iteration = 1;
	int i;

	for (i = 1; i <= row; i++)
	{
		iteration += i - 1;
	}

	for (i = 2; i <= column; i++)
	{
		iteration += (row - 1) + i;
	}

	return iteration;
}

long long find_code(long long iteration)
{
	long long i;
	long long code;

	code = 20151125;

	for (i = 1; i < iteration; i++)
	{
		code *= 252533;
		code = code % 33554393;
	}
	
	return code;
}

