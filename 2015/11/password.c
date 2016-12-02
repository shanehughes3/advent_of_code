/* * * * * * * * * * * *
 * password.c
 * 
 * finds the next string that satisfies parameters given a 
 * current string
 * * * * * * * * * * * */

#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>

void increment(char* input, int element);
int check(char* input);
int excluded(char* input);
int straight(char* input);
int doubles(char* input);

int main(int argc, char* argv[])
{
	char* input = argv[1];
	unsigned i;
	int passing = 0;

	if (argc != 2)
	{
		printf("Usage: ./password <input string>\n");
		return 1;
	}

	for (i = 0; i < strlen(input); i++)
	{
		if (!islower(input[i]))
		{
			printf("Invalid input string.\n");
			return 2;
		}
	}

	while (!passing)
	{
		increment(input, strlen(input) - 1);
		passing = check(input);
	}

	printf("Next password: %s\n", input);
	return 0;
}

void increment(char* input, int element)
{
	if (input[element] < 'z')
	{
		input[element] += 1;
	}
	else if (element > 0)
	{
		input[element] = 'a';
		increment(input, element - 1);
	}
	else
	{
		printf("Error: reached maximum string value.\n");
		exit(3);
	}
}

int check(char* input)
{
	if (excluded(input) && straight(input) && doubles(input))
		return 1;
	else
		return 0;
}

int excluded(char* input)
{
	unsigned i;

	for (i = 0; i < strlen(input); i++)
	{
		if (input[i] == 'i' ||
		    input[i] == 'o' ||
		    input[i] == 'l')
		{
			return 0;
		}
	}
	
	return 1;
}

int straight(char* input)
{
        unsigned i;

	for (i = 0; i < strlen(input) - 2; i++)
	{
		if (input[i + 1] == input[i] + 1 &&
		    input[i + 2] == input[i] + 2)
		{
			return 1;
		}
	}
	
	return 0;
}

int doubles(char* input)
{
	unsigned i;
	int found = 0;

	for (i = 0; i < strlen(input) - 1; i++)
	{
		if (input[i] == input[i + 1])
		{
			found++;
			i++;
		}
	}
	if (found > 1)
	{
		return 1;
	}
	else
	{
		return 0;
	}
}
