/* * * * * * * * * * *
 * happiness.c
 *
 * calculates "happiness points" based upon given information on
 * how people's arrangement at a table will affect those points
 *
 * _self functions include oneself in the seating arrangement,
 * where self has a 0 happiness points with all others
 * * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void parse_line(char line[], int table[][8]);
int get_name(char* name);
void find_max(int table[][8], int used[], int depth, int* max);
int check_used(int used[], int value);
int add_places(int table[][8], int used[]);
void find_max_self(int table[][8], int used[], int depth, int* max);
int check_used_self(int used[], int value);
int add_places_self(int table[][8], int used[]);

int main(int argc, char* argv[])
{
	FILE* input;
	char line[100];
	int table[8][8];
	int used[8];
	int used_self[9];
	int max_happy = 0;
	int* max = &max_happy;

	memset(used, 8, sizeof(int)* 8);
	memset(used_self, 9, sizeof(int) * 9);

	if (argc != 2)
	{
		printf("Usage: ./happiness <inputfile.txt>\n");
		return 1;
	}

	input = fopen(argv[1], "r");
	if (!input)
	{
		printf("Error opening file %s\n", argv[1]);
		return 2;
	}

	while (fgets(line, 100, input))
	{
		parse_line(line, table);
	}

	fclose(input);

	find_max(table, used, 0, max);
	printf("Without self: %d\n", *max); 

	*max = 0;
	find_max_self(table, used_self, 0, max);
	printf("With self: %d\n", *max);

	return 0;
}

void parse_line(char line[], int table[][8])
{
	int x, y, delta;
	char* word;
	int gain;

	word = strtok(line, " ");
	x = get_name(word);
	word = strtok(NULL, " ");
	word = strtok(NULL, " ");
	gain = (!strcmp(word, "gain") ? 1 : 0);
	word = strtok(NULL, " ");
	delta = (gain ? atoi(word) : 0 - atoi(word));
	word = strtok(NULL, " ");
	word = strtok(NULL, " ");
	word = strtok(NULL, " ");
	word = strtok(NULL, " ");
	word = strtok(NULL, " ");
	word = strtok(NULL, " ");
	word = strtok(NULL, ".");
	y = get_name(word);

	table[x][y] = delta;	
}

int get_name(char* name)
{
	if (!strcmp(name, "Alice"))
		return 0;
	else if (!strcmp(name, "Bob"))
		return 1;
	else if (!strcmp(name, "Carol"))
		return 2;
	else if (!strcmp(name, "David"))
		return 3;
	else if (!strcmp(name, "Eric"))
		return 4;
	else if (!strcmp(name, "Frank"))
		return 5;
	else if (!strcmp(name, "George"))
		return 6;
	else if (!strcmp(name, "Mallory"))
		return 7;
	else
	{
		printf("Invalid name: %s\n", name);
		exit(3);
	}
}

void find_max(int table[][8], int used[], int depth, int* max)
{
	int n, i, total_happiness;
	if (depth < 7)
	{
		for (n = 0; n < 8; n++)
		{
			if (!check_used(used, n))
			{
				used[depth] = n;
				find_max(table, used, depth + 1, max);

				for (i = depth; i < 8; i++)
				{
					used[i] = 8;
				}
			}
		}
		

	}
	else
	{
		for (n = 0; n < 8; n++)
		{
			if (!check_used(used, n))
			{
				used[7] = n;
				break;
			}
		}
		total_happiness = add_places(table, used);
		
		if (*max < total_happiness)
		{
			*max = total_happiness;
		}
	}
}

int check_used(int used[], int value)
{
	int n;
	for (n = 0; n < 8; n++)
	{
		if (used[n] == value)
		{
			return 1;
		}
	}

	return 0;
}

int add_places(int table[][8], int used[])
{
	int total_happiness = 0;
	int n;
	
	for (n = 0; n < 7; n++)
	{
		total_happiness += table[used[n]][used[n + 1]];
		total_happiness += table[used[n + 1]][used[n]];
	}
	total_happiness += table[used[0]][used[7]];
	total_happiness += table[used[7]][used[0]];

	return total_happiness;
}

void find_max_self(int table[][8], int used[], int depth, int* max)
{
	int n, i, total_happiness;
	if (depth < 8)
	{
		for (n = 0; n < 9; n++)
		{
			if (!check_used_self(used, n))
			{
				used[depth] = n;
				find_max_self(table, used, depth + 1, max);

				for (i = depth; i < 9; i++)
				{
					used[i] = 9;
				}
			}
		}
		

	}
	else
	{
		for (n = 0; n < 9; n++)
		{
			if (!check_used_self(used, n))
			{
				used[8] = n;
				break;
			}
		}
		total_happiness = add_places_self(table, used);
		
		if (*max < total_happiness)
		{
			*max = total_happiness;
		}
	}
}

int check_used_self(int used[], int value)
{
	int n;
	for (n = 0; n < 9; n++)
	{
		if (used[n] == value)
		{
			return 1;
		}
	}

	return 0;
}

int add_places_self(int table[][8], int used[])
{
	int total_happiness = 0;
	int n;
	
	for (n = 0; n < 8; n++)
	{
		if (used[n] != 8 && used[n + 1] != 8)
		{
			total_happiness += table[used[n]][used[n + 1]];
			total_happiness += table[used[n + 1]][used[n]];
		}
	}
	if (used[0] != 8 && used[8] != 8)
	{
		total_happiness += table[used[0]][used[8]];
		total_happiness += table[used[8]][used[0]];
	}

	return total_happiness;
}
