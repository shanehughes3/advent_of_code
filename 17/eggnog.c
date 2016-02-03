/* * * * * * * * * * * *
 * eggnog.c
 * 
 * prints the number of combinations of containers given in an input file
 * that can hold a specified volume
 *
 * also prints the number of combinations possible using the fewest containers
 * * * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void parse_input(int containers[], char* input_name);
int find_combinations(int total, int containers[], int test,
		      int depth, int volume, int number, int lowest[]);

int main(int argc, char* argv[])
{
	int containers[20];
	int lowest[5] = { 0 };
	int test, i;
	int total = 0;
	int number = 0;
	

	if (argc != 3)
	{
		printf("Usage: ./eggnog <input.txt> <test volume>\n");
		return 1;
	}

	parse_input(containers, argv[1]);

	test = atoi(argv[2]);

	total = find_combinations(total, containers, test, 0, 0, number, 
				  lowest);

	printf("Total combinations: %d\n", total);

	for (i = 0; i < 5; i++)
	{
		if (lowest[i] > 0)
		{
		        printf("%d: %d\n", i, lowest[i]);
		}
	}
	

	return 0;
}

void parse_input(int containers[], char* input_name)
{
	char line[5];
	int n = 0;
	FILE* input = fopen(input_name, "r");

	if (!input)
	{
		printf("Error opening file %s\n", input_name);
		exit(2);
	}

	while (fgets(line, 5, input))
	{
		containers[n++] = atoi(line);
	}
	
	fclose(input);
}

/* recursive function that returns the number of combinations where the total
 * volume equals test
 * modifies lowest[] array with number of lowest possibilities
 */
int find_combinations(int total, int containers[], int test, int depth, 
		      int volume, int number, int lowest[])
{
	int i;

	for (i = 0; i < 2; i++)
	{
		volume += (i) ? containers[depth] : 0;
		number += (i) ? 1 : 0;
		total += (volume == test) ? 1 : 0;
		if (volume == test && number < 5)
		{
			lowest[number]++;
		}
		if (depth < 20 && volume < test)
		{
			total = find_combinations(total, containers, test,
						  depth + 1, volume, number,
						  lowest);
		}
	}
	return total;
}
