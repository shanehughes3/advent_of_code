/* * * * * * * * * * 
 * cookies.c
 *
 * finds ideal proportions of ingredients which maximize quality values
 * as specified in input file
 * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void parse_input(int ingredients[][5], char* input);
void max_score(int ingredients[][5], int results[]);
int cookie_score(int ingredients[][5], int i, int j, int k, int l);
int cookie_calories(int ingredients[][5], int i, int j, int k, int l);

int main(int argc, char* argv[])
{
	int ingredients[4][5];
	int results[2] = { 0 };

	if (argc != 2)
	{
		printf("Usage: ./cookies <inputfile.txt>\n");
		return 1;
	}

	parse_input(ingredients, argv[1]);

	max_score(ingredients, results);

	printf("Max score: %d, Max 500: %d\n", results[0], results[1]);

	return 0;
}

/* reads input file, filling 2D array ingredients with data */
void parse_input(int ingredients[][5], char* input)
{
	FILE* input_file = fopen(input, "r");
	char line[150];
	int n = 0;
	int i;

	if (!input_file)
	{
		printf("Error opening file %s\n", input);
		exit(2);
	}

	while (fgets(line, 150, input_file))
	{
		strtok(line, " ");
		strtok(NULL, " ");
		ingredients[n][0] = atoi(strtok(NULL, ","));
		for (i = 1; i < 5; i++)
		{
			strtok(NULL, " ");
			ingredients[n][i] = atoi(strtok(NULL, ","));
		}
		n++;
	}

	fclose(input_file);
}

/* results are returned in the results[] array
 * finds maximum cookie score (results[0]) as well as 
 * max cookie score with exactly 500 calories (results[1]) 
 * by iterating through all ingredient combinations */
void max_score(int ingredients[][5], int results[])
{
	int i, j, k, l, total;
	int max = 0;
	int max_500 = 0;

	for (i = 100; i > -1; i--)
	{
		for (j = 100 - i; j > -1; j--)
		{
			for (k = 100 - i - j; k > -1; k--)
			{
				l = 100 - i - j - k;
				total = 
				   cookie_score(ingredients, i, j, k, l);

				max = (total > max) ? total : max;
				if (cookie_calories(ingredients, i,
						    j, k, l) == 500)
				{
					max_500 = (total > max_500) ? 
						total : max_500;
				}
			}
		}
	}
	results[0] = max;
	results[1] = max_500;
}

/* returns cookie score given amounts of ingredients */
int cookie_score(int ingredients[][5], int i, int j, int k, int l)
{
	int capacity, durability, flavor, texture;

	capacity = i * ingredients[0][0] + 
		j * ingredients[1][0] +
		k * ingredients[2][0] +
		l * ingredients[3][0];
	durability = i * ingredients[0][1] +
		j * ingredients[1][1] + 
		k * ingredients[2][1] +
		l * ingredients[3][1];
	flavor = i * ingredients[0][2] +
		j * ingredients[1][2] +
		k * ingredients[2][2] +
		l * ingredients[3][2];
	texture = i * ingredients[0][3] + 
		j * ingredients[1][3] +
		k * ingredients[2][3] +
		l * ingredients[3][3];

	capacity = (capacity > 0) ? capacity : 0;
	durability = (durability > 0) ? durability : 0;
	flavor = (flavor > 0) ? flavor : 0;
	texture = (texture > 0) ? texture : 0;

	return capacity * durability * flavor * texture;
}

/* returns calories in cookie given amounts of ingredients */
int cookie_calories(int ingredients[][5], int i, int j, int k, int l)
{
	return i * ingredients[0][4] +
		j * ingredients[1][4] +
		k * ingredients[2][4] +
		l * ingredients[3][4];
}

