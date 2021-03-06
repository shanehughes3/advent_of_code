/* * * * * * * * * * * * * *
 * houses.c
 *
 * finds the lowest positive integer where all factors (whose dividends are 
 * less than 50) multiplied by 11 and
 * added together equal a given number
 * * * * * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>

int find_house(int input);

int main(int argc, char* argv[])
{
	if (argc != 2)
	{
		printf("Usage: ./houses <min-presents>\n");
		return 1;
	}

	printf("Lowest house number: %d\n", find_house(atoi(argv[1])));

	return 0;
}

int find_house(int input)
{
	int i, j, presents, half;

	for (i = 1; i < 3000000; i++)
	{
		presents = 0;
		half = i / 2;

		for (j = 1; j <= half; j++)
		{
			if (i % j == 0 && i / j <= 50)
			{
				presents += j * 11;
			}
		}

		presents += i * 11;

		if (presents > input)
		{
			return i;
		}
	}

	printf("Error: out of bounds\n");
	return 0;
}
